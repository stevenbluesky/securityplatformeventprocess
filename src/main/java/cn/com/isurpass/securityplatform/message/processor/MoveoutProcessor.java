package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.constant.InfraredDetectorStatus;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="moveout")
@Scope("prototype")
public class MoveoutProcessor extends DeviceStatusProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(InfraredDetectorStatus.moveout.getStatus());
	}
}
