package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

    public static User toData(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        return user;
    }
}
