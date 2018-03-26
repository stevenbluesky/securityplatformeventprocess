package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component(value="deleteremote")
@Scope("prototype")
public class DeleteRemoteProcessor implements IMessageProcessor
{

	@Override
	public void process(JSONObject message)
	{
		//Do nothing

	}

}
