package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.service.SongService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongService songService;
    public SongController(SongService songService) {
        this.songService = songService;
    }

    
    // 사용자 ID를 사용해 노래 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Song>> getSongsByUserId(@PathVariable Long userId) {
        List<Song> songs = songService.getSongsByUserId(userId);
        return ResponseEntity.ok(songs);
    }

    // 노래 id 받고 그 노래 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        SongDTO songDTO = songService.getSongById(id);
        return ResponseEntity.ok(songDTO);
    }
}
