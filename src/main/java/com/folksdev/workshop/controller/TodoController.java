package com.folksdev.workshop.controller;

import com.folksdev.workshop.converter.TodoConverter;
import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.TodoNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    // TODO: 7/13/2023 log ekle  
    // TODO: 7/13/2023 controller ın içini servise taşı
    // TODO: 7/13/2023 validation testleri 
    // TODO: 7/13/2023 repository testleri custom query 1 tane yeterli örnek için
    // getTodoById_givenExistingId_returnTodo
    // getTodoById_givenNoneExistingId_throwError

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
        Todo todo = todoRepository.findById(todoID).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(todoRepository.findById(todoID).orElse(null));
    }

    @PostMapping("/save")
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody TodoDto todoDto, BindingResult bindingResult) {
        // binding result kullanarak vcalidation yap
        Optional<User> user = userRepository.findById(todoDto.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        todoRepository.save(TodoConverter.toData(todoDto));
        return ResponseEntity.status(HttpStatus.FOUND).body(TodoConverter.toData(todoDto));
    }

    @PostMapping("/delete/{todoID}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Long todoID) {
        Todo todo = todoRepository.findById(todoID).orElse(null);
        if (todo == null) { // private taşı
            throw new TodoNotFoundException();
        }
        Optional<Todo> deletedTodo = todoRepository.findById(todoID);
        todoRepository.deleteById(todoID);
        return ResponseEntity.status(HttpStatus.FOUND).body(deletedTodo.orElse(null));
    }

    @PatchMapping("/done/{id}")
    public ResponseEntity<Todo> doneTodo(@PathVariable Long todoID) {
        Todo todo = todoRepository.findById(todoID).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        if (todo.isComplete()) todo.setComplete(false);
        else todo.setComplete(true);
        return ResponseEntity.status(HttpStatus.OK).body(todo);
    }

    @PostMapping("/update/{todoID}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id) {
        return null;
    }
}
