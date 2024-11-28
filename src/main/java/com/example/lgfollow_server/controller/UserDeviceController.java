package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.model.UserDevice;
import com.example.lgfollow_server.service.UserDeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // QR코드로 기기 등록
    @PostMapping("/add")
    public ResponseEntity<String> addDeviceToUser(@RequestBody Map<String, String> qrData, @RequestParam Long userId) {
        try {
            userDeviceService.addDeviceToUser(qrData, userId);
            return ResponseEntity.ok("Device added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    // 온오프 기능
    @PostMapping("/toggle/{id}")
    public ResponseEntity<UserDevice> toggleDeviceStatus(@PathVariable Long id) {
        UserDevice updatedDevice = userDeviceService.toggleDeviceStatus(id);
        return ResponseEntity.ok(updatedDevice);
    }
}
