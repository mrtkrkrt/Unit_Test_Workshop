package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.model.Todo;
import org.springframework.stereotype.Service;

@Service
public class TodoConverter {

    public static Todo toData(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setComplete(todoDto.isComplete());
        todo.setDescription(todoDto.getDescription());
        todo.setUser(todoDto.getUser());
        return todo;
    }
}
