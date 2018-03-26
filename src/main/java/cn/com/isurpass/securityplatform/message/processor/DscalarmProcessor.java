package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="dscalarm")
@Scope("prototype")
public class DscalarmProcessor extends TamplerAlarmProcessor {
	
}
