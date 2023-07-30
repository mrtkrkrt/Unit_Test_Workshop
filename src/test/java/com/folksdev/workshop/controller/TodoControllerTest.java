package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.InvalidTodoRequest;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bindingResult = Mockito.mock(BindingResult.class);
    }

    @Test
    public void testRetrieveAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo(1L, null, "Todo 1", false, null));
        todos.add(new Todo(2L, null, "Todo 2", true, null));

        when(todoService.findAll()).thenReturn(todos);

        ResponseEntity<List<Todo>> responseEntity = todoController.retrieveAllTodos();

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(todos, responseEntity.getBody());
    }

    @Test
    public void testGetTodoByID() {
        Long todoID = 1L;
        Todo todo = new Todo(todoID, null, "Test Todo", false, null);

        when(todoService.findTodoById(todoID)).thenReturn(todo);

        ResponseEntity<Todo> responseEntity = todoController.getTodoByID(todoID);

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(todo, responseEntity.getBody());
    }

    @Test
    public void testAddTodo_ValidInput() {
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("New Todo");
        todoDto.setComplete(false);

        Todo savedTodo = new Todo(1L, null, todoDto.getDescription(), todoDto.isComplete(), null);
        when(todoService.addTodo(todoDto)).thenReturn(savedTodo);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<Todo> responseEntity = todoController.addTodo(todoDto, bindingResult);

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(savedTodo, responseEntity.getBody());
    }

    @Test
    public void testAddTodo_InvalidInput() {
        TodoDto todoDto = new TodoDto();
        when(bindingResult.hasErrors()).thenReturn(true);

        try {
            todoController.addTodo(todoDto, bindingResult);
        } catch (InvalidTodoRequest e) {
            // Expected behavior, do nothing
        }

        verify(todoService, never()).addTodo(any(TodoDto.class));
    }

    @Test
    public void testDeleteTodo() {
        Long todoID = 1L;
        Todo deletedTodo = new Todo(todoID, null, "Test Todo", false, null);

        when(todoService.deleteTodo(todoID)).thenReturn(deletedTodo);

        ResponseEntity<Todo> responseEntity = todoController.deleteTodo(todoID);

        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(deletedTodo, responseEntity.getBody());

        verify(todoService, times(1)).deleteTodo(todoID);
    }

    @Test
    public void testSwitchTodoStatus() {
        Long todoID = 1L;
        Todo updatedTodo = new Todo(todoID, null, "Test Todo", true, null);

        when(todoService.switchTodoStatus(todoID)).thenReturn(updatedTodo);

        ResponseEntity<Todo> responseEntity = todoController.switchTodoStatus(todoID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());

        verify(todoService, times(1)).switchTodoStatus(todoID);
    }

    @Test
    public void testUpdateTodo() {
        Long todoID = 1L;
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription("Updated Todo");
        todoDto.setComplete(true);

        Todo updatedTodo = new Todo(todoID, null, todoDto.getDescription(), todoDto.isComplete(), null);
        when(todoService.updateTodo(todoDto, todoID)).thenReturn(updatedTodo);

        ResponseEntity<Todo> responseEntity = todoController.updateTodo(todoDto, todoID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTodo, responseEntity.getBody());

        verify(todoService, times(1)).updateTodo(todoDto, todoID);
    }
}