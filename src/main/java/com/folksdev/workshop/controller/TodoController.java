package com.folksdev.workshop.controller;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{todoId}")
    public void getTodoByID(@PathVariable Long todoID) {

    }

    @PostMapping("/save")
    public void addTodo(@RequestBody TodoDto todoDto) {

    }

    @PostMapping("/delete/{todoID}")
    public void deleteTodo(@PathVariable Long todoID) {

    }
}
