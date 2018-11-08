package cn.com.isurpass.securityplatform.service;

import cn.com.isurpass.securityplatform.alarm.AlarmplatformConnectionManager;
import cn.com.isurpass.securityplatform.dao.GatewayUserDao;
import cn.com.isurpass.securityplatform.dao.UserDao;
import cn.com.isurpass.securityplatform.dao.ZwaveSubDeviceDAO;
import cn.com.isurpass.securityplatform.dao.ZwavedeviceDAO;
import cn.com.isurpass.securityplatform.domain.GatewayUserPO;
import cn.com.isurpass.securityplatform.domain.UserPO;
import cn.com.isurpass.securityplatform.domain.ZwaveSubDevicePO;
import cn.com.isurpass.securityplatform.domain.Zwavedevice;
import cn.com.isurpass.securityplatform.message.vo.Event;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;

@Component
public class AlarmService {
	
	private static Log log = LogFactory.getLog(AlarmService.class);
    @Autowired
    private ZwavedeviceDAO zddao;
    @Autowired
    private UserDao userdao;
    @Autowired
    private GatewayUserDao gwudao;
    @Autowired
    private ZwaveSubDeviceDAO zwaveSubDeviceDAO;
    private static final String DEVICE_TYPE_DSC = "47";
    private static final String ALARM_TYPE_DSC_ALARM = "dscalarm";
    private static final String ALARM_CODE_ALARM_PREFIX = "E";
    private static final String ALARM_CODE_UN_ALARM_PREFIX = "R";
    private static final HashSet<String> DSC_SELF_WARNING_TYPE_SET = new HashSet<>();
    private static final int DEFAULT_ZONE = 1;

    static{
        DSC_SELF_WARNING_TYPE_SET.add("dscfkeyalarm");
        DSC_SELF_WARNING_TYPE_SET.add("unalarmdscfkeyalarm");
        DSC_SELF_WARNING_TYPE_SET.add("dscakeyalarm");
        DSC_SELF_WARNING_TYPE_SET.add("unalarmdscakeyalarm");
        DSC_SELF_WARNING_TYPE_SET.add("dscpkeyalarm");
        DSC_SELF_WARNING_TYPE_SET.add("unalarmdscpkeyalarm");
    }

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
            if (!DSC_SELF_WARNING_TYPE_SET.contains(event.getType()) && DEVICE_TYPE_DSC.equals(zd.getDevicetype()) && event.getWarningstatus() != 0) {
                subdevicetype = getTrueAlarmCode(event, subdevicetype, zd);
                if (StringUtils.isBlank(subdevicetype)) {
                    log.info("subdevicetype is null");
                    return;
                }
                zone = event.getWarningstatus();
            }
        }

        if (DSC_SELF_WARNING_TYPE_SET.contains(event.getType())) {
            zone = DEFAULT_ZONE;
        }

        UserPO u = queryUser(event);
        if (u == null)
            return;

        AlarmplatformConnectionManager.getInstance().sendMessage(String.valueOf(u.getMonitoringstationid()), event, u, zone, subdevicetype);
    }

    private String getTrueAlarmCode(Event event, String subdevicetype, Zwavedevice zd) {
        int channelid = event.getWarningstatus();
        ZwaveSubDevicePO dicPO = zwaveSubDeviceDAO.findByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), channelid);
        if (dicPO != null) {
            subdevicetype = dicPO.getSubdevicetype();
            if ( StringUtils.isBlank(subdevicetype))
            	return null ;
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



