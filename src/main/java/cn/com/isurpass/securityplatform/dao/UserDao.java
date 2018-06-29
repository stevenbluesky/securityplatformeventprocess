package cn.com.isurpass.securityplatform.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.isurpass.securityplatform.domain.UserPO;

public interface UserDao  extends CrudRepository<UserPO, Integer>
{
	
}
