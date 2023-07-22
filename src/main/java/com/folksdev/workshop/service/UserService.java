package com.folksdev.workshop.service;

import com.folksdev.workshop.converter.UserConverter;
import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.UserAlreadyExistException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private TodoRepository todoRepository;

    public UserService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Todo> getTodosByUserId(Long userId) {
        User user = isUserExists(userId);
        List<Todo> todos = todoRepository.findAll().stream().filter(e -> e.getUser().getId().equals(userId)).collect(Collectors.toList());
        return todos;
    }

    public User saveUser(UserDto userDto) {
        List<User> users = userRepository.findAll();
        if (users.stream().anyMatch(u -> u.getUsername().equals(userDto.getUsername()))) {
            throw new UserAlreadyExistException("User Already Exists!");
        }
        User user = UserConverter.toData(userDto);
        userRepository.save(user);
        return user;
    }

    public User updateUser(UserDto userDto, Long userId) {
        User user = isUserExists(userId);
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return user;
    }

    public User getUserById(Long userId) {
        User user = isUserExists(userId);
        return user;
    }

    public User deleteUser(Long userId) {
        User user = isUserExists(userId);
        userRepository.deleteById(userId);
        return user;
    }

    public User findUserById(Long userId) {
        User user = isUserExists(userId);
        return user;
    }

    private User isUserExists(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("There is no user with the given id => " + userId);
        }
        return user;
    }
}
