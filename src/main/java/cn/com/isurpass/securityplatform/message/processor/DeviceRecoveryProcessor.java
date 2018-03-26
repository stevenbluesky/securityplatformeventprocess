package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;


@Component(value="recovery")
@Scope("prototype")
public class DeviceRecoveryProcessor extends DeviceStatusBaseProcessor
{

	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(event.getIntparam());
	}


}
