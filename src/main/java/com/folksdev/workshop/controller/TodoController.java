package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.InvalidTodoRequest;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.service.TodoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@Slf4j
public class TodoController {

    // TODO: 7/13/2023 log ekle
    // TODO: 1.08.2023 save metodlarını ekstra metod içine al (try catch)
    // TODO: 2.08.2023 parametrized test ve csv source
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> retrieveAllTodos() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(todoService.findAll());
    }

    @GetMapping("/{todoID}")
    public ResponseEntity<Todo> getTodoByID(@PathVariable Long todoID) {
        Todo todo = todoService.findTodoById(todoID);
        return ResponseEntity.status(HttpStatus.FOUND).body(todo);
    }

    @PostMapping("/save")
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody TodoDto todoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidTodoRequest("Invalid todo request -> " + todoDto.getId());
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(todoService.addTodo(todoDto));
    }

    @DeleteMapping("/delete/{todoID}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long todoID) {
        return ResponseEntity.status(HttpStatus.FOUND).body(todoService.deleteTodo(todoID));
    }

    @PatchMapping("/done/{todoID}")
    public ResponseEntity<Todo> switchTodoStatus(@PathVariable Long todoID) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.switchTodoStatus(todoID));
    }

    @PostMapping("/update/{todoID}")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDto todoDto, @PathVariable Long todoID) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodo(todoDto, todoID));
    }
}
