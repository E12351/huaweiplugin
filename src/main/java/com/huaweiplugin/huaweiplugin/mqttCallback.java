package com.huaweiplugin.huaweiplugin;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

public class mqttCallback implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    public requests request;

    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection lost! {}",cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("New msg received: { Topic : {} payload : {} }",topic,new String(message.getPayload()));
//        mqttUtils.hnadleMsg(topic, message);
        try{
            System.out.println(request);
            request.regDirectDevice("xxxx-xxxx-xxxxx-xxxx");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("Msg delivered." + token);
    }

}
