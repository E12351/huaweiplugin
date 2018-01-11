package com.huaweiplugin.huaweiplugin;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class mqttCallback implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(HuaweipluginApplication.class);

    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection lost!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("New msg received: { Topic : {} payload : {} }",topic,new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("Msg delivered." + token);
    }
}
