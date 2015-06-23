import java.io.Serializable;

/**User class*/
public class User implements Serializable {
	private String username;
	private String password;
	private String ip;
	private int port;
	private boolean online;
	
	public User(String username, String password, String ip, int port, boolean online) {
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.online = online;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean getState() {
		return online;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setState(boolean online) {
		this.online = online;
	}
}