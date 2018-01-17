package com.huaweiplugin.huaweiplugin;

import com.huaweiplugin.Dto.MqttPublishDto;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.huaweiplugin.Utils.Constant;

import java.util.HashMap;
import java.util.Map;

@Service
public class mqttUtils  {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${topic.sub}")
    private String BROKER_SUB;

    @Autowired
    public MqttClient Client;

    public void sendMsg(MqttPublishDto mqttDto, String endpoint) throws MqttException {

        String topic        = endpoint+"/" + mqttDto.getTopic();
        String content      = mqttDto.getMessage();
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(0);
        Client.publish(topic, message);

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

        sampleClient.subscribe(BROKER_SUB);

        return sampleClient;
    }

    public static Map<String, String> splitToic(String Topic) throws Exception{
        Map<String, String> topic = new HashMap<>();

        String[] parts = Topic.split("/");
        topic.put("prefix",parts[0]);
        topic.put("method",parts[1]);
        topic.put("deviceId",parts[2]);
        return topic;
    }

    public static void hnadleMsg(String topic, MqttMessage message) throws Exception {

        Map topicParts = mqttUtils.splitToic(topic);

        String method = (String) topicParts.get("method");
        String deviceId = (String) topicParts.get("deviceId");

        switch (method){
            case "direct":
                // send proper request with deviceId.
                log.info("direct excecuted.!");
                HashMap responce = new requests().regDirectDevice(deviceId);

                System.out.println("verifyCode 	: "+ responce.get("verifyCode") );
                System.out.println("psk 		: "+ responce.get("psk") );
                System.out.println("deviceId 	: "+ responce.get("deviceId") );

                break;

            case "NoNdirect":
                log.info("NoN direct excecuted.!");
                break;
        }

    }

}
