package cn.com.isurpass.securityplatform.message.constant;

public enum SystemParameterKey {

	lastSynId("Las");
	
	private String key ;

	private SystemParameterKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
}
