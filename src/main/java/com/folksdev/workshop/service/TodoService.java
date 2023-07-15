package com.folksdev.workshop.service;

import com.folksdev.workshop.converter.TodoConverter;
import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.TodoNotFoundException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private TodoRepository todoRepository;
    private UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public Todo findTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        return todo;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo addTodo(TodoDto todoDto) {
        User user = userRepository.findById(todoDto.getUserId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Todo todo = TodoConverter.toData(todoDto);
        todoRepository.save(todo);
        return todo;
    }

    public Todo deleteTodo(Long todoID) {
        Todo todo = todoRepository.findById(todoID).orElse(null);
        if (todo == null) { // private taşı
            throw new TodoNotFoundException();
        }
        todoRepository.delete(todo);
        return todo;
    }

    public Todo switchTodoStatus(Long todoID) {
        Todo todo = todoRepository.findById(todoID).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        if (todo.isComplete()) todo.setComplete(false);
        else todo.setComplete(true);
        todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodo(TodoDto todoDto, Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        todo.setDescription(todoDto.getDescription());
        todo.setComplete(todoDto.isComplete());
        todo.setCreatedDate(todoDto.getCreatedDate());
        todoRepository.save(todo);
        return todo;
    }

    private List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findAll().stream().filter(e -> e.getUser().getId().equals(userId)).collect(Collectors.toList());
    }
}
