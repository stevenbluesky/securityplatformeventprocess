package cn.com.isurpass.securityplatform.message.processor;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.Gateway;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component(value="infochanged")
@Scope("prototype")
public class InfoChangeProcessor extends GatewayBaseProcessor
{
	private static Log log = LogFactory.getLog(InfoChangeProcessor.class);

	@Autowired
	private ZwavedeviceDAO zddao ;

	@Override
	protected void process(Event event, Gateway gateway) 
	{
		JSONObject json = JSON.parseObject(event.getObjparam());
		gateway.setName(json.getString("name"));
		gateway.setAppaccount(json.getString("loginname"));
		
		List<Zwavedevice> dblst = zddao.findByDeviceid(event.getDeviceid());
		
		List<Zwavedevice> cslst = JSON.parseArray(json.getString("zwavedevice") , Zwavedevice.class);
		
		for ( Zwavedevice zd : cslst )
		{
			Zwavedevice dzd = find(dblst , zd.getZwavedeviceid());
			if ( dzd != null)
			{
				dzd.setName(zd.getName());
			}
			else 
			{
				zd.setDeviceid(event.getDeviceid());
				if ( zd.getCreatetime() == null )
					zd.setCreatetime(new Date());
				zddao.save(zd);
			}
		}
		
		for (  Zwavedevice zd : dblst )
		{
			if ( this.isDeviceExists(cslst , zd.getZwavedeviceid()))
				continue ;
			zddao.delete(zd);
			if ( log.isInfoEnabled())
				log.info(String.format("remote %s(%d)", zd.getName() , zd.getZwavedeviceid()));
		}
	}

	private Zwavedevice find(List<Zwavedevice> lst , int zwavedeviceid)
	{
		for ( Zwavedevice zd : lst )
			if ( zd.getZwavedeviceid() == zwavedeviceid )
				return zd ;
		return null ;
	}
	
	private boolean isDeviceExists(List<Zwavedevice> lst , int zwavedeviceid)
	{
		return (find(lst , zwavedeviceid) != null ) ;
	}
	
}
