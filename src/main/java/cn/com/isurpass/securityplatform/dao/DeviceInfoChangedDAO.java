package cn.com.isurpass.securityplatform.dao;

import cn.com.isurpass.securityplatform.domain.DeviceInfoChangedPO;
import org.springframework.data.repository.CrudRepository;

public interface DeviceInfoChangedDAO extends CrudRepository<DeviceInfoChangedPO,Integer>{
    DeviceInfoChangedPO findByZwavesubdeviceid(Integer zwavesubdeviceid);
}
