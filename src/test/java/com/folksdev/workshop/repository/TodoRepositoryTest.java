package com.folksdev.workshop.repository;

import com.folksdev.workshop.model.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @MockBean
    private TodoRepository mockTodoRepository;

    @Test
    public void testGetAllIncompleteTodos() {
        List<Todo> incompleteTodos = new ArrayList<>();
        incompleteTodos.add(new Todo(1L, null, "Incomplete Todo 1", false, null));
        incompleteTodos.add(new Todo(2L, null, "Incomplete Todo 2", false, null));

        when(mockTodoRepository.getAllIncompleteTodos()).thenReturn(incompleteTodos);

        List<Todo> result = mockTodoRepository.getAllIncompleteTodos();
        assertEquals(2, result.size());
        assertEquals("Incomplete Todo 1", result.get(0).getDescription());
        assertEquals("Incomplete Todo 2", result.get(1).getDescription());
    }

    @Test
    public void testGetAllCompleteTodos() {
        List<Todo> completeTodos = new ArrayList<>();
        completeTodos.add(new Todo(3L, null, "Complete Todo 1", true, null));
        completeTodos.add(new Todo(4L, null, "Complete Todo 2", true, null));

        when(mockTodoRepository.getAllCompleteTodos()).thenReturn(completeTodos);

        List<Todo> result = mockTodoRepository.getAllCompleteTodos();

        assertEquals(2, result.size());
        assertEquals("Complete Todo 1", result.get(0).getDescription());
        assertEquals("Complete Todo 2", result.get(1).getDescription());
    }
}