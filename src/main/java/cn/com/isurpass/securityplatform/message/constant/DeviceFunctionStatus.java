package cn.com.isurpass.securityplatform.message.constant;

public enum DeviceFunctionStatus 
{
	malfuncation(-1);
	
	private int status ;

	private DeviceFunctionStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
	
}
