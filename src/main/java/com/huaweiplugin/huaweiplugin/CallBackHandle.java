package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Dto.MqttPublishDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CallBackHandle {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private mqttUtils mqttutils;

    @Value("${topic.callBack}")
    private String topic;

    @RequestMapping(value = "device/change" ,  method = RequestMethod.POST, produces={"application/json"} )
    public boolean login(@RequestBody String body) throws Exception {

        MqttPublishDto mqttPublishDto = new MqttPublishDto();

        Map<String, String> data = new HashMap<>();
        data = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(body, data.getClass());

        String notifyType    = data.get("notifyType");
//        System.out.println("-------> : "+notifyType);
        switch (notifyType){
            case "deviceDataChanged":
            {
                String deviceId     = data.get("deviceId");
                String event        = data.get("data");
                log.info("deviceDataChanged: deviceId :{} data : {}",deviceId,event);
                break;
            }

            case "bindDevice":
            {
                String deviceId     = data.get("deviceId");
                log.info("bindDevice: {}",deviceId);
                break;

            }

            case "deviceEvent":
            {
                log.info("deviceEvent: {}");
                break;

            }

            default:
                log.info("notifyType Error: {}",data);
        }

        mqttPublishDto.setTopic(topic);
        mqttPublishDto.setMessage(String.valueOf(data));
        mqttutils.sendMsg( mqttPublishDto );

        return true;
    }
}
