package cn.com.isurpass.securityplatform.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.isurpass.securityplatform.dao.SystemParameterDAO;
import cn.com.isurpass.securityplatform.domain.SystemParameter;
import cn.com.isurpass.securityplatform.message.constant.SystemParameterKey;

@Component
public class ConfigService 
{
	@Autowired  
	private SystemParameterDAO dao ;

	public int getLastId()
	{
		SystemParameter sp = dao.findByParamkey(SystemParameterKey.lastSynId.toString());
		
		int lastid = 0 ;
		if ( sp != null )
			lastid = sp.getIntvalue();
		return lastid ;
	}
	
	@Transactional
	public void saveLastId(int lastid)
	{				
		SystemParameter sp = dao.findByParamkey(SystemParameterKey.lastSynId.toString());
		if ( sp == null )
		{
			sp = new SystemParameter(SystemParameterKey.lastSynId.toString() ,lastid , null );
			dao.save(sp);
		}
		else 
			sp.setIntvalue(lastid);
	}
}
