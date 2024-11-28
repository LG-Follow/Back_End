package com.example.lgfollow_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SongDto {
    private Long id;
    private String title;
    private String description;
    private String song_url;
    private double size;
    private LocalTime duration;
    private LocalDateTime createdAt;
    private String image_url; //url 추가함
}
