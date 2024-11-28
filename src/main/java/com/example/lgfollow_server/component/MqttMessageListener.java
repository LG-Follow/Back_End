package com.example.lgfollow_server.component;

import com.example.lgfollow_server.service.SongSendService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageListener { //Mqtt메세지 수신하는 클래스임
    private final SongSendService songSendService;

    public MqttMessageListener(SongSendService songSendService) {
        this.songSendService = songSendService;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel") //mqttInputChannel에서 들어오는 MQTT 메시지를 처리하기 위해 이 메서드를 연결합니다
    public void handleMessage(Message<?> message) { //Message<?>는 수신한 메시지 객체로, 헤더와 페이로드를 포함합니다.
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        String payload = message.getPayload().toString();

        if (topic.startsWith("sensor/pir/")) {
            String room = topic.replace("sensor/pir/", "");

            if ("motionDetected".equals(payload)) {
                songSendService.handleMotionDetected(room);
            }
            else if ("motionStopped".equals(payload)) {
                System.out.println("모션 없음 받음");
                songSendService.handleMotionStopped(room);
            }
        }
    }
}
