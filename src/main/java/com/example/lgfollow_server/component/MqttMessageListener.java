package com.example.lgfollow_server.component;

import com.example.lgfollow_server.service.SongSendService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageListener {
    private final SongSendService songSendService;

    public MqttMessageListener(SongSendService songSendService) {
        this.songSendService = songSendService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        String payload = message.getPayload().toString();

        if (topic.startsWith("sensor/pir/")) {
            String room = topic.replace("sensor/pir/", "");

            if ("motionDetected".equals(payload)) {
                songSendService.handleMotionDetected(room);
            }
            else if ("motionStopped".equals(payload)) {
                songSendService.handleMotionStopped(room);
            }
        }
    }
}
