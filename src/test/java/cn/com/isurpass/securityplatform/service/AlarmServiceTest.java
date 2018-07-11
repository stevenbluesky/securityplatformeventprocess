package cn.com.isurpass.securityplatform.service;

import cn.com.isurpass.securityplatform.dao.ZwaveSubDeviceDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmServiceTest {
    @Autowired
    private AlarmService alarmservice;
    @Autowired
    private ZwaveSubDeviceDAO zwaveSubDeviceDAO;

    @Test
    public void DSCAlarmTest() {
        JSONObject json = JSON.parseObject("{                                             " +
                "	\"newid\": 17951,                          " +
                "	\"zwavedeviceid\": 11545,                  " +
                "	\"newtime\": \"2018-05-25 15:05:25\",      " +
                "	\"lastid\": 17950,                         " +
                "	\"type\": \"dscalarm\",                    " +
                "	\"deviceid\": \"iRemote8005000000001\",    " +
                "	\"intparam\": 0,                           " +
                "	\"warningstatus\": 9,                      " +
                "	\"warningstatuses\": \"[9]\",              " +
                "	\"id\": 17951                              " +
                "}                                             ");
        alarmservice.sendAlarmMessage(json);
    }

    @Test
    public void deletewhennull() {
//		deviceInfoChangedDAO.delete(null);
//		ZwaveSubDevicePO byZwavesubdeviceid = deviceInfoChangedDAO.findByZwavesubdeviceid(1231);
//		System.out.println(byZwavesubdeviceid);
//		deviceInfoChangedDAO.delete(byZwavesubdeviceid);
    }

    @Test
    public void test1ndCache() {
        System.out.println("11");
        zwaveSubDeviceDAO.findByZwavedeviceid(101);
        System.out.println("11");
        zwaveSubDeviceDAO.findByZwavedeviceid(101);
    }
}
