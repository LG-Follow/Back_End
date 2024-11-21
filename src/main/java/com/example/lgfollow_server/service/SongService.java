package com.example.lgfollow_server.service;

import com.example.lgfollow_server.dto.SongDto;
import com.example.lgfollow_server.dto.SunoApiResponse;
import com.example.lgfollow_server.model.Prompt;
import com.example.lgfollow_server.model.Song;
import com.example.lgfollow_server.repository.PromptRepository;
import com.example.lgfollow_server.repository.SongRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final PromptRepository promptRepository;
    private final SongRepository songRepository;
    private final RestTemplate restTemplate;
    private final String sunoApiUrl = "http://localhost:3000/api/generate"; // Suno API 서버 URL

    @Autowired
    public SongService(PromptRepository promptRepository, SongRepository songRepository, RestTemplate restTemplate) {
        this.promptRepository = promptRepository;
        this.songRepository = songRepository;
        this.restTemplate = restTemplate;
    }

    // Suno API 호출 및 곡 저장
    public List<Song> generateSongFromPrompt(Long promptId) {
        // Prompt 조회
        Optional<Prompt> promptOptional = promptRepository.findById(promptId);
        if (promptOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid prompt ID: " + promptId);
        }
        Prompt prompt = promptOptional.get();

        // Suno API 요청 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String requestJson = "{\"prompt\": \"" + prompt.getPromptText() + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        // Suno API 호출
        ResponseEntity<String> response = restTemplate.exchange(sunoApiUrl, HttpMethod.POST, request, String.class);
        String responseBody = response.getBody();

        // Suno API 응답 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<SunoApiResponse> songResponses;
        try {
            songResponses = objectMapper.readValue(responseBody, new TypeReference<List<SunoApiResponse>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse Suno API response: " + e.getMessage());
        }

        // Song 엔티티 생성 및 저장
        List<Song> savedSongs = new ArrayList<>();
        for (SunoApiResponse songResponse : songResponses) {
            Song song = new Song();
            song.setPrompt(prompt);
            song.setTitle(songResponse.getTitle().isEmpty() ? "Generated Song" : songResponse.getTitle());
            song.setDescription(songResponse.getLyric());
            song.setSongUrl(songResponse.getAudio_url());
            song.setSize(5.0); // 기본값 설정
            song.setDuration(LocalTime.of(0, 3, 30)); // 고정된 3분 30초
            savedSongs.add(songRepository.save(song));
        }

        return savedSongs;
    }

    // 사용자 ID로 노래 목록 조회
    public List<Song> getSongsByUserId(Long userId) {
        return songRepository.findAllByPrompt_Image_User_Id(userId);
    }

    // 노래 ID로 노래 정보 조회
    public SongDto getSongById(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.isEmpty()) {
            throw new IllegalArgumentException("Song not found for ID: " + id);
        }

        Song song = songOptional.get();
        return new SongDto(
                song.getId(),
                song.getTitle(),
                song.getDescription(),
                song.getSongUrl(),
                song.getSize(),
                song.getDuration(),
                song.getCreatedAt()
        );
    }
}
