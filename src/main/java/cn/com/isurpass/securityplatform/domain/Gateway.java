package cn.com.isurpass.securityplatform.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gateway")
public class Gateway {
	
	@Id
	private String deviceid;
	private String name;
	private String appaccount;
	private Integer status; 
	private String model;
	private String firmwareversion;
	private Integer battery; 
	private Date createtime;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFirmwareversion() {
		return firmwareversion;
	}
	public void setFirmwareversion(String firmwareversion) {
		this.firmwareversion = firmwareversion;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getAppaccount() {
		return appaccount;
	}
	public void setAppaccount(String appaccount) {
		this.appaccount = appaccount;
	}
	
	
}
