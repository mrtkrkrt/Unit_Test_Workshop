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
        Todo todo = isTodoExist(id);
        return todo;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo addTodo(TodoDto todoDto) {
        User user = isUserExists(todoDto.getUserId());
        Todo todo = TodoConverter.toData(todoDto);
        todoRepository.save(todo);
        return todo;
    }

    public Todo deleteTodo(Long todoID) {
        Todo todo = isTodoExist(todoID);
        todoRepository.delete(todo);
        return todo;
    }

    public Todo switchTodoStatus(Long todoID) {
        Todo todo = isTodoExist(todoID);
        if (todo.isComplete()) todo.setComplete(false);
        else todo.setComplete(true);
        todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodo(TodoDto todoDto, Long todoId) {
        Todo todo = isTodoExist(todoId);
        todo.setDescription(todoDto.getDescription());
        todo.setComplete(todoDto.isComplete());
        todo.setCreatedDate(todoDto.getCreatedDate());
        todoRepository.save(todo);
        return todo;
    }

    private List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findAll().stream().filter(e -> e.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    private Todo isTodoExist(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null) {
            throw new TodoNotFoundException();
        }
        return todo;
    }

    private User isUserExists(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
