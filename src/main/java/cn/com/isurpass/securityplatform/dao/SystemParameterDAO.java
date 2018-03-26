package cn.com.isurpass.securityplatform.dao;

import org.springframework.data.repository.CrudRepository;

import cn.com.isurpass.securityplatform.domain.SystemParameter;

public interface SystemParameterDAO extends CrudRepository<SystemParameter, Integer>
{
	SystemParameter findByParamkey(String key);
}
