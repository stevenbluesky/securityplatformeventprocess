package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.constant.InfraredDetectorStatus;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="movein")
@Scope("prototype")
public class MoveinProcessor extends DeviceStatusProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(InfraredDetectorStatus.movein.getStatus());
		zd.setWarningstatuses(event.getWarningstatuses());
	}
}
