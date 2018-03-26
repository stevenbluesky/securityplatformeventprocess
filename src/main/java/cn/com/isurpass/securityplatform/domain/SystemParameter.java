package cn.com.isurpass.securityplatform.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "systemparameter")
public class SystemParameter 
{
	@Id
	private int systemparameterid;
	private String paramkey;
	private Integer intvalue;
	private String stringvalue;

	public SystemParameter(String paramkey, Integer intvalue, String stringvalue) {
		super();
		this.paramkey = paramkey;
		this.intvalue = intvalue;
		this.stringvalue = stringvalue;
	}
	public SystemParameter() {
		super();
	}
	public int getSystemparameterid() {
		return systemparameterid;
	}
	public void setSystemparameterid(int systemparameterid) {
		this.systemparameterid = systemparameterid;
	}
	public String getParamkey() {
		return paramkey;
	}
	public void setParamkey(String paramkey) {
		this.paramkey = paramkey;
	}
	public Integer getIntvalue() {
		return intvalue;
	}
	public void setIntvalue(Integer intvalue) {
		this.intvalue = intvalue;
	}
	public String getStringvalue() {
		return stringvalue;
	}
	public void setStringvalue(String stringvalue) {
		this.stringvalue = stringvalue;
	}
}
