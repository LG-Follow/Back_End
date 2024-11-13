package com.example.lgfollow_server.service;

import com.example.lgfollow_server.model.UserDevice;
import com.example.lgfollow_server.repository.UserDeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public UserDeviceService(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    // 회원 ID로 userDevice 목록 조회
    public List<UserDevice> getUserDevicesByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    @Transactional
    public UserDevice toggleDeviceStatus(Long userDeviceId) {
        UserDevice userDevice = userDeviceRepository.findById(userDeviceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid userDevice ID: " + userDeviceId));
        
        // isActive 값 반전
        userDevice.setActive(!userDevice.isActive());
        
        return userDeviceRepository.save(userDevice);
    }
}
