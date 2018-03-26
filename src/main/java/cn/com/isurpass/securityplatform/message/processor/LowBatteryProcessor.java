package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="lowbattery")
@Scope("prototype")
public class LowBatteryProcessor extends DeviceStatusBaseProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setBattery(event.getIntparam());
	}

}
