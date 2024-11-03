package com.example.lgfollow_server.service;

import com.example.lgfollow_server.dto.UserDto;
import com.example.lgfollow_server.model.Users;
import com.example.lgfollow_server.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users enrollUser(UserDto userDto) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setLocation(userDto.getLocation());

        return usersRepository.save(user);
    }
}
