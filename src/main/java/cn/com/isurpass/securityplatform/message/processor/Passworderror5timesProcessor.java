package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="passworderror5times")
@Scope("prototype")
public class Passworderror5timesProcessor extends DeviceStatusProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setWarningstatuses(event.getWarningstatuses());
	}
}
