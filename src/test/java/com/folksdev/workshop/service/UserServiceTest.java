package com.folksdev.workshop.service;

import com.folksdev.workshop.converter.UserConverter;
import com.folksdev.workshop.dto.UserDto;
import com.folksdev.workshop.exception.UserAlreadyExistException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
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

    private final LogCaptor logCaptor = LogCaptor.forClass(UserService.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "user1", new ArrayList<>()));
        users.add(new User(2L, "user2", new ArrayList<>()));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(users, result);
    }

    @Test
    void testGetTodosByUserId() {
        User user1 = new User(1L, "user1", new ArrayList<>());
        User user2 = new User(2L, "user2", new ArrayList<>());

        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1L, user1, "Todo 1", false, new Date()));
        todos.add(new Todo(2L, user1, "Todo 2", false, new Date()));
        todos.add(new Todo(3L, user2, "Todo 3", true, new Date()));

        when(todoRepository.findAll()).thenReturn(todos);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        List<Todo> result = userService.getTodosByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testSaveUser() {
        MockedStatic<UserConverter> userConverterMockedStatic = Mockito.mockStatic(UserConverter.class);

        UserDto userDto = new UserDto();
        userDto.setUsername("newUser");
        User user = new User(1L, "newUser", new ArrayList<>());

        userConverterMockedStatic.when(() -> UserConverter.toData(Mockito.any())).thenReturn(user);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        User result = userService.saveUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getUsername(), result.getUsername());

        userConverterMockedStatic.close();
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(new User(1L, "existingUser", new ArrayList<>()));

        when(userRepository.findAll()).thenReturn(existingUsers);

        assertThrows(UserAlreadyExistException.class, () -> userService.saveUser(userDto));
        assertTrue(logCaptor.getErrorLogs().get(0).contains("existingUser"));
    }

    @ParameterizedTest
    @CsvSource({
            "'updateUsername1', 1",
            "'updateUsername2', 2"
    })
    void testUpdateUser(String username, Long userId) {
        User existingUser = new User(userId, "existingUser", new ArrayList<>());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.updateUser(username, userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(username, result.getUsername());
        assertTrue(logCaptor.getInfoLogs().get(0).contains(username));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        String username = "updatedUsername";

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(username, 1L));
    }

    @Test
    void testDeleteUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = userService.deleteUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
        assertTrue(logCaptor.getInfoLogs().get(0).contains("existingUser"));

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetUserById() {
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
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testFindUserById() {
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
    void testFindUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    void testIsUserExists() {
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
    void testIsUserExists_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> ReflectionTestUtils.invokeMethod(userService, "isUserExists", 1L));
    }
}