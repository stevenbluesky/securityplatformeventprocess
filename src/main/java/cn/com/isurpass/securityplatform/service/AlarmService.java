package cn.com.isurpass.securityplatform.service;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.alarm.AlarmplatformConnectionManager;
import cn.com.isurpass.securityplatform.dao.GatewayUserDao;
import cn.com.isurpass.securityplatform.dao.UserDao;
import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.GatewayUserPO;
import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component
public class AlarmService 
{
	@Autowired  
	private ZwavedeviceDAO zddao ;
	@Autowired  
	private UserDao userdao ;
	@Autowired  
	private GatewayUserDao gwudao ;
	
	
	public void sendAlarmMessage(JSONObject json)
	{
		Event event = JSON.toJavaObject(json,Event.class);
		
		int zone = 1 ;
		
		if( event.getZwavedeviceid() != 0 )
		{
			Zwavedevice zd = queryZwavedevice(event);
			if ( zd == null )
				return ;
			if ( zd.getArea() != null )
				zone = zd.getArea();
		}
		
		UserPO u = queryUser(event);
		if ( u == null )
			return ;
		
		AlarmplatformConnectionManager.getInstance().sendMessage(String.valueOf(u.getOrganizationid()), event.getType(), u.getSupcode(), String.valueOf(zone));
	}
	
	private Zwavedevice queryZwavedevice(Event event)
	{
		if( event.getZwavedeviceid() == 0 )
			return null ;
		
		Optional<Zwavedevice> oz = zddao.findById(event.getZwavedeviceid());
		if ( oz.isPresent())
			return oz.get();
		return null;
	}
	
	private UserPO queryUser(Event event)
	{
		if ( StringUtils.isBlank(event.getDeviceid()))
			return null ;
		GatewayUserPO gu = gwudao.findByDeviceid(event.getDeviceid());
		
		if ( gu == null || gu.getUserid() == null )
			return null ;
		
		Optional<UserPO> user = userdao.findById(gu.getUserid());

		if ( user.isPresent())
			return user.get();
		return null ;
	}
	
}



