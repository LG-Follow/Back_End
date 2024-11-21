package com.example.lgfollow_server.config;

import com.example.lgfollow_server.service.SongSendService;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    private static final String MQTT_BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "spring";
    private static final String[] TOPICS = {"sensor/pir/#"};

    @Bean
    public MqttPahoClientFactory mqttClientFactory() { // Mqtt 클라이언트를 설정하는 팩토리 클래스임(팩토리 클래스: 객체를 생성하는 역할을 담당하는 클래스)
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory(); // DefaultMqttPahoClientFactory 인스턴스를 생성하여 MQTT 클라이언트 설정을 관리
        MqttConnectOptions options = new MqttConnectOptions(); // MQTT 연결 옵션을 설정할 수 있는 객체를 생성
        options.setServerURIs(new String[]{MQTT_BROKER_URL}); // MqttConnectOptions에서 사용할 서버 URI를 설정
        factory.setConnectionOptions(options); // 팩토리 객체에 MqttConnectOptions를 적용하여, 클라이언트가 연결할 때 설정된 URI와 옵션을 사용하도록 합니다
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() { // MQTT 메시지를 수신하기 위한 입력 채널을 생성하고 빈으로 등록합니다
        return new DirectChannel(); // DirectChannel은 메시지를 직접적으로 수신하여 전달하는 채널로, 이 채널을 통해 MQTT에서 수신한 메시지를 다른 컴포넌트로 전달
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inboundAdaptor() { //MQTT 수신 어댑터를 설정하고 빈으로 등록합니다. 이 어댑터는 MQTT에서 수신된 메시지를 mqttInputChannel로 전달합니다.
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(CLIENT_ID + "_inbound", mqttClientFactory(), TOPICS); //  어댑터 생성 시 클라이언트 ID와 구독할 토픽 목록을 지정
        adapter.setCompletionTimeout(5000); // MQTT 메시지 전송 완료까지의 타임아웃을 5000ms(5초)로 설정합니다.
        adapter.setConverter(new DefaultPahoMessageConverter()); // 수신된 메시지를 변환할 기본 변환기를 설정합니다. 문자열이나 바이트 배열로 변환
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel()); // mqttInputChannel을 메시지 출력 채널로 설정하여, 수신된 MQTT 메시지를 mqttInputChannel로 전달하도록 합니다.
        return adapter;
    }

    @Bean
    public MessageHandler mqttOutputHandler() { //  MQTT 송신 핸들러를 설정하고 빈으로 등록합니다.
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(CLIENT_ID + "_outbound", mqttClientFactory()); // 송신 핸들러 생성 시 클라이언트 ID와 MQTT 클라이언트 팩토리를 설정합니다.
        messageHandler.setAsync(true); // 비동기적으로 메시지를 전송하도록 설정
        messageHandler.setDefaultQos(1);
        return messageHandler; // 설정이 완료된 메시지 송신 핸들러를 반환
    }

    @Bean
    public MessageChannel mqttOutputChannel() { // MQTT 메시지를 송신할 때 사용할 출력 채널을 생성하고 빈으로 등록
        return new DirectChannel(); // DirectChannel 객체를 반환하여, 송신 메시지가 직접 전달될 수 있도록 설정합니다.
    }
}
