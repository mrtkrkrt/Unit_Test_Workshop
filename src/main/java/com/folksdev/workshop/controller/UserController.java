package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/{userId}")
    public void retrieveAllTodosByUserId(@PathVariable Long userId) {

    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserDto userDto) {

    }

    @PostMapping("/update/{userId}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {

    }
}
