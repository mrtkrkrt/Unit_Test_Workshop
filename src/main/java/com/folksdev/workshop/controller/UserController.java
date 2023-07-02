package com.folksdev.workshop.controller;

import com.folksdev.workshop.converter.UserConverter;
import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public void retrieveAllTodosByUserId(@PathVariable Long userId) {
        // user exist kontrolü
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        // username exist kontrolü
        log.info("USER ADD");
        User user = UserConverter.toData(userDto);
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/update/{userId}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        // user exist kontrolü
    }
}
