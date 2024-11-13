package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
