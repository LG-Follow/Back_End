package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.dto.ImageSendDto;
import com.example.lgfollow_server.model.Prompt;
import com.example.lgfollow_server.model.Song;
import com.example.lgfollow_server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageSendDto> uploadImage(@RequestParam("image") MultipartFile image,
                                                    @RequestParam("user_id") Long user_id,
                                                    @RequestParam(value = "is_active", defaultValue = "false") boolean is_active,
                                                    @RequestParam(value = "version", defaultValue = "1") Long version) throws IOException {
        ImageSendDto imageSendDto = imageService.uploadImage(image, user_id, is_active, version);
        imageService.sendToFlask(imageSendDto);
        return ResponseEntity.ok(imageSendDto);
    }

}
