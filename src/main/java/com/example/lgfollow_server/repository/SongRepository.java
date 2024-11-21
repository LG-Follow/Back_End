package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Song;
import com.example.lgfollow_server.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
public interface SongRepository extends JpaRepository<Song, Long> {
//    @Query("SELECT s FROM Song s JOIN s.prompt p JOIN p.image i WHERE i.user.id = :userId")
//    List<Song> findAllByUserId(@Param("userId") Long userId);
    List<Song> findAllByPrompt_Image_User_Id(Long userId);
}
