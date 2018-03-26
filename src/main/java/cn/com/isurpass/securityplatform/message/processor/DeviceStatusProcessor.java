package cn.com.isurpass.securityplatform.message.processor;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="devicestatus")
@Scope("prototype")
public class DeviceStatusProcessor extends DeviceStatusBaseProcessor
{
	@Override
	protected void process(Event event, Zwavedevice zd) 
	{
		zd.setStatus(event.getIntparam());
		if ( StringUtils.isNotBlank(event.getObjparam()))
			zd.setStatuses(event.getObjparam());
		zd.setWarningstatuses(event.getWarningstatuses());
	}

}
