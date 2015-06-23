import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class ChatWindow extends JFrame implements ActionListener {
// GUI
	private JTextArea textAreaShow = new JTextArea(null, 19, 30);
	private JScrollPane scrollPane = new JScrollPane(textAreaShow);
	private JTextArea textAreaInput = new JTextArea(null, 5, 30);
	private JButton sendButton = new JButton("Send");
	private JButton logButton = new JButton("ChatLog");
	private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JPanel namePanel = new JPanel(new BorderLayout());
	private JPanel chatPanel = new JPanel(new BorderLayout());
	private JPanel sendPanel = new JPanel(new BorderLayout());
	private JLabel nameLabel;
	private ChatLog chatlog = null;

	private String destAddress;
	private int destPort;
	private String destName;
	private String myName;
	private Lock lock;
	
	public ChatWindow(String destName, String destAddress, int destPort, String myName, Lock lock) {
		this.destName = destName;
		this.destAddress = destAddress;
		this.destPort = destPort;
		this.myName = myName;
		this.lock = lock;
		
		nameLabel = new JLabel(destName);

		buttonPanel.add(sendButton);
		buttonPanel.add(logButton);
		
		namePanel.add(nameLabel, BorderLayout.WEST);
		
		chatPanel.add(namePanel, BorderLayout.NORTH);
		chatPanel.add(scrollPane, BorderLayout.CENTER);
		textAreaShow.setEditable(false);
		
		sendPanel.add(buttonPanel, BorderLayout.NORTH);
		sendPanel.add(textAreaInput, BorderLayout.CENTER);
				
		getContentPane().add(chatPanel, BorderLayout.NORTH);
		getContentPane().add(sendPanel, BorderLayout.CENTER);
		
		textAreaInput.setLineWrap(true);
		textAreaShow.setLineWrap(true);
		
		sendButton.addActionListener(this);
		logButton.addActionListener(this);
		this.addWindowListener(new MyWindowListener());
		textAreaInput.addKeyListener(new MyKeyListener());
		
		Communicate.setFrame(this, "Chatting with...", 300, 100, 600, 500, JFrame.HIDE_ON_CLOSE);
	}
		
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}	
	
	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			sendMessage();
		}
		
		if (e.getSource() == logButton) {
			if (chatlog != null)
				chatlog.dispose();
			chatlog = new ChatLog(myName, destName);
		}
	}
	
	/** Send Message */
	void sendMessage() {
		String content = textAreaInput.getText();
		// Can not send empty string
		if (content.length() == 0) {
			JOptionPane.showMessageDialog(null, "Sending Text cannot be blank");
			return;
		}
		
		// Send message
		String toSend = myName + "  " + Communicate.getShowDate() + "\r\n  " + textAreaInput.getText() + "\r\n";
		textAreaShow.append(toSend);
		if (destAddress.equals(ChatClient.serverAddress) && destPort == ChatClient.serverUDPPort)
			toSend = toSend + "  " + destName;
		System.out.println(toSend);
		Communicate.udpSendMsg(toSend, destAddress, destPort);
		
		// Message sending finished, save log and set text area
		System.out.println( myName + " chat with  " + destName + "( destination: " + "\\" + destAddress + "\\" + destPort + ")");
		textAreaInput.setText("");
		lock.lock();
		Communicate.saveLog(myName + "_" + destName + ".txt", toSend);
		lock.unlock();
		setScrollBar();
	}
	
	public void appendMessage(String message) {
		textAreaShow.append(message);
	}
	
	public void setScrollBar() {
		JScrollBar jsb = scrollPane.getVerticalScrollBar();
		int max = jsb.getMaximum();
		jsb.setValue(max);
	}
	
	class MyWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent we) {
			textAreaShow.setText("");
		}
	}
	
	class MyKeyListener extends KeyAdapter {
		private boolean crtlPressed = false;
		private boolean enterPressed = false;
		
		@Override
		public void keyPressed(KeyEvent ke) {
			switch (ke.getKeyCode()) {
				case KeyEvent.VK_ENTER:		
					enterPressed = true;
					break;
				case KeyEvent.VK_CONTROL:	
					crtlPressed = true;
					break;							
			}
			
			if (crtlPressed && enterPressed) {
				sendMessage();
				enterPressed = false;
				crtlPressed = false;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent ke) {
			switch (ke.getKeyCode()) {
				case KeyEvent.VK_ENTER:		
					enterPressed = false;
					break;
				case KeyEvent.VK_CONTROL:	
					crtlPressed = false;
					break;							
			}
		}
	}
}

/** ChatLog class */
class ChatLog extends JFrame implements ActionListener {
	private JTextArea chatlog = new JTextArea();
	private JScrollPane logScrollPane = new JScrollPane(chatlog);
	private JButton delButton = new JButton("Delete");
	private JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JLabel label;
	
	private String myName;
	private String destName;
	
	public ChatLog(String myName, String destName) {
		this.label = new JLabel("Chat Log with " + destName);
		this.btnPanel.add(delButton);
		this.labelPanel.add(label);
		this.myName = myName;
		this.destName = destName;
		
		chatlog.setEditable(false);
		chatlog.setLineWrap(true);		
		getContentPane().add(labelPanel, BorderLayout.NORTH);
		getContentPane().add(logScrollPane, BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
				
		delButton.addActionListener(this);

		ReadLog();
		Communicate.setFrame(this, "Chat Log", 400, 50, 400, 300, JFrame.DISPOSE_ON_CLOSE);
	}
	
	void ReadLog() {
		StringBuilder sb = new StringBuilder();
		String read;
		try {
			BufferedReader bufr = new BufferedReader(new FileReader(myName + "_" + destName + ".txt"));
			while((read = bufr.readLine()) != null) {
				sb.append(read + "\r\n");
			}
			read = sb.toString();
			bufr.close();
			chatlog.append(read);
		} catch(IOException ioe) {
			System.out.println("An exception occured during reading the log file.");
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == delButton) {
			try {
				new File(myName + "_" + destName + ".txt").delete();
				JOptionPane.showMessageDialog(null, "Chatlog Deleted!");
				chatlog.setText("");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Deleting Failed!");
			}
		}
	}
}

