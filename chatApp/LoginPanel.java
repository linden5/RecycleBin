import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;

public class LoginPanel extends JFrame implements ActionListener{
	private JPanel jplText = new JPanel(new GridLayout(2, 2, 10, 10));
	private JPanel jplButton = new JPanel(new GridLayout(2, 2, 10, 10));
	private ResetFrame frameReset = null;
	
	private JLabel jlbName = new JLabel("Username:");
	private JTextField jtfName = new JTextField();
	private JLabel jlbPassword = new JLabel("Password:");
	private JPasswordField jtfPassword = new JPasswordField();
	
	private JButton jbtnCommit = new JButton("Log in");
	private JButton jbtnCancel = new JButton("Cancel");
	private JButton jbtnSignup = new JButton("Sign Up");
	private JButton jbtnReset = new JButton("Reset Password");

	
	private String username = null;
	private String password = null;
	
	/** Login Panel constructor*/
	public LoginPanel() {
		jlbName.setHorizontalAlignment(SwingConstants.CENTER);
		jlbPassword.setHorizontalAlignment(SwingConstants.CENTER);
		jplText.add(jlbName);
		jplText.add(jtfName);
		jplText.add(jlbPassword);
		jplText.add(jtfPassword);
		
		jplButton.add(jbtnCommit);
		jplButton.add(jbtnSignup);
		jplButton.add(jbtnReset);
		jplButton.add(jbtnCancel);

		jbtnCommit.addActionListener(this);
		jbtnCancel.addActionListener(this);
		jbtnSignup.addActionListener(this);
		jbtnReset.addActionListener(this);
		MyKeyListener kl = new MyKeyListener();
		jtfName.addKeyListener(kl);
		jtfPassword.addKeyListener(kl);

		getContentPane().add(jplText, BorderLayout.CENTER);
		getContentPane().add(jplButton, BorderLayout.SOUTH);
		
		Communicate.setFrame(this, "Log In", 500, 300, 300, 160, JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		username = jtfName.getText();
		password = new String(jtfPassword.getPassword());
		// Action for different button
		if (e.getSource() == jbtnCommit && checkInput())
			Login("Login");
		if (e.getSource() == jbtnCancel)
			System.exit(0);
		if (e.getSource() == jbtnSignup && checkInput())
			Login("Create");
		if (e.getSource() == jbtnReset) {
			if (frameReset != null)
				frameReset.dispose();
			frameReset = new ResetFrame();
		}
	}
	
	/** Blank username or password is not allowed */
	boolean checkInput() {
		if (username.length() == 0) {
			JOptionPane.showMessageDialog(null, "Username cannot be blank");
			return false;
		} else if (password.length() == 0) {
			JOptionPane.showMessageDialog(null, "Password cannot be blank");
			return false;
		} else if (!username.matches("[\\w\\d]{6,}")) {
			JOptionPane.showMessageDialog(null, "Username at least 6 characters, and can only be word characters or digits");
			return false;
		} else if (!password.matches("[\\w\\d@!]{6,}")) {
			JOptionPane.showMessageDialog(null, "Password at least 6 characters, and can only be word characters, digits, @ or !");
			return false;
		} 
		return true;
	}

	/** Method to send login information*/
	void Login(String type) {
		// Login information format setting
		System.out.println("Server address---IP: " + ChatClient.serverAddress + " \\ Port: " + ChatClient.serverPort);
		byte[] bytesToSend = (type + ":" + username + ":" + password + ":0").getBytes();
		byte[] buf = new byte[1024];
		Socket socket;
		try {
			// Send login information to the server
			socket = new Socket(ChatClient.serverAddress, ChatClient.serverPort);
			Communicate.tcpSend(socket, bytesToSend);
			
			// Print information returned from the server
			String[] serverReturn = Communicate.tcpReceive(socket, buf);
			System.out.println("Server reply---IP: " + serverReturn[0] + " \\ Port: " + serverReturn[1] + " \\ Info: " + serverReturn[2]);
			socket.close();		
			// Login succeeded
			if ("OK".equals(serverReturn[2])) {
				// Invoke userlist
				this.dispose();
				OnlineList list = new OnlineList(username, password);
			// Login failed	
			} else {
				JOptionPane.showMessageDialog(null, serverReturn[2]);
				if ("Password reseted!".equals(serverReturn[2]))
					frameReset.dispose();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't connect to server!");
		}
	}
	
	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			username = jtfName.getText();
			password = new String(jtfPassword.getPassword());
			if (ke.getKeyCode() == KeyEvent.VK_ENTER)
				Login("Login");	
		}
	}
	
	class ResetFrame extends JFrame implements ActionListener {
		private JPanel jplReset = new JPanel(new GridLayout(3, 2, 10, 10));
			
		private JLabel resetName = new JLabel("Username:");
		private JTextField resetNameField = new JTextField();
		private JLabel resetOldPassword = new JLabel("Old Password:");
		private JPasswordField resetOldPasswordField = new JPasswordField();
		private JLabel resetNewPassword = new JLabel("New Password:");
		private JPasswordField resetNewPasswordField = new JPasswordField();
		private JButton jbtnResetOK = new JButton("Reset");
		
		private String newPassword = null;
		
		public ResetFrame() {
					
			jplReset.add(resetName);
			jplReset.add(resetNameField);
			jplReset.add(resetOldPassword);
			jplReset.add(resetOldPasswordField);
			jplReset.add(resetNewPassword);
			jplReset.add(resetNewPasswordField);
			
			this.add(jplReset, BorderLayout.CENTER);
			this.add(jbtnResetOK, BorderLayout.SOUTH);
			
			jbtnResetOK.addActionListener(this);
			
			ResetHandler rh = new ResetHandler();
			resetNameField.addKeyListener(rh);
			resetOldPasswordField.addKeyListener(rh);
			resetNewPasswordField.addKeyListener(rh);
			Communicate.setFrame(this, "Reset Password", 500, 300, 250, 150, JFrame.DISPOSE_ON_CLOSE);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sendReset();
		}
		
		void sendReset() {
			username = resetNameField.getText();
			password = new String(resetOldPasswordField.getPassword());
			newPassword = new String(resetNewPasswordField.getPassword());
			if (checkReset()) {
				password = password + "&" + newPassword;
				Login("Reset");
			}
		}
			
		/** Blank username or password is not allowed */
		boolean checkReset() {
			if (newPassword.length() == 0) {
				JOptionPane.showMessageDialog(null, "New password cannot be blank");
				return false;
			} else if (!newPassword.matches("[\\w\\d@!]{6,}")) {
				JOptionPane.showMessageDialog(null, "New password at least 6 characters, and can only be word characters, digits, @ or !");
				return false;
			} 
			return checkInput();
		}
		
		class ResetHandler extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					sendReset();
			}
		}
	}
}

