package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.InvalidTodoRequest;
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
@RequestMapping("/api/user")
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

    @GetMapping("/todos/{userId}")
    public ResponseEntity<List<Todo>> retrieveTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getTodosByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidTodoRequest();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.saveUser(userDto));
    }

    @PostMapping("/update/{userId}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        userService.updateUser(userDto, userId);
    }

    // delete user
    // get user by id
}
