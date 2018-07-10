package cn.com.isurpass.securityplatform.message.processor;

import cn.com.isurpass.securityplatform.dao.DeviceInfoChangedDAO;
import cn.com.isurpass.securityplatform.domain.DeviceInfoChangedPO;
import cn.com.isurpass.securityplatform.domain.Gateway;
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
    private DeviceInfoChangedDAO deviceInfoChangedDAO;

    @Override
    protected void process(Event event, Gateway gateway) {
        JSONObject json = JSON.parseObject(event.getObjparam());
        Integer zwavedeviceid = json.getInteger("zwavedeviceid");
        JSONArray zsdJSONArray = json.getJSONArray("zwavesubdevice");
        for (int i = 0; zsdJSONArray != null && i < zsdJSONArray.size(); i++) {
            JSONObject jsonObject = zsdJSONArray.getJSONObject(i);
            saveDeviceInfoChangePO(zwavedeviceid, jsonObject);
        }
    }

    private void saveDeviceInfoChangePO(Integer zwavedeviceid, JSONObject jsonObject) {
        DeviceInfoChangedPO deviceInfoChangedPO = new DeviceInfoChangedPO();
        Integer zwavesubdeviceid = jsonObject.getInteger("zwavesubdeviceid");
        deviceInfoChangedPO.setZwavesubdeviceid(zwavesubdeviceid);
        deviceInfoChangedPO.setZwavedeviceid(zwavedeviceid);
        deviceInfoChangedPO.setChannelid(jsonObject.getInteger("channelid"));
        deviceInfoChangedPO.setName(jsonObject.getString("name"));
        deviceInfoChangedPO.setName(jsonObject.getString("subdevicetype"));
        deviceInfoChangedDAO.save(deviceInfoChangedPO);
    }
}
