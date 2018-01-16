package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Dto.MqttPublishDto;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.huaweiplugin.Utils.Constant;

@Service
public class mqttUtils  {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MqttClient sampleClient;

    public void sendMsg(MqttPublishDto mqttDto, String endpoint) throws MqttException {

        String topic        = endpoint+"/" + mqttDto.getTopic();
        String content      = mqttDto.getMessage();
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(0);
        sampleClient.publish(topic, message);

        log.info("Msg Sent: {}  {}",topic,content);

    }

    @Bean
    public MqttClient mqttConnection() throws MqttException {
        String clientId     =  MqttClient.generateClientId();
        String broker       =  Constant.BROKER;

        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();

        connOpts.setCleanSession(true);
        connOpts.setUserName(Constant.BROKER_USER);
        connOpts.setPassword(Constant.BROKER_PSWRD.toCharArray());

        sampleClient.setCallback(new mqttCallback());
        sampleClient.connect(connOpts);

        sampleClient.subscribe(Constant.BROKER_SUB);

        return sampleClient;
    }

}
