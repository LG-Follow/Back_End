package com.example.lgfollow_server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.lgfollow_server.config.S3Config;
import com.example.lgfollow_server.dto.ImageSendDto;
import com.example.lgfollow_server.dto.PromptGetDto;
import com.example.lgfollow_server.model.Image;
import com.example.lgfollow_server.model.Prompt;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.ImageRepository;
import com.example.lgfollow_server.repository.PromptRepository;
import com.example.lgfollow_server.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ImageService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;
    private final PromptRepository promptRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String Topic = "image-topic";
    private final SongService songService;  // SongService 주입1

    public ImageService(ImageRepository imageRepository, AmazonS3 s3Client, UsersRepository usersRepository, KafkaTemplate<String, Object> kafkaTemplate, PromptRepository promptRepository) {
        this.imageRepository = imageRepository;
        this.s3Client = s3Client;
        this.usersRepository = usersRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.promptRepository = promptRepository;
        this.songService = songService;  // SongService 초기화1
    }


    public ImageSendDto uploadImage(MultipartFile image, Long user_id, boolean is_active, Long version) throws IOException {
        Image newImage = new Image();
        Users user = usersRepository.findById(user_id).orElse(null);
        String fileName = image.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".")); // 확장자 추출

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(extension); //파일 형식 설정

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, image.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String image_url = s3Client.getUrl(bucketName, fileName).toString();

        newImage.setImageName(fileName);
        newImage.setImageUrl(image_url);
        newImage.setActive(is_active);
        newImage.setImageType(extension);
        newImage.setVersion(version);
        newImage.setUser(user);

        imageRepository.save(newImage);

        ImageSendDto imageSendDto = new ImageSendDto();
        imageSendDto.setImage_id(newImage.getId());
        imageSendDto.setImage_url(image_url);
        imageSendDto.setUser_id(user_id);
        imageSendDto.setVersion(version);
        imageSendDto.setActive(is_active);

        return imageSendDto;
    }

    public void sendToFlask(ImageSendDto imageSendDto) {
        kafkaTemplate.send(Topic, imageSendDto);
        System.out.println("send Image Data: " + imageSendDto);
    }

    @Transactional
    @KafkaListener(topics = "prompt-topic")
    public void getPrompt(PromptGetDto promptGetDto) {
        log.info("Get Prompt: " + promptGetDto);

        Prompt prompt = new Prompt();
        prompt.setImage(imageRepository.findById(promptGetDto.getImage_id()).orElse(null));
        prompt.setPromptText(promptGetDto.getPrompt_text());
        prompt.setCreatedAt(LocalDateTime.now());

        promptRepository.save(prompt);
        // 프롬프트 저장 후 SongService를 호출하여 노래 생성1 Id로 프롬프트 가져옴
        songService.generateSongFromPrompt(prompt.getId());
    }
}
