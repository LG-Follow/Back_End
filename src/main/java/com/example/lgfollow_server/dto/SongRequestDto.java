package com.example.lgfollow_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequestDto {
    private Long id;
    private String song_url;
}
