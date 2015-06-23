/** Class for incoming infomation buffer */
class MessageBuffer {
	private String name;
	private int indexOfShowed;
	private StringBuilder msgBuf;
	
	public MessageBuffer(String name) {
		this.name = name;
		this.indexOfShowed = 0;
		this.msgBuf = new StringBuilder();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setIndexOfShowed(int indexOfShowed) {
		this.indexOfShowed = indexOfShowed;
	}
	
	public int getIndexOfShowed() {
		return indexOfShowed;
	}
	
	public void setBuffer(StringBuilder msgBuf) {
		this.msgBuf = msgBuf;
	}
	
	public StringBuilder getBuffer() {
		return msgBuf;
	}
}