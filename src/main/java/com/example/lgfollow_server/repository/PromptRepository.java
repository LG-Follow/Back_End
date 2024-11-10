package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    
}

// 그냥 promptrepo 추가함