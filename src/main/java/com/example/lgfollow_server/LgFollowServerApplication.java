package com.example.lgfollow_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("com.example.lgfollow_server.model")
public class LgFollowServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LgFollowServerApplication.class, args);
    }

}
