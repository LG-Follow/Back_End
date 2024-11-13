package com.example.lgfollow_server.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PromptGetDto {
    private Long image_id;
    private String prompt_text;
}
