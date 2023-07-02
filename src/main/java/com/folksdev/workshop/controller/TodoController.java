package com.folksdev.workshop.controller;

import com.folksdev.workshop.converter.TodoConverter;
import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoController(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Todo>> retrieveAllTodos() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(todoRepository.findAll());
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoByID(@PathVariable Long todoID) {
        // Todo boş mu kontrolü ekle
        return ResponseEntity.status(HttpStatus.FOUND).body(todoRepository.findById(todoID).orElse(null));
    }

    @PostMapping("/save")
    public ResponseEntity<Todo> addTodo(@RequestBody TodoDto todoDto) {
        Optional<User> user = userRepository.findById(todoDto.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(TodoConverter.toData(todoDto));
    }

    @PostMapping("/delete/{todoID}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long todoID) {
        // Todo boş mu kontrolü ekle
        Optional<Todo> deletedTodo = todoRepository.findById(todoID);
        todoRepository.deleteById(todoID);
        return ResponseEntity.status(HttpStatus.FOUND).body(deletedTodo.orElse(null));
    }
}
