package cn.com.isurpass.securityplatform.alarmone;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.securityplatform.alarm.IAlarmMessageSender;
import cn.com.isurpass.securityplatform.util.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;


public class AlarmoneMessageSender implements IAlarmMessageSender 
{
	private static Log log = LogFactory.getLog(AlarmoneMessageSender.class);
	
	private static final Map<String , String> alarmmessagemap = new HashMap<String , String>();
	private static final String alarmmessagepatten = "~50X1 18 %s %s 01 %s";  
	
	public static AttributeKey<String> ATTR_ALARMPLATFORMNAME = AttributeKey.valueOf("ALARAMPLATEFORMNAME"); 
	
	private ChannelHandlerContext ctx ;
	
	static
	{
		alarmmessagemap.put("smoke", "E111");
		alarmmessagemap.put("unalarmsmoke", "R111");
		alarmmessagemap.put("waterleak", "E154");
		alarmmessagemap.put("unalarmwaterleak", "R154");
		alarmmessagemap.put("gasleak", "E151");
		alarmmessagemap.put("unalarmgasleak", "R151");
		alarmmessagemap.put("tampleralarm", "E137");
		alarmmessagemap.put("unalarmtampleralarm", "R137");
		alarmmessagemap.put("sos", "E120");
		alarmmessagemap.put("unalarmsos", "R120");
		alarmmessagemap.put("dooropen", "E131");
		alarmmessagemap.put("unalarmdooropen", "R131");
		alarmmessagemap.put("doorlockopen", "E131");
		alarmmessagemap.put("unalarmdoorlockopen", "R131");
		alarmmessagemap.put("poweroverload", "E312");
		alarmmessagemap.put("unalarmpoweroverload", "R312");
		alarmmessagemap.put("movein", "E132");
		alarmmessagemap.put("unalarmmovein", "E132");
		alarmmessagemap.put("remoteoffline", "E350");
		alarmmessagemap.put("remoteonline", "R350");
		alarmmessagemap.put("dscalarm", "E132");
		alarmmessagemap.put("unalarmdscalarm", "R132");
//		alarmmessagemap.put("passworderror5times", "");
//		alarmmessagemap.put("bulliedopenlock", "");
//		alarmmessagemap.put("malfunction", "");
//		alarmmessagemap.put("recovery", "");
//		alarmmessagemap.put("lowbattery", "");
	}

	public AlarmoneMessageSender(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
	}


	@Override
	public boolean sendAlarmMessage(String msg, String usercode , String zone) 
	{
		String ec = alarmmessagemap.get(msg);
		if ( StringUtils.isBlank(ec))
			return true;
		
		String am = String.format(alarmmessagepatten, usercode , ec , zone);
		
		LogUtils.info("Send to %s : %s" , ctx.channel().attr(ATTR_ALARMPLATFORMNAME).get() , am);
		if ( ctx.channel().isActive())
			ctx.channel().writeAndFlush(am.getBytes());
		else 
			log.info("connection is not active");
		
		return true;
	}

}
