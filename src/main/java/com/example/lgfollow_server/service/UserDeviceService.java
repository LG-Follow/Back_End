package com.example.lgfollow_server.service;

import com.example.lgfollow_server.model.Device;
import com.example.lgfollow_server.model.UserDevice;
import com.example.lgfollow_server.model.UserDeviceId;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.DeviceRepository;
import com.example.lgfollow_server.repository.UserDeviceRepository;
import com.example.lgfollow_server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserDeviceService {

    private final UsersRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Autowired
    public UserDeviceService(UsersRepository userRepository, DeviceRepository deviceRepository, UserDeviceRepository userDeviceRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.userDeviceRepository = userDeviceRepository;
    }

    // 회원 ID로 userDevice 목록 조회
    public List<UserDevice> getUserDevicesByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public void addDeviceToUser(Map<String, String> qrData, Long userId) {
        // 1. 사용자 정보 가져오기
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2. QR 코드로부터 Device ID 추출
        Long deviceId = Long.parseLong(qrData.get("deviceId"));
        String location = qrData.get("location");

        // 3. 기기 정보 가져오기
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new IllegalArgumentException("Device not found"));

        // 4. UserDevice 엔터티 생성
        UserDeviceId userDeviceId = new UserDeviceId(userId, deviceId);
        UserDevice userDevice = new UserDevice();
        userDevice.setId(userDeviceId);
        userDevice.setUser(user);
        userDevice.setDevice(device);
        userDevice.setLocation(location);
        userDevice.setActive(true);

        // 5. UserDevice 저장
        userDeviceRepository.save(userDevice);
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

