package cn.com.isurpass.securityplatform.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.com.isurpass.securityplatform.domain.Zwavedevice;

public interface ZwavedeviceDAO extends CrudRepository<Zwavedevice, Integer>{
	List<Zwavedevice> findByDeviceid(String deviceid);
}
