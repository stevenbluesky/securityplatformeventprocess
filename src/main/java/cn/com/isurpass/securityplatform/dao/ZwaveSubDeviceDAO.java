package cn.com.isurpass.securityplatform.dao;

import cn.com.isurpass.securityplatform.domain.ZwaveSubDevicePO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZwaveSubDeviceDAO extends CrudRepository<ZwaveSubDevicePO,Integer>{
    ZwaveSubDevicePO findByZwavesubdeviceid(Integer zwavesubdeviceid);

    ZwaveSubDevicePO findByZwavedeviceidAndChannelid(Integer zwavedeviceid, int channelid);

    List<ZwaveSubDevicePO> findByZwavedeviceid(Integer zwavedeviceid);
}
