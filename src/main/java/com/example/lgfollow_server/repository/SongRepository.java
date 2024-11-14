package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByUserId(Long userId);
}
