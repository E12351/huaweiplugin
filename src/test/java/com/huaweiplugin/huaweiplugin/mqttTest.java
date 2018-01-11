package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Dto.MqttPublishDto;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class mqttTest {

    @Autowired
    private mqttUtils mqttutils;

    @Value("${topic.prefix}")
    private String prefix;

    @Test
    public void mqtt() throws MqttException {
        MqttPublishDto mqttPublishDto = new MqttPublishDto();
        mqttPublishDto.setTopic("topic/"+prefix);
        mqttPublishDto.setMessage("Onna enooooo");
        mqttutils.sendMsg( mqttPublishDto, "12354");
    }
}
