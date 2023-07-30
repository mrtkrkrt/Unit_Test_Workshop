package com.folksdev.workshop.service;

import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.UserAlreadyExistException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "user1", new ArrayList<>()));
        users.add(new User(2L, "user2", new ArrayList<>()));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(users, result);
    }

    @Test
    public void testGetTodosByUserId() {
        User user1 = new User(1L, "user1", new ArrayList<>());
        User user2 =  new User(2L, "user2", new ArrayList<>());

        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1L, user1, "Todo 1", false, new Date()));
        todos.add(new Todo(2L, user1, "Todo 2", false, new Date()));
        todos.add(new Todo(3L, user2, "Todo 3",  true, new Date()));

        when(todoRepository.findAll()).thenReturn(todos);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        List<Todo> result = userService.getTodosByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("newUser");

        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        User result = userService.saveUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(new User(1L, "existingUser", new ArrayList<>()));

        when(userRepository.findAll()).thenReturn(existingUsers);

        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("updatedUser");
        User existingUser = new User(1L, "existingUser", new ArrayList<>());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.updateUser(userDto, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(userDto.getUsername(), result.getUsername());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        UserDto userDto = new UserDto();
        userDto.setUsername("updatedUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto, 1L));
    }

    @Test
    public void testDeleteUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.deleteUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testGetUserById() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testFindUserById() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    public void testIsUserExists() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = ReflectionTestUtils.invokeMethod(userService, "isUserExists", 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testIsUserExists_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> ReflectionTestUtils.invokeMethod(userService, "isUserExists", 1L));
    }
}