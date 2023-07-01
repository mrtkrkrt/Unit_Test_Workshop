package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public static User toData(UserDto userDto) {
        // TODO username validasyonu
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }
}
