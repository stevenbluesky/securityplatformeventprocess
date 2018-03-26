package cn.com.isurpass.securityplatform.message.constant;

public enum DoorlockStatus 
{
	open(1),
	close(255);
	
	private int status ;

	private DoorlockStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	
}
