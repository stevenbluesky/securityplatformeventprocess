package cn.com.isurpass.securityplatform.alarm;

public interface IAlarmMessageSender 
{
	boolean sendAlarmMessage(String msg , String usercode, String zone);
}
