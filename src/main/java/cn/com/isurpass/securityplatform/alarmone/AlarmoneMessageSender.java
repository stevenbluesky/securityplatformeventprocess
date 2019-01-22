package cn.com.isurpass.securityplatform.alarmone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.alarm.IAlarmMessageSender;
import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;
import cn.com.isurpass.securityplatform.util.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class AlarmoneMessageSender implements IAlarmMessageSender 
{
	private static Log log = LogFactory.getLog(AlarmoneMessageSender.class);
	
	private static final Map<String , String> alarmmessagemap = new HashMap<String , String>();
	protected String alarmmessagepatten = "~5%s1 18 %s %s 01 %03d";  
	private static final String DSCPARTIONTIONARMSTATUS = "dscpartitionarmstatus";
	private static final String WARNING_TYPE_USER_ARM ="arm";
	private static final String WARNING_TYPE_USER_INHOME_ARM ="inhomearm";
	private static final String WARNING_TYPE_USER_DISARM ="disarm";
	public static final String MESSAGE_PARTITION_DIS_ARM_USER_CODE = "partitiondisarmusercode";
	public static final String MESSAGE_PARTITION_ARM_USER_CODE = "partitionarmusercode";
	
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
		alarmmessagemap.put("dooropendelaywarning", "E131");
		alarmmessagemap.put("unalarmdooropen", "R131");
		alarmmessagemap.put("doorlockopen", "E131");
		alarmmessagemap.put("doorlockdelaywarning", "E131");
//		alarmmessagemap.put("doorlockopendelaywarning", "E131");
		alarmmessagemap.put("unalarmdoorlockopen", "R131");
		alarmmessagemap.put("poweroverload", "E312");
		alarmmessagemap.put("unalarmpoweroverload", "R312");
		alarmmessagemap.put("movein", "E132");
		alarmmessagemap.put("moveindelaywarning", "E132");
		alarmmessagemap.put("unalarmmovein", "R132");
		alarmmessagemap.put("remoteoffline", "E350");
		alarmmessagemap.put("remoteonline", "R350");
		alarmmessagemap.put("dscalarm", "E132");
		alarmmessagemap.put("unalarmdscalarm", "R132");
		alarmmessagemap.put("armstatus_0", "E401");
		alarmmessagemap.put("armstatus_1", "R401");
		alarmmessagemap.put("armstatus_3", "R441");

		alarmmessagemap.put("dscfkeyalarm", "E110");
		alarmmessagemap.put("unalarmdscfkeyalarm", "R110");
		alarmmessagemap.put("dscakeyalarm", "E100");
		alarmmessagemap.put("unalarmdscakeyalarm", "R100");
		alarmmessagemap.put("dscpkeyalarm", "E120");
		alarmmessagemap.put("unalarmdscpkeyalarm", "R120");
	}

	public AlarmoneMessageSender(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
	}


	@Override
	public boolean sendAlarmMessage(Event event, UserPO user,  int zone,String alarmvalue)
	{
		if ( !ctx.channel().isActive())
		{
			log.info("connection is not active");
			return true;
		}
					
		String ec = null;
		if (!StringUtils.isBlank(alarmvalue)) {
			ec = alarmvalue;
		}else{
			ec = alarmmessagemap.get(event.getType());
			if ( StringUtils.isBlank(ec))
			{
				if (MESSAGE_PARTITION_DIS_ARM_USER_CODE.equals(event.getType()) 
					|| MESSAGE_PARTITION_ARM_USER_CODE.equals(event.getType()) )
					{
						sendArmMessage(event , user);
					}
				return true;
			}
			if ( ec.startsWith("E") 
					&& event.getWarningstatus() == 0 
					&&  !"E350".equals(ec) ) // gateway offline  
			{
				return true;
			}
		}
		

		String am = String.format(alarmmessagepatten, user.getGroupid() , user.getSupcode() , ec , zone);
		
		sendMessage(am);
		
		return true;
	}
	
	private void sendArmMessage(Event event, UserPO user)
	{
		if ( StringUtils.isBlank(event.getObjparam()))
			return ;
		JSONObject json = JSON.parseObject(event.getObjparam());
		
		if ( StringUtils.isBlank(json.getString("armstatus")))
			return ;
		String ec = alarmmessagemap.get(String.format("armstatus_%d", json.getIntValue("armstatus")));
		
		if ( StringUtils.isBlank(ec))
			return ;

		String am = String.format(alarmmessagepatten, user.getGroupid() , user.getSupcode() , ec , event.getIntparam() );
		
		sendMessage(am);
	}
	
	private void sendGatewayArmMessage(Event event, UserPO user)
	{
		String ec = null ;
		if ( WARNING_TYPE_USER_ARM.equals(event.getType()) )
			ec = "R401";
		else if ( WARNING_TYPE_USER_INHOME_ARM.equals(event.getType()))
			ec = "R441";
		else if ( WARNING_TYPE_USER_DISARM.equals(event.getType()))
			ec = "E401";
		if ( StringUtils.isBlank(ec))
			return ;
		
		ZwavedeviceDAO dao = SpringUtil.getBean(ZwavedeviceDAO.class);
		
		List<Zwavedevice> lst = dao.findByDeviceid(event.getDeviceid());
		
		if ( lst == null || lst.size() == 0 )
			return ;
		Set<Integer> zs = new HashSet<Integer>();
		
		for ( Zwavedevice zd : lst )
		{
			Integer zn = zd.getArea();
			if ( zn == null )
				zn = 1 ;
			if ( zs.contains(zn))
				continue;
			zs.add(zn);
			String am = String.format(alarmmessagepatten, user.getGroupid() , user.getSupcode() , ec , zn );
			sendMessage(am);
		}
	}
	
	private void sendArmMessge(Event event, UserPO user)
	{
		if ( StringUtils.isBlank(event.getObjparam()))
			return ;
		JSONObject json = JSON.parseObject(event.getObjparam());
		
		if ( StringUtils.isBlank(json.getString("armstatus")))
			return ;
		String ec = alarmmessagemap.get(String.format("armstatus_%d", json.getIntValue("armstatus")));
		
		if ( StringUtils.isBlank(ec))
			return ;
		
		JSONArray channelid = json.getJSONArray("channelid");
		
		if ( channelid != null && channelid.size() > 0 )
		{	
			for ( int i = 0 ; i < channelid.size() ; i ++ )
			{
				String am = String.format(alarmmessagepatten, user.getGroupid() , user.getSupcode() , ec , channelid.getIntValue(i));
			
				sendMessage(am);
			}
		}
		
		JSONArray zwavedeivceid = json.getJSONArray("zwavedeivceid");
		if ( zwavedeivceid != null && zwavedeivceid.size() > 0 )
		{
			
			ZwavedeviceDAO dao = SpringUtil.getBean(ZwavedeviceDAO.class);
			Iterable<Zwavedevice> lst = dao.findAllById(zwavedeivceid.toJavaList(Integer.class));
			
			if ( lst == null )
				return ;
			Set<Integer> zs = new HashSet<Integer>();
			
			for ( Zwavedevice zd : lst )
			{
				Integer zn = zd.getArea();
				if ( zn == null )
					zn = 1 ;
				if ( zs.contains(zn))
					continue;
				zs.add(zn);
				String am = String.format(alarmmessagepatten, user.getGroupid() , user.getSupcode() , ec , zn );
				sendMessage(am);
			}
		}
		
	}

	private void sendMessage(String am)
	{
		LogUtils.info("Send to %s : %s" , ctx.channel().attr(ATTR_ALARMPLATFORMNAME).get() , am);
		if ( ctx.channel().isActive())
			ctx.channel().writeAndFlush(am.getBytes());
		else 
			log.info("connection is not active");
	}
}
