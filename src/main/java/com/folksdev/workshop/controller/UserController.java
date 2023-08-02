package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.InvalidUserRequest;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
    }

    @GetMapping("/todos/{userId}")
    public ResponseEntity<List<Todo>> retrieveTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getTodosByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidUserRequest("Invalid user request -> " + userDto.getId());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveUser(userDto));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto, userId));
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
    }
}
