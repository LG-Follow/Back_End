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


}
