package com.example.lgfollow_server.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.lgfollow_server.config.S3Config;
import com.example.lgfollow_server.dto.ImageSendDto;
import com.example.lgfollow_server.model.Image;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.ImageRepository;
import com.example.lgfollow_server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;

    public ImageService(ImageRepository imageRepository, AmazonS3 s3Client, UsersRepository usersRepository) {
        this.imageRepository = imageRepository;
        this.s3Client = s3Client;
        this.usersRepository = usersRepository;
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
}
