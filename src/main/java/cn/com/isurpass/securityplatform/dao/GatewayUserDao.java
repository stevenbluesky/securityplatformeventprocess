package cn.com.isurpass.securityplatform.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.isurpass.securityplatform.domain.GatewayUserPO;

public interface GatewayUserDao  extends CrudRepository<GatewayUserPO, Integer>
{
	GatewayUserPO findByDeviceid(String deviceid); 
}
