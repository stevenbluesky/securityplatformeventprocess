package cn.com.isurpass.securityplatform.message.constant;

public enum SwitchStatus {

	close(0),
	open(255);
	
	private int status ;

	private SwitchStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
