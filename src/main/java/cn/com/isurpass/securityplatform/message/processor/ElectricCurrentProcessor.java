package cn.com.isurpass.securityplatform.message.processor;

import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

public class ElectricCurrentProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(ElectricCurrentProcessor.class);
	
	@Resource
	private ZwavedeviceDAO devicedao;

	@Override
	public void process(JSONObject message)
	{
		Event event = JSON.toJavaObject(message,Event.class);
		if( event.getZwavedeviceid() == 0 )
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		Optional<Zwavedevice> d = devicedao.findById(event.getZwavedeviceid());
		if ( !d.isPresent() )
		{
			log.info("device not exist");
			return ;
		}
		
		

	}

}
