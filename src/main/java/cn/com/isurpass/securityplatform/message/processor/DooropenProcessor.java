package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.constant.DoorSensorStatus;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="dooropen")
@Scope("prototype")
public class DooropenProcessor extends DeviceStatusProcessor
{

	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(DoorSensorStatus.open.getStatus());
		zd.setWarningstatuses(event.getWarningstatuses());
	}
	
}
