package com.example.lgfollow_server.repository;

import com.example.lgfollow_server.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    // 회원 ID로 userDevice 목록 조회
    List<UserDevice> findByUserId(Long userId);
}
