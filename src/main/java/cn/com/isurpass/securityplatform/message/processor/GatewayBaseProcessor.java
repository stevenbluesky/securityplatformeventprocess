package cn.com.isurpass.securityplatform.message.processor;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.dao.GatewayDAO;
import cn.com.isurpass.securityplatform.domain.Gateway;
import cn.com.isurpass.securityplatform.message.vo.Event;

public abstract class GatewayBaseProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(GatewayBaseProcessor.class);
	
	@Resource
	protected GatewayDAO gatewaydao;
	protected Event event ;

	@Override
	@Transactional
	public void process(JSONObject message) 
	{
		event = JSON.toJavaObject(message,Event.class);
		if( StringUtils.isBlank(event.getDeviceid()))
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		Optional<Gateway> d = gatewaydao.findById(event.getDeviceid());

		if ( !d.isPresent() )
		{
			log.warn("gateway not exist");
			return ;
		}
		process(event , d.get());
	}

	protected abstract void process(Event event , Gateway gateway);
	
}
