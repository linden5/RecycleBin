import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFrame;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.*;

/** Static methods dealing with different tcp udp transmission */
public class Communicate {
	/** UDP send */
	public static void udpSend(DatagramSocket ds, byte[] buf, String ip, int port) throws Exception {
		DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), port);
		ds.send(dp);
	}
	
	/** TCP send */
	public static void tcpSend(Socket s, byte[] buf) throws Exception {
		OutputStream os = s.getOutputStream();
		os.write(buf);
		os.flush();
	}
	
	/** UDP Receive */
	public static String[] udpReceive(DatagramSocket ds, byte[] buf) throws Exception {
		DatagramPacket dp = new DatagramPacket(buf, buf.length);
		ds.receive(dp);
		
		String[] result = new String[3];
		result[0] = dp.getAddress().getHostAddress();
		result[1] = dp.getPort() + "";
		result[2] = new String(dp.getData(), 0, dp.getLength());
		return result;
	}
	
	/** TCP Receive */
	public static String[] tcpReceive(Socket s, byte[] buf) throws Exception {
		InputStream in = s.getInputStream();
		int len = in.read(buf);
		
		String[] result = new String[3];
		result[0] = s.getInetAddress().getHostAddress();
		result[1] = s.getPort() + "";
		result[2] = new String(buf, 0, len);
		return result;
	}
	
	
	/** Following 3 methods are specially used for dealing with Object transmission */
	/** TCP receive byte */
	public static byte[] tcpReceiveByte(Socket s, byte[] buf) throws Exception {
		InputStream in = s.getInputStream();
		int len = in.read(buf);
		return buf;
	}
	
	/** Object to byte */
	public static byte[] object2Byte(Object obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}
	
	/** Byte to Object */
	public static Object byte2Object(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		bais.close();
		return obj;
	}
	
	/** set frame*/
	public static void setFrame(JFrame frame, String title, int posx, int posy, int width, int height, int operation) {
		frame.setBounds(posx, posy, width, height);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(operation);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	/** Get date */
	public static String getShowDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/** Save Chat log */
	public static void saveLog(String filename, String chatlog) {
		StringBuilder sb = new StringBuilder();
		String read;
		File f = new File(filename);
		
		try {
			// Read
			if (!f.exists())
				f.createNewFile();
			BufferedReader bufr = new BufferedReader(new FileReader(filename));
			while((read = bufr.readLine()) != null) {
				sb.append(read + "\r\n");
			}
			sb.append(chatlog);
			read = sb.toString();
			bufr.close();

			// Write
			BufferedWriter bufw = new BufferedWriter(new FileWriter(filename));
			bufw.write(read, 0, read.length());
			bufw.flush();
			bufw.close();
		} catch(IOException ioe) {
			System.out.println("An exception occured during saving the log file.");
		}
	}
	
	/** UDP send message, use udpSend method */
	public static void udpSendMsg(String toSend, String destAddress, int destPort) {
		byte[] byteToSend = toSend.getBytes();
		DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			udpSend(socket, byteToSend, destAddress, destPort);
			socket.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/** Message buffer operation */
	public static BufferAndStringArray push2Buffer(Hashtable<String, MessageBuffer> msgBuffer, DatagramSocket dgsocket) {
		byte[] buf = new byte[1024];
		String[] incomeMessage = new String[3];
		String[] msg = null;
		MessageBuffer current;

		try {
			//System.out.println("Receive Communicate");
			incomeMessage = udpReceive(dgsocket, buf);
			//System.out.println("Receive Communicate finish");
			msg = incomeMessage[2].split(" +");
			
			//System.out.println("incoming save Savelog finish");
			current = bufferProcess(msg, incomeMessage, msgBuffer);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return new BufferAndStringArray(current, new String[]{msg[0], incomeMessage[2]});
	}
	
	/** Buffer processsing */
	public static MessageBuffer bufferProcess(String[] msg, String[] incomeMessage, Hashtable<String, MessageBuffer> msgBuffer) {
		MessageBuffer current;
				
		if (!msgBuffer.containsKey(msg[0])) {
			current = new MessageBuffer(msg[0]);
			current.getBuffer().append(incomeMessage[2]);
			msgBuffer.put(msg[0], current);
		} else {
			current = msgBuffer.get(msg[0]);
			current.getBuffer().append(incomeMessage[2]);
		}
		System.out.println("Incoming-----IP: " + incomeMessage[0] + " \\ Sending Port: " + incomeMessage[1] + "-----\r\n" + incomeMessage[2]);
		return current;
	}
	
	static class BufferAndStringArray {
		private MessageBuffer msgBuf;
		private String[] strArr;
		
		public BufferAndStringArray(MessageBuffer msgBuf, String[] strArr) {
			this.msgBuf = msgBuf;
			this.strArr = strArr;
		}
		
		public MessageBuffer getMsgBuffer() {
			return msgBuf;
		}
		
		public String[] getStringArray() {
			return strArr;
		}
		
		public void setMsgBuffer(MessageBuffer msgBuf) {
			this.msgBuf = msgBuf;
		}
		
		public void setStringArray(String[] strArr) {
			this.strArr = strArr;
		}
	}
}