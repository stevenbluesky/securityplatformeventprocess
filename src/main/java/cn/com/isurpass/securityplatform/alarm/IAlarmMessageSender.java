package cn.com.isurpass.securityplatform.alarm;

public interface IAlarmMessageSender 
{
	boolean sendAlarmMessage(String msg , String groupid , String usercode, int zone);
}
