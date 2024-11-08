package com.example.lgfollow_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ImageSendDto {
    private Long image_id;
    private Long user_id;
    private String image_url;
    private boolean isActive;
    private Long version;
}
