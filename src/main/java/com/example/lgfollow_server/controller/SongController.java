package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.dto.SongDto;
import com.example.lgfollow_server.model.Song;
import com.example.lgfollow_server.repository.SongRepository;
import com.example.lgfollow_server.service.SongSendService;
import com.example.lgfollow_server.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongService songService;
    private final SongSendService songSendService;
    private final SongRepository songRepository;

    public SongController(SongService songService, SongRepository songRepository, SongSendService songSendService) {
        this.songService = songService;
        this.songRepository = songRepository;
        this.songSendService = songSendService;
    }

    
    // 사용자 ID를 사용해 노래 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Song>> getSongsByUserId(@PathVariable Long userId) {
        List<Song> songs = songService.getSongsByUserId(userId);
        return ResponseEntity.ok(songs);
    }

    // 노래 id 받고 그 노래 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        SongDto songDto = songService.getSongById(id);
        return ResponseEntity.ok(songDto);
    }

    @PostMapping("/play/{id}")
    public String playSong(@PathVariable Long id) {
        Song song = songRepository.findById(id).orElse(null);
        songSendService.setCurrentSong(song);
        songSendService.setCurrentTime(0);
        songSendService.playSong();
        return "success";
    }
    //test
    // @PostMapping("/generate/{promptId}")
    // public ResponseEntity<List<Song>> generateSongFromPrompt(@PathVariable Long promptId) {
    //     List<Song> songs = songService.generateSongFromPrompt(promptId);
    //     return ResponseEntity.ok(songs);
    // }

}
