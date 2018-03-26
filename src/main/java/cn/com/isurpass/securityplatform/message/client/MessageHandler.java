package cn.com.isurpass.securityplatform.message.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.SystemConfig;
import cn.com.isurpass.securityplatform.message.processor.IMessageProcessor;
import cn.com.isurpass.securityplatform.service.ConfigService;
import cn.com.isurpass.securityplatform.util.HttpUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Component
@Scope("prototype")
public class MessageHandler extends SimpleChannelInboundHandler<String> 
{
	private static Log log = LogFactory.getLog(MessageHandler.class);
	private static Map<String , String> processormap = new HashMap<String , String>();
	
	private boolean haslogin = false ;
	private int lastid = 0 ;
	private boolean hassendlastid = false ;

	@Autowired
	private ConfigService configservice ;
	@Autowired
	private SystemConfig systemconfig ;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
    {
    	log.error(cause.getMessage() , cause);
        ctx.close();
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{		
		log.info("channelActive");
		super.channelActive(ctx);
		
		login(ctx);
	}

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception 
	{
	     if ( message == null || message.length() == 0 )
	    	 return ;

	     log.info(message);
	     if ( !haslogin )
	     {
	    	 processLoginResponse(ctx , message);
	     }
	     else 
	     {
	    	 processEventMessage(ctx , message);
	     }
	}
	
	private void processEventMessage(ChannelHandlerContext ctx,String message)
	{
		JSONObject json = null ;
		try
		{
			json = JSON.parseObject(message);
		}
		catch(Throwable e)
		{
			log.error(e.getMessage());
			return ;
		}
		
		if ( json.containsKey("resultcode") && !json.containsKey("lastid"))  //heart beat message 
		{
			return ;
		}
		
		if ( lastid != 0 && lastid != json.getIntValue("lastid"))
		{
			if ( log.isWarnEnabled())
				log.warn(String.format("lastid not equal , lastid is %d" , lastid));
			this.sendLastid(ctx);
			
			return ;
		}
		
		try
		{
			String pt = json.getString("type");
			if ( processormap.containsKey(pt))
				pt = processormap.get(pt);
			
			if ( !SpringUtil.containsBean(pt) )
			{
				if ( log.isInfoEnabled())
					log.info(String.format("not support %s", json.getString("type")));
				else 
					log.warn("not support");
			}
			else 
			{
				IMessageProcessor bean = (IMessageProcessor)SpringUtil.getBean(pt);
				bean.process(json);
			}
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t );
		}
		
		this.saveLastId(json.getIntValue("newid"));
	}
	
	private void processLoginResponse(ChannelHandlerContext ctx, String message)
	{
		JSONObject json = JSON.parseObject(message);
	   	 if ( json.containsKey("resultcode") && json.getIntValue("resultcode") == 0 )
	   	 {
	   		 log.info("login success");
	   		 haslogin = true ;
	   		 sendLastid(ctx);
	   	 }
	   	 else if ( json.containsKey("resultcode") 
	   			 && ( json.getIntValue("resultcode") == 30330 || json.getIntValue("resultcode") == 30010 ))
	   	 {
	   		 //RestfulUtil.getInstance().login();
	   		 login(ctx);
	   	 }
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);
		
		if (evt instanceof IdleStateEvent) 
		{  
	      IdleStateEvent event = (IdleStateEvent) evt;  
	      if (event.state() == IdleState.ALL_IDLE) 
	      {
	    	  sendHeartBeat(ctx);
	      }
	      else if ( event.state() == IdleState.READER_IDLE)
	      {
	    	  haslogin = false ;
	    	  ctx.close();
	      }
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{
		log.info("channelInactive");
		super.channelInactive(ctx);
		haslogin = false ;
		ctx.close();
	}
	
	private void login(ChannelHandlerContext ctx)
	{
		JSONObject json = new JSONObject();
		json.put("code", systemconfig.getThirdpartcode());
		json.put("password", systemconfig.getThirdpartpw());
		
		String rst = HttpUtil.httprequest(String.format("https://%s/iremote/thirdpart/login", systemconfig.getCoreserverurl()), json);
		
		if ( StringUtils.isBlank(rst) || rst.length() == 0 ) //time out , no result .
		{
			log.info("Time out");
			return ;
		}
		
		json = JSON.parseObject(rst);
		if ( json.containsKey("resultCode") && json.getInteger("resultCode") == 30010)
		{
			log.error("Request token failed , exit");
			System.exit(0);
		}
		
		String token = json.getString("token");
		
		json = new JSONObject();
		json.put("token", token);
		json.put("code", systemconfig.getThirdpartcode());
		log.info(json.toJSONString());
		ctx.writeAndFlush(json.toJSONString());
	}
	
	private void sendLastid(ChannelHandlerContext ctx)
	{
		if ( hassendlastid == true)
			return ;
		
		lastid = this.configservice.getLastId();
		
		JSONObject nid = new JSONObject();
		nid.put("lastid", lastid);
		
		String strlastid = nid.toJSONString();
		log.info(strlastid);
		
		ctx.writeAndFlush(strlastid);
		
		hassendlastid = true;
	}
	
	private void sendHeartBeat(ChannelHandlerContext ctx)
	{
		JSONObject json = new JSONObject();
		json.put("action", "heartbeat");
		
		log.info(json.toJSONString());
		ctx.writeAndFlush(json.toJSONString());
	}
	
	private void saveLastId(int id)
	{
		hassendlastid = false ;
		lastid = id ;
		
		this.configservice.saveLastId(lastid);
	}
	
	static 
	{
		processormap.put("unalarmdooropen", "unalarmprocessor");
		processormap.put("unalarmdscalarm", "unalarmprocessor");
		processormap.put("unalarmmovein", "unalarmprocessor");
		processormap.put("unalarmtampleralarm", "unalarmprocessor");
		processormap.put("unalarmwaterleak", "unalarmprocessor");
		processormap.put("unalarmsos", "unalarmprocessor");
		processormap.put("unalarmsmoke", "unalarmprocessor");
		processormap.put("unalarmgasleak", "unalarmprocessor");
		processormap.put("unalarmwaterleak", "unalarmprocessor");
		processormap.put("unalarmpassworderror5times", "unalarmprocessor");
		processormap.put("unalarmlockkeyerror", "unalarmprocessor");
		processormap.put("unalarmdoorlockopen", "unalarmprocessor");
		processormap.put("unalarmbulliedopenlock", "unalarmprocessor");
		processormap.put("unalarmlockopeninside", "unalarmprocessor");
		processormap.put("unalarmpoweroverload", "unalarmprocessor");
		processormap.put("unalarmlockkeyevent", "unalarmprocessor");
		processormap.put("unalarmlocklockerror", "unalarmprocessor");
		processormap.put("lockkeyerror", "alarmprocessor");
		processormap.put("lockkeyevent", "alarmprocessor");
		processormap.put("lockopeninside", "doorlockopen");
		processormap.put("poweroverload", "devicestatus");
		processormap.put("locklockerror", "alarmprocessor");
	}
}
