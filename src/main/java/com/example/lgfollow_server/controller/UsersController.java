package com.example.lgfollow_server.controller;

import com.example.lgfollow_server.dto.UserDto;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.UsersRepository;
import com.example.lgfollow_server.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<Users> enrollUser(@RequestBody UserDto userDto) {
        Users user = usersService.enrollUser(userDto);
        return ResponseEntity.ok(user);
    }
}
