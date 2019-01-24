package cn.com.isurpass.securityplatform.alarmtwo;

import cn.com.isurpass.securityplatform.alarmone.AlarmoneMessageSender;
import io.netty.channel.ChannelHandlerContext;

public class AlarmTwoMessageSender extends AlarmoneMessageSender 
{

	public AlarmTwoMessageSender(ChannelHandlerContext ctx) 
	{
		super(ctx);
		alarmmessagepatten = "5%s 18%s%s01%03d";  
	}

}
