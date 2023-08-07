package com.folksdev.workshop.service;

import com.folksdev.workshop.converter.TodoConverter;
import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.TodoNotFoundException;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.TodoRepository;
import com.folksdev.workshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

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
        Todo todo = TodoConverter.toData(todoDto, userRepository);
        log.info("Todo added successfully: {}", todoDto.getDescription());
        todoRepository.save(todo);
        return todo;
    }

    public Todo deleteTodo(Long todoID) {
        Todo todo = isTodoExist(todoID);
        todoRepository.delete(todo);
        log.info("Todo deleted successfully: {}", todoID);
        return todo;
    }

    public Todo switchTodoStatus(Long todoID) {
        Todo todo = isTodoExist(todoID);
        if (todo.isComplete()) {
            todo.setComplete(false);
        }
        else {
            todo.setComplete(true);
        }
        log.info("Todo status switched successfully: {}", todoID);
        todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodo(TodoDto todoDto, Long todoId) {
        Todo todo = isTodoExist(todoId);
        todo.setDescription(todoDto.getDescription());
        todo.setComplete(todoDto.isComplete());
        todo.setCreatedDate(todoDto.getCreatedDate());
        todoRepository.save(todo);
        log.info("Todo updated successfully: {}", todoId);
        return todo;
    }

    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findAll().stream().filter(e -> e.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    private Todo isTodoExist(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (Objects.isNull(todo)) {
            throw new TodoNotFoundException(String.format("There is no todo with the given id => %s", todoId));
        }
        return todo;
    }

    private User isUserExists(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(String.format("There is no user with the given id => %s", userId));
        }
        return user;
    }
}
