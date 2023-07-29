package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TodoConverter {

    public static Todo toData(TodoDto todoDto, UserRepository userRepository) {
        Todo todo = new Todo();
        todo.setComplete(todoDto.isComplete());
        todo.setDescription(todoDto.getDescription());

        User user = userRepository.findById(todoDto.getUserId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("The user for the given Id could not be found -> " + todoDto.getUserId());
        }

        todo.setUser(user);
        return todo;
    }
}
