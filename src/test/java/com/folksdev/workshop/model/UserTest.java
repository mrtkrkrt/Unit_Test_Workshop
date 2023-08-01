package com.folksdev.workshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    private User user;
    private List<Todo> todos;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        todos = new ArrayList<>();
        todos.add(new Todo(1L, user, "Todo 1", false, null));
        todos.add(new Todo(2L, user, "Todo 2", true, null));
        user.setTodos(todos);
    }

    @Test
    void testGetters() {
        Long actualId = user.getId();
        String actualUsername = user.getUsername();
        List<Todo> actualTodos = user.getTodos();
        assertEquals(1L, actualId);
        assertEquals("testUser", actualUsername);
        assertEquals(2, actualTodos.size());
        assertEquals("Todo 1", actualTodos.get(0).getDescription());
        assertEquals("Todo 2", actualTodos.get(1).getDescription());
    }

    @Test
    void testSetters() {
        Long newId = 2L;
        String newUsername = "newUser";
        List<Todo> newTodos = new ArrayList<>();
        newTodos.add(new Todo(3L, user, "New Todo", false, null));

        user.setId(newId);
        user.setUsername(newUsername);
        user.setTodos(newTodos);

        assertEquals(newId, user.getId());
        assertEquals(newUsername, user.getUsername());
        assertEquals(1, user.getTodos().size());
        assertEquals("New Todo", user.getTodos().get(0).getDescription());
    }

    @Test
    void testEmptyConstructor() {
        User emptyUser = new User();
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
        assertEquals(0, emptyUser.getTodos().size());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 3L;
        String username = "testUser2";

        User user = new User(id, username, todos);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(2, user.getTodos().size());
        assertEquals("Todo 1", user.getTodos().get(0).getDescription());
        assertEquals("Todo 2", user.getTodos().get(1).getDescription());
    }
}