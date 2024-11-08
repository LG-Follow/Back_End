package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
