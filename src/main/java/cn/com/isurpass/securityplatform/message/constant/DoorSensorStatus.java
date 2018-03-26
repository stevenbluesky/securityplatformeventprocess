package cn.com.isurpass.securityplatform.message.constant;

public enum DoorSensorStatus 
{
	open(255),
	close(0);
	
	private int status ;

	private DoorSensorStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
}
