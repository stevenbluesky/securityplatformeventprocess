package cn.com.isurpass.securityplatform.message.processor;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

public abstract class DeviceStatusBaseProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(DeviceStatusBaseProcessor.class);
	
	@Resource
	protected ZwavedeviceDAO devicedao;
	protected Event event ;

	@Override
	@Transactional
	public void process(JSONObject message) 
	{
		event = JSON.toJavaObject(message,Event.class);
		if( event.getZwavedeviceid() == 0 )
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		Optional<Zwavedevice> d = devicedao.findById(event.getZwavedeviceid());

		if ( !d.isPresent() )
		{
			log.warn("device not exist");
			return ;
		}
		process(event , d.get());
	}

	protected abstract void process(Event event , Zwavedevice zd);
	
}
