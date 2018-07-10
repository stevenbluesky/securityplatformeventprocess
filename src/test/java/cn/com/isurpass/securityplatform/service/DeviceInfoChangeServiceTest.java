package cn.com.isurpass.securityplatform.service;

import cn.com.isurpass.securityplatform.dao.DeviceInfoChangedDAO;
import cn.com.isurpass.securityplatform.domain.DeviceInfoChangedPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceInfoChangeServiceTest {
    @Autowired
    private DeviceInfoChangedDAO deviceInfoChangedDAO;

    @Test
    public void testUpdate(){
        DeviceInfoChangedPO deviceInfoChangedPO = new DeviceInfoChangedPO();
        deviceInfoChangedPO.setZwavesubdeviceid(1);
        deviceInfoChangedPO.setZwavedeviceid(101);
        deviceInfoChangedPO.setChannelid(222);
        deviceInfoChangedDAO.save(deviceInfoChangedPO);
    }
}
