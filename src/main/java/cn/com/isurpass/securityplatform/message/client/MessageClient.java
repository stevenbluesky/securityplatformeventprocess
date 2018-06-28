package cn.com.isurpass.securityplatform.message.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.SystemConfig;
import cn.com.isurpass.securityplatform.alarmone.AlarmoneClientInitializer;
import cn.com.isurpass.securityplatform.netty.NettyClient;

@Component
public class MessageClient implements ApplicationRunner, Runnable{

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MessageClient.class);
	private static Thread thread;
	

	@Override
	public void run() 
	{
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		
		NettyClient.startClient(systemconfig.getEventpushserverurl(),systemconfig.getCoreserverport(), new MessageClientInitializer());
		
		NettyClient.startClient(systemconfig.getAlarmoneplatformip(), systemconfig.getAlarmoneplatformport(), new AlarmoneClientInitializer());
	}


	@Override
	public void run(ApplicationArguments args) throws Exception 
	{
		if ( thread == null )
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	   
}
