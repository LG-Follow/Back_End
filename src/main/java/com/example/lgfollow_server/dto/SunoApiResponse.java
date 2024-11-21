package com.example.lgfollow_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SunoApiResponse {
    private String id;
    private String title;
    private String image_url;
    private String lyric;
    private String audio_url;
    private String video_url;
    private String created_at;
    private String model_name;
    private String status;
    private String prompt;
    private String type;
}
