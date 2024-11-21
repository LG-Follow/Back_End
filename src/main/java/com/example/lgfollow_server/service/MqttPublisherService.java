//package com.example.lgfollow_server.service;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MqttPublisherService {
//    private final MqttClient mqttClient;
//
//    @Autowired
//    public MqttPublisherService(MqttClient mqttClient) {
//        this.mqttClient = mqttClient;
//    }
//
//    public void publish(String topic, String payload) {
//        try{
//            MqttMessage message = new MqttMessage(payload.getBytes());
//            message.setQos(1);
//            mqttClient.publish(topic, message);
//        }catch (MqttException e){
//            throw new RuntimeException(e);
//        }
//    }
//}
