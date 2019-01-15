package cn.com.isurpass.securityplatform.alarm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.message.vo.Event;

public class AlarmplatformConnectionManager 
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AlarmplatformConnectionManager.class);
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
	
	public boolean sendMessage(String name , Event event ,UserPO user,int zone, String alarmvalue)
	{
		IAlarmMessageSender ams = connectionmap.get(name);
		if ( ams == null )
			return false ;
		return ams.sendAlarmMessage(event,user,zone,alarmvalue);
	}
}
