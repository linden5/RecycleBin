import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ChatServer {
	private Hashtable<String, User> info;
	private Hashtable<String, Integer> count;
	private Hashtable<String, MessageBuffer> msgBuffer;
	private String filename;
	private ServerSocket ssocket;
	private DatagramSocket dgsocket;
	private Socket socket;
	private Lock lock;
	
	/** Constructor */
	public ChatServer(ServerSocket ssocket, DatagramSocket dgsocket) {
		this.info = new Hashtable<String, User>();
		this.count = new Hashtable<String, Integer>();
		this.msgBuffer = new Hashtable<String, MessageBuffer>();
		this.filename = "userInfo.txt";
		this.ssocket = ssocket;
		this.dgsocket = dgsocket;
		this.socket = null;
		this.lock = new ReentrantLock();
	}
	
	/** Get Login information and return results to the client */
	void handleLogInOut() throws Exception {
		// Initialize user list
		userListInit();
		new OfflineMessageHandler().start();
		new UpdateCount().start();
		new OnlineChecker().start();
		while(true) {
			socket = ssocket.accept();
			Thread loginout = new ReceiveTCP(socket);
			loginout.setPriority(Thread.MAX_PRIORITY);
			loginout.start();
		}
	}
	
	/** Thread to handle tcp connection */
	class ReceiveTCP extends Thread {
		private Socket rsocket;
		
		public ReceiveTCP(Socket rsocket) {
			this.rsocket = rsocket;
		}
		
		@Override
		public void run() {
			byte[] buf = new byte[1024];
			String[] userInfo;
			String[] user;
			
			try {
				//System.out.println(Thread.currentThread().getName()+"getlink");
				do {
					// Print received information
					userInfo = Communicate.tcpReceive(rsocket, buf);
					//System.out.println(Thread.currentThread().getName()+"ReceiveInfo");
					user = userInfo[2].split(":"); // split data to get information
					String printString = "User Address: " + userInfo[0] + " \\ TCP Port: " + userInfo[1] + " \\ Request---" + userInfo[2];				
					/* ------- Note for fields ------------
					 * String username = user[1];
					 * String password = user[2];
					 * String ip = userInfo[0];
					 * String listenPort = user[3];
					 */
					lock.lock();
					//System.out.println(Thread.currentThread().getName()+"aaaaa");
					if ("Login".equals(user[0])) {
						//Login handling
						handleLogin(rsocket, user, userInfo);
						System.out.println(printString);
					} else if ("Create".equals(user[0])) {
						//Create Account handling
						handleCreate(rsocket, user, userInfo);
						System.out.println(printString);
						printList();
					} else if ("Logout".equals(user[0])) {
						//Log out handling
						updateUserInfo(user, userInfo, false);
						count.remove(user[1]);
						rsocket.close();
						System.out.println(printString);
						printList();
					} else if ("Update".equals(user[0])) {
						updateUserInfo(user, userInfo, true);
						count.put(user[1], new Integer(5));
						sendList(rsocket);
					} else if ("Reset".equals(user[0])) {
						handleReset(rsocket, user, userInfo);
						System.out.println(printString);
						printList();
					}
					lock.unlock();
				} while(!rsocket.isClosed());
			} catch(Exception e) {
				//e.printStackTrace();
			}
		}
	}
	
	/** Thread to handle offline message */
	class OfflineMessageHandler extends Thread {
		@Override
		public void run() {
			byte[] buf = new byte[1024];
			String[] incomeMessage = new String[3];
			String[] msg = null;
			MessageBuffer current;
			// Listening for incoming message
			while(true) {
				System.out.println(Thread.currentThread().getName()+"UDP listening");
				try {
					incomeMessage = Communicate.udpReceive(dgsocket, buf);
					msg = incomeMessage[2].split(" +");
					String find = msg[msg.length - 1];
					Communicate.saveLog("server_to" + find + ".txt", msg[1]);
				} catch(Exception e) {
					e.printStackTrace();
				}

				if (msg != null){
					// Find the dest name
					String find = msg[msg.length - 1];	
					if (!msgBuffer.containsKey(find)) {
						current = new MessageBuffer(find);
						current.getBuffer().append(incomeMessage[2].substring(0, incomeMessage[2].length() - find.length() - 2));
						msgBuffer.put(find, current);
					} else {
						current = msgBuffer.get(find);
						current.getBuffer().append(incomeMessage[2].substring(0, incomeMessage[2].length() - find.length() - 2));
					}

					System.out.println("Incoming-----IP: " + incomeMessage[0] + " \\ Sending Port: " + incomeMessage[1] + "-----\r\n" + incomeMessage[2]);
				}
			}
		}
	}
	
	class OnlineChecker extends Thread {
		@Override
		public void run() {
			while(true) {
				lock.lock();
				Set<String> keySet = info.keySet();
				Iterator it = keySet.iterator();

				while(it.hasNext()) {
					String s = (String)it.next();
					User u = info.get(s);
					//System.out.println(Thread.currentThread().getName()+"onlineChecking");
					//System.out.println(Thread.currentThread().getName() + u.getUsername() + u.getState());
					//System.out.println(Thread.currentThread().getName() + u.getUsername() + "buff" + msgBuffer.containsKey(s));
					if (u.getState() && msgBuffer.containsKey(s)) {
						System.out.println(Thread.currentThread().getName()+"onlineChecking");
						MessageBuffer current = msgBuffer.get(s);
						if (current.getIndexOfShowed() < current.getBuffer().length()) {
							System.out.println("User login, send");
							Communicate.udpSendMsg(current.getBuffer().substring(current.getIndexOfShowed()), u.getIp(), u.getPort());
							current.setIndexOfShowed(current.getBuffer().length());
							System.out.println("User login, finish");
						}
					}
				}
				lock.unlock();
			}
		}			
	}

	class UpdateCount extends Thread {
		@Override
		public void run() {
			String current;
			int i;
			Hashtable<String, Integer> tmp = new Hashtable<String, Integer>();
			while(true) {
				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					
				}
				lock.lock();
				tmp.putAll(count);
				Set<String> countSet = tmp.keySet();
				Iterator it = countSet.iterator();
				//System.out.println(Thread.currentThread().getName()+"hhhhh");
				while(it.hasNext()) {
					//System.out.println(Thread.currentThread().getName()+"nnnnnn");
					current = (String)it.next();
					i = tmp.get(current).intValue();
					i--;
					if (i <= 0) {
						count.remove(current);
						User u = info.get(current);
						u.setState(false);
						info.put(current, u);
					}
					else
						count.put(current, new Integer(i));
				}
				lock.unlock();
			}
		}
	}
	
	/** Send user list Hashtable<String, User> info, Socket rsocket*/
	void sendList(Socket rsocket) throws Exception {
		byte[] byteInfo = Communicate.object2Byte(info);
		Communicate.tcpSend(rsocket, byteInfo);
	}
	
	/** Handle user login */
	void handleLogin(Socket rsocket, String[] user, String[] userInfo) {
		if (!info.containsKey(user[1])) {
			SendLoginAck(rsocket, "Username doesn't exists!");
		} else if (!user[2].equals(info.get(user[1]).getPassword())) {
			SendLoginAck(rsocket, "Password Error!");
		} else if (info.get(user[1]).getState() == true) {
			SendLoginAck(rsocket, "Already Logged in!");
		} else {
			count.put(user[1], new Integer(5));
			SendLoginAck(rsocket, "OK");
		}	
	}
	
	/** Handle create Account */
	void handleCreate(Socket rsocket, String[] user, String[] userInfo) 
		throws Exception {
		if (!info.containsKey(user[1])) {
			updateUserInfo(user, userInfo, true);
			writeUserInfo();
			count.put(user[1], new Integer(5));
			SendLoginAck(rsocket, "OK");
		} else {
			SendLoginAck(rsocket, "Username already exists!");
		}
	}
	
	/** Handle Reset Password */
	void handleReset(Socket rsocket, String[] user, String[] userInfo) 
		throws Exception {
		String[] password = user[2].split("&");
		if (!info.containsKey(user[1])) {
			SendLoginAck(rsocket, "Username doesn't exists!");
		} else if (!password[0].equals(info.get(user[1]).getPassword())) {
			SendLoginAck(rsocket, "Old password Error!");
		} else {
			user[2] = password[1];
			updateUserInfo(user, userInfo, true);
			writeUserInfo();
			SendLoginAck(rsocket, "Password reseted!");
		}	
	}
	
	void writeUserInfo() throws IOException {
		ObjectOutputStream objos = new ObjectOutputStream(new FileOutputStream(filename));
		objos.writeObject(info);
		objos.flush();		
		objos.close();		
	}
	
	/** User list initialization */
	void userListInit() {
		File infoFile = new File(filename);
		ObjectInputStream objis = null;
		boolean flag = false;
		try {
			if (infoFile.exists()) {
				objis = new ObjectInputStream(new FileInputStream(filename));
				info = (Hashtable<String, User>)objis.readObject();
				objis.close();
				flag = true;
			} else {
				infoFile.createNewFile();
			}
		} catch(IOException ee) {
			System.out.println("User info file doesn't exist or empty! No problem, continue...");
		} catch(ClassNotFoundException cnfe) {
			throw new RuntimeException("Data damage or user info data format missmatch!");
		}
		
		if (flag) {
			Set<String> keySet = info.keySet();
			Iterator<String> it = keySet.iterator();
			while(it.hasNext()) {
				String tmp = (String)it.next();
				User current = info.get(tmp);
				current.setState(false);
				info.put(tmp, current);
			}
		}
	}
		
	/** Update user information*/
	void updateUserInfo(String[] user, String[] userInfo, boolean state) {
		User loginUser = new User(user[1], user[2], userInfo[0], Integer.parseInt(user[3]), state);
		info.put(user[1], loginUser);
		//System.out.println(info.toString());
	}
	
	/** Sending login ack to client */
	void SendLoginAck(Socket socket, String ack) {
		// Information format setting
		byte[] bytesToSend = ack.getBytes();

		try {
			Communicate.tcpSend(socket, bytesToSend);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	void printList() {
		Set<String> keySet = info.keySet();
		Iterator it = keySet.iterator();
		StringBuilder list = new StringBuilder();
		
		// Print user list
		User current;
		while( it.hasNext()) {
			current = info.get(it.next());
			list.append("|---User: " + current.getUsername() + "\r\n    |---Password: " + current.getPassword() 
				+ "\r\n    |---Current IP: " + current.getIp() + "\r\n    |---Current Listening Port: " + current.getPort() 
				+ "\r\n    |---IsOnline(Ip and Listening Port are invalid if Offline); " + current.getState() + "\r\n");
		}
		System.out.println("User List:\r\n" + list.toString());
	}
	
	public static void main(String[] args) throws Exception {
		ServerSocket ssocket = new ServerSocket(10001);
		DatagramSocket dgsocket = new DatagramSocket(10000);
		ChatServer server = new ChatServer(ssocket, dgsocket);
		server.handleLogInOut();
		ssocket.close();
	}
}
