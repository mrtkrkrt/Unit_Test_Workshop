package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.InvalidTodoRequest;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class) -> case olarak anlat
class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllTodos() {
        // given -> build operate check pattern clean code kitabı
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1L, null, "Todo 1", false, null));
        todos.add(new Todo(2L, null, "Todo 2", true, null));

        when(todoService.findAll()).thenReturn(todos);

        // when
        ResponseEntity<List<Todo>> responseEntity = todoController.retrieveAllTodos();

        // then
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(todos, responseEntity.getBody());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void testGetTodoByID(Long todoId) {
        // given
        Todo todo = new Todo(todoId, null, "Test Todo", false, null);

        when(todoService.findTodoById(anyLong())).thenReturn(todo);

        // when
        ResponseEntity<Todo> responseEntity = todoController.getTodoByID(todoId);

        // then
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());
    }

    @Test
    void testAddTodo_ValidInput() {
        // given
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("New Todo");
        todoDto.setComplete(false);

        Todo savedTodo = new Todo(1L, null, todoDto.getDescription(), todoDto.isComplete(), null);
        when(todoService.addTodo(todoDto)).thenReturn(savedTodo);
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        ResponseEntity<Todo> responseEntity = todoController.addTodo(todoDto, bindingResult);

        // then
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(savedTodo, responseEntity.getBody());
    }

    @Test
    void testAddTodo_givenInvalidInput_thenThrowError() {
        // given
        TodoDto todoDto = new TodoDto();
        todoDto.setId(1L);
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        InvalidTodoRequest invalidTodoRequest = assertThrows(InvalidTodoRequest.class, () -> todoController.addTodo(todoDto, bindingResult));

        // then
        System.out.println(invalidTodoRequest.getMessage());
        assertTrue(invalidTodoRequest.getMessage().contains(String.valueOf(todoDto.getId())));
        verify(todoService, never()).addTodo(any(TodoDto.class));
    }

    @Test
    void testDeleteTodo() {
        // given
        Long todoID = 1L;
        Todo deletedTodo = new Todo(todoID, null, "Test Todo", false, null);

        when(todoService.deleteTodo(todoID)).thenReturn(deletedTodo);

        // when
        ResponseEntity<Todo> responseEntity = todoController.deleteTodo(todoID);

        // then
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(deletedTodo, responseEntity.getBody());

        verify(todoService, times(1)).deleteTodo(todoID);
    }

    @Test
    void testSwitchTodoStatus(boolean status) {
        // given
        Todo updatedTodo = new Todo(1L, null, "Test Todo", status, null);

        when(todoService.switchTodoStatus(anyLong())).thenReturn(updatedTodo);

        // when
        ResponseEntity<Todo> responseEntity = todoController.switchTodoStatus(1L);

        // then

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());

        verify(todoService, times(1)).switchTodoStatus(anyLong());
    }

    @Test
    void testUpdateTodo() {
        // given
        Long todoID = 1L;
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("Updated Todo");
        todoDto.setComplete(true);

        Todo updatedTodo = new Todo(todoID, null, todoDto.getDescription(), todoDto.isComplete(), null);
        when(todoService.updateTodo(todoDto, todoID)).thenReturn(updatedTodo);

        // when
        ResponseEntity<Todo> responseEntity = todoController.updateTodo(todoDto, todoID);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());

        verify(todoService, times(1)).updateTodo(todoDto, todoID);
    }
}