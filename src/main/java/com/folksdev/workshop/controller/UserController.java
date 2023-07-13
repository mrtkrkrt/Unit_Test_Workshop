package com.folksdev.workshop.controller;

import com.folksdev.workshop.converter.UserConverter;
import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.UserAlreadyExistException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private UserRepository userRepository;
    private TodoRepository todoRepository;

    public UserController(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Todo>> retrieveAllTodosByUserId(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        List<Todo> todos = todoRepository.findAll().stream().filter(e -> e.getUser().getId().equals(userId)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.FOUND).body(todos);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        List<User> users = userRepository.findAll();
        if (users.stream().anyMatch(u -> u.getUsername().equals(userDto.getUsername()))) {
            throw new UserAlreadyExistException();
        }
        log.info("USER ADD");
        User user = UserConverter.toData(userDto);
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/update/{userId}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.save(UserConverter.toData(userDto));
    }
}
