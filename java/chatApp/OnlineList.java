import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class OnlineList extends JFrame implements Runnable {
	private Hashtable<String, User> info;
	private Hashtable<String, ChatWindow> win;
	private Hashtable<String, MessageBuffer> msgBuffer;
	private Hashtable<String, MessageNotification> noteWin;
	
	private String myName;
	private String password;
	private int listenPort;
	private DatagramSocket dgsocket;
	private Socket socket;
		
	private JLabel jlbName;
	private JButton jbtnReset;
	private JList<String> jlOnline;
	private JScrollPane scrollPane;
	private JPanel jplName;
	private ResetFrame resetFrame;
	
	private Lock lock = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	/** Constructor */
	public OnlineList(String name, String password) throws Exception {
		this.info = null;
		this.win = new Hashtable<String, ChatWindow>();
		this.msgBuffer = new Hashtable<String, MessageBuffer>();
		this.noteWin = new Hashtable<String, MessageNotification>();
		
		this.myName = name;
		this.password = password;
		this.listenPort = findAvailPort();
		connectServer();

		jlOnline = new JList<String>();
		getReturnedUserList();
		printList();
		generateList();
		
		scrollPane = new JScrollPane(jlOnline);
		jlbName = new JLabel(name);
		jbtnReset = new JButton("Reset Password");
		jplName = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
		resetFrame = null;
		jplName.add(jlbName);
		jplName.add(jbtnReset);
		
		scrollPane.setSize(100,100);
		getContentPane().add(jplName, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		Communicate.setFrame(this, "User List", 1100, 100, 250, 500, JFrame.EXIT_ON_CLOSE);		
		
		jlOnline.addMouseListener(new myMouseListener());
		jbtnReset.addActionListener(new myActionListener());
		this.addWindowListener(new myWindowListener());
		new Thread(this).start();
		new ReceiveUDP().start();
	}
	
	@Override
	public void run() {
		byte[] bytesToSend = ("Update:" + myName + ":" + password + ":" + listenPort).getBytes();
		byte[] buf = new byte[1024];
		Hashtable<String, User> returnInfo = null;

		while(true) {
			//System.out.println(Thread.currentThread().getName()+"TCP updating");
			try {
				//socket.connect(sktAdrs);
				Thread.sleep(3000);
				returnInfo = listFetch(bytesToSend, buf);			
				if (returnInfo.equals(info)) {
					continue;
				}
			} catch(Exception ex) {
				//ex.printStackTrace();
				connectServer();
				run();
			}
			info = returnInfo;
			generateList();
		}
	}
	
	/** Listening for UDP incoming transmission */
	class ReceiveUDP extends Thread {
		@Override
		public void run() {
			// Listening for incoming message
			String[] incomeMessage = new String[3];
			MessageBuffer current;
			ChatWindow correspondWin;
			String[] msg;
			while(true) {
				//System.out.println(Thread.currentThread().getName()+"UDP listening");
				Communicate.BufferAndStringArray bufsa = Communicate.push2Buffer(msgBuffer, dgsocket);
				//System.out.println(Thread.currentThread().getName()+"UDP listening finish");
				current = bufsa.getMsgBuffer();
				msg = bufsa.getStringArray();
				
				// Write chatlog
				lock.lock();
				Communicate.saveLog(myName +"_" + msg[0] + ".txt", msg[1]);
				lock.unlock();
			
				if (msg != null){
					// Append message to window
					if (win.containsKey(msg[0])) {
						correspondWin = win.get(msg[0]);
						if (!correspondWin.isShowing())
							showMessageNotification(msg[0]);
						else {
							lock2.lock();
							correspondWin.appendMessage(current.getBuffer().substring(current.getIndexOfShowed()));
							current.setIndexOfShowed(current.getBuffer().length());
							lock2.unlock();
							correspondWin.setScrollBar();
						}
					} else {
						showMessageNotification(msg[0]);
					}
				}
			}
		}
	}
	
	/** Find available port*/
	int findAvailPort() {
		int port = (int)(Math.random() * 100) + 10000;
		try {
			dgsocket = new DatagramSocket(port);
		} catch(SocketException e) {
			findAvailPort();
		}
		return port;
	}
	
	void getReturnedUserList() {
		byte[] bytesToSend = ("Update:" + myName + ":" + password + ":" + listenPort).getBytes();
		byte[] buf = new byte[1024];
		try {
			info = listFetch(bytesToSend, buf);	
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	Hashtable<String, User> listFetch(byte[] bytesToSend, byte[] buf) throws Exception {
		Communicate.tcpSend(socket, bytesToSend);
		byte[] byteInfo = Communicate.tcpReceiveByte(socket, buf);
		return (Hashtable<String, User>)Communicate.byte2Object(byteInfo);
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
	
	void generateList() {
		Set<String> keySet = info.keySet();
		String[] online = keySet.toArray(new String[0]);
		String[] listname = new String[online.length - 1];
		for (int j = 0, i = 0; i < listname.length; i++) {
			if (listname[i] != null)
				continue;
			for (; j < online.length;) {
				if (online[j].equals(myName)) {
					j++;
					continue;
				}
				listname[i] = online[j] + "     " + (info.get(online[j]).getState() ? "online" : "offline");
				j++;
				break;
			}
		}
		jlOnline.setListData(listname);
	}
	
	void connectServer() {
		try {
			this.socket = new Socket(ChatClient.serverAddress, ChatClient.serverPort);
		} catch(Exception e) {
			connectServer();
		}
	}
	
	class myWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			byte[] bytesToSend = ("Logout:" + myName + ":" + password + ":" + listenPort).getBytes();
			byte[] buf = new byte[1024];
			try {
				// Send update information to the server
				Communicate.tcpSend(socket, bytesToSend);
				socket.close();
			} catch(Exception ex) {
				System.out.println("Connection Error!");
			} 
			System.exit(0);
		}
	}
	
	/** Double click to creat chat window */
	class myMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				try {
					String selection = ((JList<String>)e.getSource()).getSelectedValue();
					String[] tmp = selection.split("     ");
					if ("offline".equals(tmp[1]))
						JOptionPane.showMessageDialog(null, "The user is offline and will get message when it is online!");
					showChatWindow(tmp[0]);
					hideMessageNotification(tmp[0]);
				} catch(Exception ex) {
					
				}
			}
		}
	}
	
	class myActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (resetFrame != null)
				resetFrame.dispose();
			resetFrame = new ResetFrame();
		}
	}
	
	/** Class for reseting password in this interface */
	class ResetFrame extends JFrame implements ActionListener {
		private JPanel jplReset = new JPanel(new GridLayout(3, 2, 10, 10));
			
		private JLabel resetOldPassword = new JLabel("Old Password:");
		private JPasswordField resetOldPasswordField = new JPasswordField();
		private JLabel resetNewPassword = new JLabel("New Password:");
		private JPasswordField resetNewPasswordField = new JPasswordField();
		private JButton jbtnResetOK = new JButton("Reset");
		
		private String oldPassword = null;
		private String newPassword = null;
		
		public ResetFrame() {
					
			jplReset.add(resetOldPassword);
			jplReset.add(resetOldPasswordField);
			jplReset.add(resetNewPassword);
			jplReset.add(resetNewPasswordField);
			
			this.add(jplReset, BorderLayout.CENTER);
			this.add(jbtnResetOK, BorderLayout.SOUTH);
			
			jbtnResetOK.addActionListener(this);
			MyKeyListener mkl = new MyKeyListener();
			resetOldPasswordField.addKeyListener(mkl);
			resetNewPasswordField.addKeyListener(mkl);
			
			Communicate.setFrame(this, "Reset Password", 300, 100, 250, 150, JFrame.DISPOSE_ON_CLOSE);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			reset();
		}
			
		/** Blank username or password is not allowed */
		boolean checkReset() {
			if (newPassword.length() == 0 || oldPassword.length() == 0) {
				JOptionPane.showMessageDialog(null, "Password cannot be blank");
				return false;
			} else if (!newPassword.matches("[\\w\\d@!]{6,}") || !oldPassword.matches("[\\w\\d@!]{6,}")) {
				JOptionPane.showMessageDialog(null, "Password at least 6 characters, and can only be word characters, digits, @ or !");
				return false;
			} 
			return true;
		}
		
		void reset() {
			this.oldPassword = new String(resetOldPasswordField.getPassword());
			this.newPassword = new String(resetNewPasswordField.getPassword());
			if (checkReset())
				sendReset(this.oldPassword, this.newPassword);
		}
		
		void sendReset(String oldPassword, String newPassword) {
			// Login information format setting
			String resetWord = oldPassword + "&" + newPassword;
			byte[] bytesToSend = ("Reset:" + myName + ":" + resetWord + ":" + listenPort).getBytes();
			byte[] buf = new byte[1024];
			
			try {
				// Send login information to the server
				Communicate.tcpSend(socket, bytesToSend);
				
				// Print information returned from the server
				String[] serverReturn = Communicate.tcpReceive(socket, buf);
				System.out.println("Server reply---IP: " + serverReturn[0] + " \\ Port: " + serverReturn[1] + " \\ Info: " + serverReturn[2]);
				JOptionPane.showMessageDialog(null, serverReturn[2]);
				if ("Password reseted!".equals(serverReturn[2]))
					password = newPassword;
					this.dispose();
			} catch(Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unable to connect to server!");
			}
		}
		
		class MyKeyListener extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					reset();
			}
		}
	}
	
	/** Class for message notification window */
	class MessageNotification extends JFrame {
		private String nameIncoming;
		private JLabel messagePopup;
		
		MessageNotification(String nameIncoming) {
			this.nameIncoming = nameIncoming;
			this.messagePopup = new JLabel("You've got message from " + nameIncoming);
			getContentPane().add(messagePopup);
			this.addMouseListener(new NotifyMouseListener(this));
			Communicate.setFrame(this, "New Message", 1100, 600, 200, 100, JFrame.DISPOSE_ON_CLOSE);
		}
		
		class NotifyMouseListener extends MouseAdapter {
			private JFrame jf;
			
			public NotifyMouseListener(JFrame jf) {
				this.jf = jf;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				showChatWindow(nameIncoming);
				jf.dispose();
			}
		}
	}
	

	
	void showMessageNotification(String name) {
		MessageNotification mnWin;
		if (noteWin.containsKey(name))
			noteWin.get(name).dispose();
		mnWin = new MessageNotification(name);
		noteWin.put(name, mnWin);
	}
	
	void hideMessageNotification(String name) {
		if (noteWin.containsKey(name)) {
			MessageNotification mnWin = noteWin.get(name);
			mnWin.dispose();
		}
	}
	
	void showChatWindow(String name) {
		ChatWindow cw;
		User user = info.get(name);

		if (win.containsKey(name)) {
			cw = win.get(name);
			if (!user.getState()) {
				cw.setDestAddress(ChatClient.serverAddress);
				cw.setDestPort(ChatClient.serverUDPPort);
			} else {
				cw.setDestAddress(user.getIp());
				cw.setDestPort(user.getPort());
			}
			cw.toFront();
			cw.setVisible(true);
		} else {
			if (!user.getState())
				cw = new ChatWindow(user.getUsername(), ChatClient.serverAddress, ChatClient.serverUDPPort, myName, lock);
			else
				cw = new ChatWindow(user.getUsername(), user.getIp(), user.getPort(), myName, lock);
			win.put(name, cw);
		}
		
		// Check if there are message not shown yet
		if (msgBuffer.containsKey(name)) {
			MessageBuffer msgbuf = msgBuffer.get(name);
			int indexShowed = msgbuf.getIndexOfShowed();
			StringBuilder sb = msgbuf.getBuffer();
			lock2.lock();
			if (indexShowed < sb.length()) {
				cw.appendMessage(sb.substring(indexShowed));
				msgbuf.setIndexOfShowed(msgbuf.getBuffer().length());
				cw.setScrollBar();
			}
			lock2.unlock();
		}
	}
}



