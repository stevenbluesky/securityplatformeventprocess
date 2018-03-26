package cn.com.isurpass.securityplatform.dao;

import org.springframework.data.repository.CrudRepository;
import cn.com.isurpass.securityplatform.domain.Gateway;


public interface GatewayDAO extends CrudRepository<Gateway, String> 
{
	
}
