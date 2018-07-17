package cn.com.isurpass.securityplatform.service;

import cn.com.isurpass.securityplatform.dao.ZwaveSubDeviceDAO;
import cn.com.isurpass.securityplatform.domain.ZwaveSubDevicePO;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceInfoChangeServiceTest {
    @Autowired
    private ZwaveSubDeviceDAO zwaveSubDeviceDAO;

    @Test
    public void testUpdate(){
        String x = "[1]";
        JSONArray jsonArray = JSONArray.parseArray(x);
        jsonArray.removeIf(l -> l.equals(1));
        System.out.println(jsonArray.size());
    }
}
