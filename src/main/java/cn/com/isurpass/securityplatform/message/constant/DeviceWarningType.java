package cn.com.isurpass.securityplatform.message.constant;

public enum DeviceWarningType 
{
	tampler(251);
	
	private int code ;

	private DeviceWarningType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
