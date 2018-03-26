package cn.com.isurpass.securityplatform.message.constant;

public enum InfraredDetectorStatus 
{
	moveout(0),
	movein(255);
	
	private int status ;

	private InfraredDetectorStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
