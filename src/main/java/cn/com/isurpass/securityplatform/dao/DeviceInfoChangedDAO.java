package cn.com.isurpass.securityplatform.dao;

import cn.com.isurpass.securityplatform.domain.DeviceInfoChangedPO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceInfoChangedDAO extends CrudRepository<DeviceInfoChangedPO,Integer>{
    DeviceInfoChangedPO findByZwavesubdeviceid(Integer zwavesubdeviceid);

    DeviceInfoChangedPO findByZwavedeviceidAndChannelid(Integer zwavedeviceid, int channelid);

    List<DeviceInfoChangedPO> findByZwavedeviceid(Integer zwavedeviceid);
}
