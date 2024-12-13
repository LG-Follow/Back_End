package com.example.lgfollow_server.service;

import com.example.lgfollow_server.gateway.MqttGateway;
import com.example.lgfollow_server.model.Song;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Setter
public class SongSendService {
    private final MqttGateway mqttGateway;

    Map<String, Boolean> rooms = new HashMap<>();

    public SongSendService(MqttGateway mqttGateway) {
        this.mqttGateway = mqttGateway;
    }

    private Song currentSong;
    private int currentTime;

    public void playInitialSong() {
        // 모든 방에 initial: true 메시지 전송
        String[] rooms = {"room1", "room2"};
        String url = currentSong.getSongUrl();
        for (String room : rooms) {
            startMusicInRoom(room, url, 0, true);
        }
    }


//    public void playSong(){
//        rooms.forEach((room, isActive) -> {
//            if (isActive) { // 모션이 감지된 방에서만 음악 재생
//                startMusicInRoom(room, currentSong.getSongUrl(), currentTime);
//            }
//        });
//    }

    public void handleMotionDetected(String room){
        if (currentSong == null) {
            System.err.println("Error: No song is currently loaded.");
            return; // 더 이상 진행하지 않음
        }
        rooms.put(room, true);
        startMusicInRoom(room, currentSong.getSongUrl(), currentTime, false);
    }

    public void handleMotionStopped(String room){
        rooms.put(room, false);
        stopMusicInRoom(room);
    }

    @Scheduled(fixedRate = 1000)
    public void updateCurrentTime() {
        if (isAnyRoomActive()) {
            currentTime++;
            System.out.println("Updated time: " + currentTime);
        }
    }

    private void startMusicInRoom(String room, String url, int startTime, boolean initial) {
        long currentTimestamp = System.currentTimeMillis();
        String message = String.format("{\"url\": \"%s\", \"currentTime\": %d, \"timestamp\": %d, \"initial\": %b}", url, startTime, currentTimestamp, initial);
        mqttGateway.send("sensor/pir/" + room, message);
        System.out.println("음악 보내기 성공");
    }

    private void stopMusicInRoom(String room) {
        String message = "{\"stop\": true}";
        mqttGateway.send("sensor/pir/" + room, message);
        System.out.println("음악 중지 보내기 성공");
    }

    private boolean isAnyRoomActive() {
        return rooms.containsValue(true);
    }

}
