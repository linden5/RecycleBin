import java.net.*;

public class ChatClient {
	private LoginPanel loginPanel;

	static final String serverAddress = "127.0.0.1";
	static final int serverPort = 10001;
	static final int serverUDPPort = 10000;
		
	/** Constructor */
	public ChatClient() {
		this.loginPanel = new LoginPanel();
	}
	
	public static void main(String[] args) throws Exception {
		new ChatClient();
	}
}