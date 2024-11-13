package com.example.lgfollow_server.service;

import com.example.lgfollow_server.model.Prompt;
import com.example.lgfollow_server.model.Song;
import com.example.lgfollow_server.repository.PromptRepository;
import com.example.lgfollow_server.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class SongService {

    private final PromptRepository promptRepository;
    private final SongRepository songRepository;
    private final RestTemplate restTemplate;
    private final String sunoApiUrl = "http://localhost:3000/generate-music"; // Suno API 서버 URL

    @Autowired
    public SongService(PromptRepository promptRepository, SongRepository songRepository, RestTemplate restTemplate) {
        this.promptRepository = promptRepository;
        this.songRepository = songRepository;
        this.restTemplate = restTemplate;
    }

    public Song generateSongFromPrompt(Long promptId) {
        Optional<Prompt> promptOptional = promptRepository.findById(promptId);
        if (promptOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid prompt ID: " + promptId);
        }
        
        Prompt prompt = promptOptional.get();

        // Suno API 요청 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestJson = "{\"prompt_text\": \"" + prompt.getPromptText() + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        // Suno API 호출
        ResponseEntity<String> response = restTemplate.exchange(sunoApiUrl, HttpMethod.POST, request, String.class);
        String songUrl = response.getBody(); // 반환된 음악 URL
        
        // Song 엔티티 생성 및 데이터베이스에 저장
        Song song = new Song();
        song.setPrompt(prompt);
        song.setTitle("Generated Song"); // 기본 제목, 필요시 수정 가능
        song.setDescription(prompt);
        song.setSongUrl(songUrl);
        song.setSize(5); // 걍 5로함
        song.setDuration(LocalTime.of(0, 3, 30)); // 3분 30초 예시

        return songRepository.save(song);
    }
}
