package cn.com.isurpass.securityplatform.alarmone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.SystemConfig;
import cn.com.isurpass.securityplatform.alarm.AlarmplatformConnectionManager;
import cn.com.isurpass.securityplatform.util.LogUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class AlarmoneHeartBeatHandler extends SimpleChannelInboundHandler<byte[]>
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AlarmoneHeartBeatHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception 
	{
//		if ( StringUtils.isBlank(msg))
//			return ;
		if ( msg == null || msg.length == 0 )
			return ;
		if ( msg.length == 1)
			LogUtils.info("Receive from %s : %d" , ctx.channel().attr(AlarmoneMessageSender.ATTR_ALARMPLATFORMNAME).get() , msg[0]);
		else 
		{
			StringBuffer sb = new StringBuffer();
			for ( int i = 0 ; i < msg.length ; i ++ )
				sb.append(String.format("%d ", msg[i] & 0xff));
			sb.append(new String(msg));
		}
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);
		
		if (evt instanceof IdleStateEvent) 
		{  
	      IdleStateEvent event = (IdleStateEvent) evt;  
	      if (event.state() == IdleState.ALL_IDLE
	    		  || event.state() == IdleState.READER_IDLE
	    		  || event.state() == IdleState.WRITER_IDLE) 
	      {
	    	  LogUtils.info("Send to %s : 1011           @ " , ctx.channel().attr(AlarmoneMessageSender.ATTR_ALARMPLATFORMNAME).get());
	    	  ctx.channel().writeAndFlush("1011           @ ".getBytes());
	      }
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		super.channelActive(ctx);
		
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		LogUtils.info("%s channel active", systemconfig.getAlarmoneplatformname());
		
		ctx.channel().attr(AlarmoneMessageSender.ATTR_ALARMPLATFORMNAME).set(systemconfig.getAlarmoneplatformname());
		
		AlarmplatformConnectionManager.getInstance().putChannelHandlerContext(systemconfig.getAlarmoneplatformid(), new AlarmoneMessageSender(ctx));
		
  	  	LogUtils.info("Send to %s : 1011           @ " , ctx.channel().attr(AlarmoneMessageSender.ATTR_ALARMPLATFORMNAME).get());
  	  	ctx.channel().writeAndFlush("1011           @ ".getBytes());
	}

	private byte[] concat(byte[] b1 , byte[] b2)
	{
		if ( b1 == null )
			return b2 ;
		if ( b2 == null )
			return b1 ;
		
		byte[] b = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b, 0, b1.length);
		System.arraycopy(b2, 0, b, b1.length, b2.length);
		return b ;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		LogUtils.info("%s channel inactive", systemconfig.getAlarmoneplatformname());
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error(cause.getMessage(),cause);
		super.exceptionCaught(ctx, cause);
	}
}
