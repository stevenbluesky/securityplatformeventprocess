package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.domain.Gateway;
import cn.com.isurpass.securityplatform.message.constant.GatewayStatus;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="remoteoffline")
@Scope("prototype")
public class RemoteOfflineProcessor extends GatewayBaseProcessor
{
	@Override
	protected void process(Event event, Gateway gateway) 
	{
		gateway.setStatus(GatewayStatus.offline.ordinal());
	}

}
