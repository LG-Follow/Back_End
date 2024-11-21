package com.example.lgfollow_server.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
public interface MqttGateway {
    void send(String message);

    void send(@Header("mqtt_topic") String topic, String message);
}
