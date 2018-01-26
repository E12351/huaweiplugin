package com.huaweiplugin.services;

import com.huaweiplugin.Dto.MqttPublishDto;
import com.huaweiplugin.huaweiplugin.Application;
import com.huaweiplugin.Repository.RepositoryDeviceData;
import com.huaweiplugin.huaweiplugin.deviceData;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.huaweiplugin.Utils.JsonUtil;
import com.huaweiplugin.Utils.Constant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Controller
@Service
public class mqttUtils  {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    // topic initialize from broker perspective.
    @Value("${topic.pub}")
    private String BROKER_SUB;

//    @Value("${topic.sub}")
//    private String sub;

    @Autowired
    public MqttClient Client;

    @Autowired
    private requests req;

    @Autowired
    private mqttUtils mqttutils;

    @Autowired
    private RepositoryDeviceData repository;

//    @Autowired
//    private requests req;

    public void sendMsg(MqttPublishDto mqttDto) throws MqttException {

        String topic        = mqttDto.getTopic();
        String content      = mqttDto.getMessage();
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(1);
        Client.publish(topic, message);

        log.info("Msg Sent: {}  {}",topic,content);

    }

    @Bean
    public MqttClient mqttConnection() throws MqttException {
        String clientId     =  MqttClient.generateClientId();
        String broker       =  Constant.BROKER;

        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient Client = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();

        connOpts.setCleanSession(true);
        connOpts.setUserName(Constant.BROKER_USER);
        connOpts.setPassword(Constant.BROKER_PSWRD.toCharArray());

        Client.connect(connOpts);
        Client.subscribe(BROKER_SUB, 0);
        Client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                        log.info("Connection lost! {}",cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                log.info("New msg received: { Topic : {} payload : {} }",topic,new String(message.getPayload()));
                mqttutils.hnadleMsg(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info("Msg delivered." + token);
            }
        });

        return Client;
    }

    public Map<String, String> splitToic(String Topic) throws Exception{
        Map<String, String> topic = new HashMap<>();

        String[] parts = Topic.split("/");
        topic.put("prefix",parts[0]);
        topic.put("method",parts[1]);
        topic.put("deviceId",parts[2]);
        return topic;
    }

    public Map<String, String> methodInovacation(MqttMessage message){
        Map<String, String> messageBodyInfor = new HashMap<>();
        messageBodyInfor = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(message.toString(), messageBodyInfor.getClass());
        return messageBodyInfor;
    }

    public void hnadleMsg(String topic, MqttMessage message) throws Exception {

//        Map data = splitToic(topic);
        Map data = methodInovacation(message);

        String method = (String) data.get("method");

        switch (method){
            case "direct":
            {
                log.info("direct executed.");

                String mac = (String) data.get("mac");
                HashMap responce = req.regDirectDevice(mac);

                String verifyCode = (String) responce.get("verifyCode");
                String psk = (String) responce.get("psk");
                String deviceId = (String) responce.get("deviceId");

                log.info("verifyCode    : "+ verifyCode );
                log.info("psk 		    : "+ psk );
                log.info("deviceId 	    : "+ deviceId );

                try {
                    repository.save(new deviceData(mac, deviceId, verifyCode, psk));
                }catch (Exception e){
                    log.info("Exception : {}",e.getMessage());
                }

                MqttPublishDto mqttDto = new MqttPublishDto();
                mqttDto.setTopic("Reg");

                Map<String, String> msgBody = new HashMap<>();
                msgBody.put("deviceId",deviceId);
                String jsonBody = JsonUtil.jsonObj2Sting(msgBody);

                mqttDto.setMessage(jsonBody);

                sendMsg(mqttDto);

                log.info("direct device registration success.");
                break;
            }

            case "NoNdirect": {
                log.info("NoN direct executed.");
                break;
            }
            case "ActionResponce": {
                log.info("ActionResponce executed.");

                String deviceId = (String) data.get("deviceId");

                break;
            }
            default :
                log.info("Invalid method Error! Method : {}",method);

        }

    }

}
