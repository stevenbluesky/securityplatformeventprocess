package cn.com.isurpass.securityplatform.alarmtwo;

import cn.com.isurpass.securityplatform.alarmone.AlarmoneMessageSender;
import io.netty.channel.ChannelHandlerContext;

@Deprecated
public class AlarmTwoMessageSender extends AlarmoneMessageSender 
{

	public AlarmTwoMessageSender(ChannelHandlerContext ctx) 
	{
		super(ctx);
		alarmmessagepatten = "~5%s1 18%s%s01%03d";  
	}

}
