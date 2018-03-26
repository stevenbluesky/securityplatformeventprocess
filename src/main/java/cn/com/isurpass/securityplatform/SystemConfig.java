package cn.com.isurpass.securityplatform;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cn.com.isurpass.eventprocess.config")
public class SystemConfig 
{
	private  String coreserverurl ;
	private  int coreserverport ;
	private  String eventpushserverurl;
	private  String thirdpartcode ;
	private  String thirdpartpw ;
	
	public  String getCoreserverurl() {
		return coreserverurl;
	}
	public  void setCoreserverurl(String coreserverurl) {
		this.coreserverurl = coreserverurl;
	}
	public  String getThirdpartcode() {
		return thirdpartcode;
	}
	public  void setThirdpartcode(String thirdpartcode) {
		this.thirdpartcode = thirdpartcode;
	}
	public  String getThirdpartpw() {
		return thirdpartpw;
	}
	public  void setThirdpartpw(String thirdpartpw) {
		this.thirdpartpw = thirdpartpw;
	}
	public  int getCoreserverport() {
		return coreserverport;
	}
	public  void setCoreserverport(int coreserverport) {
		this.coreserverport = coreserverport;
	}
	public  String getEventpushserverurl() {
		return eventpushserverurl;
	}
	public  void setEventpushserverurl(String eventpushserverurl) {
		this.eventpushserverurl = eventpushserverurl;
	}
	
	
}
