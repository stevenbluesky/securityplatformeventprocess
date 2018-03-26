package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.constant.DoorlockStatus;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="doorlockclose")
@Scope("prototype")
public class DoorLockCloseProcessor extends DeviceStatusBaseProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(DoorlockStatus.close.getStatus());
	}

}
