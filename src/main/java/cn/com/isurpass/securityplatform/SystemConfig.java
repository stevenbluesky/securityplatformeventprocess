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
	
	private String alarmoneplatformid;
	private String alarmoneplatformip;
	private int alarmoneplatformport;
	private String alarmoneplatformname;
	
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
	public String getAlarmoneplatformid() {
		return alarmoneplatformid;
	}
	public void setAlarmoneplatformid(String alarmoneplatformid) {
		this.alarmoneplatformid = alarmoneplatformid;
	}
	public String getAlarmoneplatformip() {
		return alarmoneplatformip;
	}
	public void setAlarmoneplatformip(String alarmoneplatformip) {
		this.alarmoneplatformip = alarmoneplatformip;
	}
	public int getAlarmoneplatformport() {
		return alarmoneplatformport;
	}
	public void setAlarmoneplatformport(int alarmoneplatformport) {
		this.alarmoneplatformport = alarmoneplatformport;
	}
	public String getAlarmoneplatformname() {
		return alarmoneplatformname;
	}
	public void setAlarmoneplatformname(String alarmoneplatformname) {
		this.alarmoneplatformname = alarmoneplatformname;
	}
	
	
}
