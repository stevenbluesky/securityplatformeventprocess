package cn.com.isurpass.securityplatform.service;

import java.util.Optional;

import cn.com.isurpass.securityplatform.dao.DeviceInfoChangedDAO;
import cn.com.isurpass.securityplatform.domain.DeviceInfoChangedPO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.securityplatform.alarm.AlarmplatformConnectionManager;
import cn.com.isurpass.securityplatform.dao.GatewayUserDao;
import cn.com.isurpass.securityplatform.dao.UserDao;
import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.GatewayUserPO;
import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;

@Component
public class AlarmService {
    @Autowired
    private ZwavedeviceDAO zddao;
    @Autowired
    private UserDao userdao;
    @Autowired
    private GatewayUserDao gwudao;
    @Autowired
    private DeviceInfoChangedDAO deviceInfoChangedDAO;
    private static final String DEVICE_TYPE_DSC = "47";
    private static final String ALARM_TYPE_DSC_ALARM = "dscalarm";
    private static final String ALARM_CODE_ALARM_PREFIX = "E";
    private static final String ALARM_CODE_UN_ALARM_PREFIX = "R";

    public void sendAlarmMessage(JSONObject json) {
        Event event = JSON.toJavaObject(json, Event.class);

        int zone = 1;
        String subdevicetype = null;
        if (event.getZwavedeviceid() != 0) {
            Zwavedevice zd = queryZwavedevice(event);
            if (zd == null)
                return;
            if (zd.getArea() != null)
                zone = zd.getArea();
            if (DEVICE_TYPE_DSC.equals(zd.getDevicetype())) {
                subdevicetype = getTrueAlarmCode(event, subdevicetype, zd);
            }
        }

        UserPO u = queryUser(event);
        if (u == null)
            return;

        AlarmplatformConnectionManager.getInstance().sendMessage(String.valueOf(u.getOrganizationid()), event.getType(), u.getGroupid(), u.getSupcode(), zone, subdevicetype);
    }

    private String getTrueAlarmCode(Event event, String subdevicetype, Zwavedevice zd) {
        int channelid = event.getIntparam();
        DeviceInfoChangedPO dicPO = deviceInfoChangedDAO.findByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), channelid);
        if (dicPO != null) {
            subdevicetype = dicPO.getSubdevicetype();
            if (ALARM_TYPE_DSC_ALARM.equals(event.getType())) {
                subdevicetype = ALARM_CODE_ALARM_PREFIX + subdevicetype;
            } else {
                subdevicetype = ALARM_CODE_UN_ALARM_PREFIX + subdevicetype;
            }
        }
        return subdevicetype;
    }

    private Zwavedevice queryZwavedevice(Event event) {
        if (event.getZwavedeviceid() == 0)
            return null;

        Optional<Zwavedevice> oz = zddao.findById(event.getZwavedeviceid());
        if (oz.isPresent())
            return oz.get();
        return null;
    }

    private UserPO queryUser(Event event) {
        if (StringUtils.isBlank(event.getDeviceid()))
            return null;
        GatewayUserPO gu = gwudao.findByDeviceid(event.getDeviceid());

        if (gu == null || gu.getUserid() == null)
            return null;

        Optional<UserPO> user = userdao.findById(gu.getUserid());

        if (user.isPresent())
            return user.get();
        return null;
    }

}



