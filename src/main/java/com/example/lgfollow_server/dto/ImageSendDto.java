package com.example.lgfollow_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ImageSendDto implements Serializable {
    private Long image_id;
    private Long user_id;
    private String image_url;
    private boolean isActive;
    private Long version;
}
