package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoConverter {

    private static UserRepository userRepository;

    public TodoConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static Todo toData(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setComplete(todoDto.isComplete());
        todo.setDescription(todoDto.getDescription());
        todo.setUser(userRepository.findById(todoDto.getUserId()).orElse(null));
        return todo;
    }
}
