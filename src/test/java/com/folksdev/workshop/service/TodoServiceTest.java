package com.folksdev.workshop.service;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.TodoNotFoundException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindTodoById() {
        Todo existingTodo = new Todo(1L, new User(1L, "user1", new ArrayList<>()),
                "Todo 1", false, new Date());

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingTodo));

        Todo result = todoService.findTodoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindTodoById_TodoNotFound() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TodoNotFoundException.class, () -> todoService.findTodoById(1L));
    }

    @Test
    public void testAddTodo() {
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("Todo 1");
        todoDto.setUserId(1L);
        User existingUser = new User(1L, "user1", new ArrayList<>());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        Todo result = todoService.addTodo(todoDto);

        assertNotNull(result);
        assertEquals(todoDto.getDescription(), result.getDescription());
        assertEquals(existingUser, result.getUser());

        verify(todoRepository, times(1)).save(result);
    }

    @Test
    public void testAddTodo_UserNotFound() {
        TodoDto todoDto = new TodoDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> todoService.addTodo(todoDto));
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        User user = new User(1L, "user1", new ArrayList<>());
        Todo existingTodo = new Todo(1L, user, "Task 1", false, new Date());

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingTodo));

        Todo result = todoService.deleteTodo(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(todoRepository, times(1)).delete(existingTodo);
    }

    @Test
    public void testDeleteTodo_TodoNotFound() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(1L));
        verify(todoRepository, never()).delete(any(Todo.class));
    }

    @Test
    public void testSwitchTodoStatus() {
        User user = new User(1L, "user1", new ArrayList<>());
        Todo existingTodo = new Todo(1L, user, "Task 1", false, new Date());

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingTodo));

        Todo result = todoService.switchTodoStatus(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.isComplete());

        verify(todoRepository, times(1)).save(result);
    }

    @Test
    public void testSwitchTodoStatus_TodoNotFound() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.switchTodoStatus(1L));

        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    public void testUpdateTodo() {
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("Task 2");
        todoDto.setComplete(false);
        User user = new User(1L, "user1", new ArrayList<>());
        Todo existingTodo = new Todo(1L, user, "Task 1", false, new Date());

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingTodo));

        Todo result = todoService.updateTodo(todoDto, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(todoDto.getDescription(), result.getDescription());
        assertEquals(todoDto.isComplete(), result.isComplete());

        verify(todoRepository, times(1)).save(result);
    }

    @Test
    public void testUpdateTodo_TodoNotFound() {
        TodoDto todoDto = new TodoDto();
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(todoDto, 1L));
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    public void testFindAll() {
        List<Todo> todos = new ArrayList<>();
        User user = new User(1L, "user1", new ArrayList<>());
        todos.add(new Todo(1L, user, "Task 1", false, new Date()));
        todos.add(new Todo(2L, user, "Task 2", true, new Date()));
        todos.add(new Todo(3L, user, "Task 3", false, new Date()));

        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.findAll();

        assertNotNull(result);
        assertEquals(todos.size(), result.size());
        assertEquals(todos, result);
    }

    @Test
    public void testGetTodosByUserId() {
        User user1 = new User(1L, "user1", new ArrayList<>());
        User user2 = new User(2L, "user2", new ArrayList<>());
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1L, user1, "Task 1", false, new Date()));
        todos.add(new Todo(2L, user1, "Task 2", false, new Date()));
        todos.add(new Todo(3L, user2, "Task 3", false, new Date()));

        when(todoRepository.findAll()).thenReturn(todos);

        List<Todo> result = todoService.getTodosByUserId(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(todo -> todo.getUser().getId().equals(1L)));
    }

    @Test
    public void testIsTodoExist() {
        User user = new User(1L, "user1", new ArrayList<>());
        Todo existingTodo = new Todo(1L, user, "Task 1", false, new Date());

        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(existingTodo));
        Todo result = ReflectionTestUtils.invokeMethod(todoService, "isTodoExist", 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Task 1", result.getDescription());
    }

    @Test
    public void testIsTodoExist_TodoNotFound() {
        when(todoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(TodoNotFoundException.class, () -> ReflectionTestUtils.invokeMethod(todoService, "isTodoExist", 1L));
    }

    @Test
    public void testIsUserExists() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));

        User result = ReflectionTestUtils.invokeMethod(todoService, "isUserExists", 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testIsUserExists_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> ReflectionTestUtils.invokeMethod(todoService, "isUserExists", 1L));
    }
}