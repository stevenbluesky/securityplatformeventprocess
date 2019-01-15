package cn.com.isurpass.securityplatform.alarmtwo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.SystemConfig;
import cn.com.isurpass.securityplatform.alarm.AlarmplatformConnectionManager;
import cn.com.isurpass.securityplatform.alarmone.AlarmoneHeartBeatHandler;
import cn.com.isurpass.securityplatform.alarmone.AlarmoneMessageSender;
import io.netty.channel.ChannelHandlerContext;

public class AlarmTwoHeartBeatHandler extends AlarmoneHeartBeatHandler 
{
	private static Log log = LogFactory.getLog(AlarmTwoHeartBeatHandler.class);
	
	@Override
	protected void setAlarmMessageSender(ChannelHandlerContext ctx)
	{
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		log.info("setAlarmMessageSender " + systemconfig.getAlarmtwoplatformid());
		AlarmplatformConnectionManager.getInstance().putChannelHandlerContext(systemconfig.getAlarmtwoplatformid(), new AlarmoneMessageSender(ctx));
	}
	
	@Override
	protected String getAlarmPlatformName()
	{
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		return systemconfig.getAlarmtwoplatformname();
	}
}
