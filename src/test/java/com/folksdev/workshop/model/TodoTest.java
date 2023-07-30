package com.folksdev.workshop.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    private Todo todoWitNoArgConstructor;
    private Todo todoWitAllArgConstructor;
    private User user;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.AUGUST);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        user = new User();
        user.setId(1L);

        todoWitNoArgConstructor = new Todo();
        todoWitNoArgConstructor.setComplete(false);
        todoWitNoArgConstructor.setDescription("testDescriptionNoArg");
        todoWitNoArgConstructor.setUser(user);
        todoWitNoArgConstructor.setCreatedDate(calendar.getTime());
        todoWitNoArgConstructor.setId(2L);

        todoWitAllArgConstructor = new Todo(3L, user, "to", false, calendar.getTime());
    }

   @Test
   void testTodo() {
       Assertions.assertEquals(2L, todoWitNoArgConstructor.getId());
       Assertions.assertEquals("testDescriptionNoArg", todoWitNoArgConstructor.getDescription());
       Assertions.assertEquals(user, todoWitNoArgConstructor.getUser());
       Assertions.assertEquals(calendar.getTime(), todoWitNoArgConstructor.getCreatedDate());
       Assertions.assertFalse(todoWitNoArgConstructor.isComplete());

       Assertions.assertEquals(3L, todoWitAllArgConstructor.getId());
       Assertions.assertEquals("to", todoWitAllArgConstructor.getDescription());
       Assertions.assertEquals(user, todoWitAllArgConstructor.getUser());
       Assertions.assertEquals(calendar.getTime(), todoWitAllArgConstructor.getCreatedDate());
       Assertions.assertFalse(todoWitAllArgConstructor.isComplete());
   }


}