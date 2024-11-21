package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    // @Query("SELECT s FROM Song s JOIN s.prompt p JOIN p.image i where i.user.id = :userId")
    List<Song> findAllByPrompt_Image_User_Id(Long userId);

}

