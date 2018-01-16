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

    @Value("${topic.prefix.callBack}")
    private String prefix;

    @RequestMapping(value = "device/change" ,  method = RequestMethod.POST, produces={"application/json"} )
    public boolean login(@RequestBody String body) throws Exception {

        Map<String, String> data = new HashMap<>();
        data = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(body, data.getClass());

        MqttPublishDto mqttPublishDto = new MqttPublishDto();
        mqttPublishDto.setTopic(prefix);
        mqttPublishDto.setMessage(String.valueOf(data));
        mqttutils.sendMsg( mqttPublishDto, data.get("deviceId"));

        return true;
    }
}
