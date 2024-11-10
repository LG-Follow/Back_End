// package com.example.lgfollow_server.service;

// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.ObjectMetadata;
// import com.amazonaws.services.s3.model.PutObjectRequest;
// import com.example.lgfollow_server.config.S3Config;
// import com.example.lgfollow_server.dto.ImageSendDto;
// import com.example.lgfollow_server.model.Image;
// import com.example.lgfollow_server.model.Users;
// import com.example.lgfollow_server.repository.ImageRepository;
// import com.example.lgfollow_server.repository.UsersRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;

// 위에 있는게 원래거임

package com.example.lgfollow_server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.lgfollow_server.dto.ImageSendDto;
import com.example.lgfollow_server.model.Image;
import com.example.lgfollow_server.model.Prompt;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.ImageRepository;
import com.example.lgfollow_server.repository.PromptRepository; //promptRepository 추가
import com.example.lgfollow_server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;
    private final PromptRepository promptRepository; //promptRepository 추가

    
    private final RestTemplate restTemplate; // 추가
    
    private final String aiServerUrl = "http://192.168.0.55:5000/generate-prompt"; // AI 서버 URL

    public ImageService(ImageRepository imageRepository, AmazonS3 s3Client, UsersRepository usersRepository) {
        this.imageRepository = imageRepository;
        this.s3Client = s3Client;
        this.usersRepository = usersRepository;

        //추가한것
        this.prompRepository = promptRepository;
        this.restTemplate = restTemplate;
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

        // AI 서버로 이미지 URL 보내기
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"image_url\":\"" + imageUrl + "\"}", headers);

        // AI 서버로 요청 전송 및 프롬프트 응답 받기
        ResponseEntity<String> response = restTemplate.exchange(aiServerUrl, HttpMethod.POST, request, String.class);
        String generatedPrompt = response.getBody();

        // 프롬프트 저장
        Prompt prompt = new Prompt();
        prompt.setImage(newImage);
        prompt.setPromptText(generatedPrompt);
        prompt.setCreatedAt(LocalDateTime.now());
        promptRepository.save(prompt);

        ImageSendDto imageSendDto = new ImageSendDto();
        imageSendDto.setImage_id(newImage.getId());
        imageSendDto.setImage_url(image_url);
        imageSendDto.setUser_id(user_id);
        imageSendDto.setVersion(version);
        imageSendDto.setActive(is_active);
        imageSendDto.setGenerated_prompt(generatedPrompt); // dto에 추가

        return imageSendDto;
    }
}
