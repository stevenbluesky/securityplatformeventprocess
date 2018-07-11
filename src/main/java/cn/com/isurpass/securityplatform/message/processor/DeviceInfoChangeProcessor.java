package cn.com.isurpass.securityplatform.message.processor;

import cn.com.isurpass.securityplatform.dao.ZwaveSubDeviceDAO;
import cn.com.isurpass.securityplatform.domain.Gateway;
import cn.com.isurpass.securityplatform.domain.ZwaveSubDevicePO;
import cn.com.isurpass.securityplatform.message.vo.Event;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "deviceinfochanged")
@Scope("prototype")
public class DeviceInfoChangeProcessor extends GatewayBaseProcessor {

    @Autowired
    private ZwaveSubDeviceDAO zwaveSubDeviceDAO;

    @Override
    protected void process(Event event, Gateway gateway) {
        JSONObject json = JSON.parseObject(event.getObjparam());
        Integer zwavedeviceid = json.getInteger("zwavedeviceid");
        zwaveSubDeviceDAO.deleteAll(zwaveSubDeviceDAO.findByZwavedeviceid(zwavedeviceid));
        JSONArray zsdJSONArray = json.getJSONArray("zwavesubdevice");
        for (int i = 0; zsdJSONArray != null && i < zsdJSONArray.size(); i++) {
            JSONObject jsonObject = zsdJSONArray.getJSONObject(i);
            saveDeviceInfoChangePO(zwavedeviceid, jsonObject);
        }
    }

    private void saveDeviceInfoChangePO(Integer zwavedeviceid, JSONObject jsonObject) {
        ZwaveSubDevicePO zwaveSubDevicePO = new ZwaveSubDevicePO();
        Integer zwavesubdeviceid = jsonObject.getInteger("zwavesubdeviceid");
        zwaveSubDevicePO.setZwavesubdeviceid(zwavesubdeviceid);
        zwaveSubDevicePO.setZwavedeviceid(zwavedeviceid);
        zwaveSubDevicePO.setChannelid(jsonObject.getInteger("channelid"));
        zwaveSubDevicePO.setName(jsonObject.getString("name"));
        zwaveSubDevicePO.setSubdevicetype(jsonObject.getString("subdevicetype"));
        zwaveSubDeviceDAO.save(zwaveSubDevicePO);
    }
}
