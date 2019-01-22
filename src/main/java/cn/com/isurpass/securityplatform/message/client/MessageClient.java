package cn.com.isurpass.securityplatform.message.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.SpringUtil;
import cn.com.isurpass.securityplatform.SystemConfig;
import cn.com.isurpass.securityplatform.alarmone.AlarmoneClientInitializer;
import cn.com.isurpass.securityplatform.alarmtwo.AlarmTwoClientInitializer;
import cn.com.isurpass.securityplatform.netty.NettyClient;
import cn.com.isurpass.securityplatform.netty.NettyServer;

@Component
public class MessageClient implements ApplicationRunner,DisposableBean, Runnable{

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MessageClient.class);
	private static Thread thread;
	private NettyServer alarmtwonettyserver ;

	@Override
	public void run() 
	{
		SystemConfig systemconfig = SpringUtil.getBean(SystemConfig.class);
		
		NettyClient.startClient(systemconfig.getEventpushserverurl(),systemconfig.getCoreserverport(), new MessageClientInitializer());
		
		NettyClient.startClient(systemconfig.getAlarmoneplatformip(), systemconfig.getAlarmoneplatformport(), new AlarmoneClientInitializer());
		//NettyClient.startClient(systemconfig.getAlarmtwoplatformip(), systemconfig.getAlarmtwoplatformport(), new AlarmTwoClientInitializer());
		alarmtwonettyserver = new NettyServer(systemconfig.getAlarmtwoplatformport() , new AlarmTwoClientInitializer());
		alarmtwonettyserver.start();
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


	@Override
	public void destroy() throws Exception 
	{
		alarmtwonettyserver.destroy();
	}
	   
}
