package cn.com.isurpass.securityplatform.alarm;

import java.util.HashMap;
import java.util.Map;

public class AlarmplatformConnectionManager 
{
	private static Map<String , IAlarmMessageSender> connectionmap = new HashMap<>();
	private static AlarmplatformConnectionManager instance = new AlarmplatformConnectionManager();
	
	public static AlarmplatformConnectionManager getInstance()
	{
		return instance ;
	}
	
	private AlarmplatformConnectionManager()
	{
		
	}
	
	public void putChannelHandlerContext(String name , IAlarmMessageSender sender)
	{
		connectionmap.put(name, sender);
	}
	
	public boolean sendMessage(String name , String msg , String usercode, String zone)
	{
		IAlarmMessageSender ams = connectionmap.get(name);
		if ( ams == null )
			return false ;
		return ams.sendAlarmMessage(msg, usercode,zone);
	}
}
