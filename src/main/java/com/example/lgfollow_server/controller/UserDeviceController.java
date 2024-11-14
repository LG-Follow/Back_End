package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.model.UserDevice;
import com.example.lgfollow_server.service.UserDeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-device")
public class UserDeviceController {

    private final UserDeviceService userDeviceService;

    public UserDeviceController(UserDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    // 회원 ID의 userDevice 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserDevice>> getUserDevicesByUserId(@PathVariable Long userId) {
        List<UserDevice> userDevices = userDeviceService.getUserDevicesByUserId(userId);
        return ResponseEntity.ok(userDevices);
    }
    // 온오프 기능
    @PostMapping("/toggle/{id}")
    public ResponseEntity<UserDevice> toggleDeviceStatus(@PathVariable Long id) {
        UserDevice updatedDevice = userDeviceService.toggleDeviceStatus(id);
        return ResponseEntity.ok(updatedDevice);
    }
}
