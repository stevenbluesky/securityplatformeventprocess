package cn.com.isurpass.securityplatform.message.processor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value="battery")
@Scope("prototype")
public class BatteryProcessor extends LowBatteryProcessor
{

}
