package com.folksdev.workshop.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TodoDtoTest {

    private TodoDto todoDto;
    private TodoDto equalTodoDto;
    private TodoDto notEqualTodoDto;
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

        todoDto = new TodoDto();
        todoDto.setId(1L);
        todoDto.setComplete(false);
        todoDto.setUserId(2L);
        todoDto.setDescription("testTodoDescription");
        todoDto.setCreatedDate(calendar.getTime());

        equalTodoDto = new TodoDto();
        equalTodoDto.setId(1L);
        equalTodoDto.setComplete(false);
        equalTodoDto.setUserId(2L);
        equalTodoDto.setDescription("testTodoDescription");
        equalTodoDto.setCreatedDate(calendar.getTime());

        notEqualTodoDto = new TodoDto();
        notEqualTodoDto.setId(2L);
        notEqualTodoDto.setComplete(true);
        notEqualTodoDto.setUserId(3L);
        notEqualTodoDto.setDescription("testTodoDescription");
        notEqualTodoDto.setCreatedDate(calendar.getTime());
    }

    @Test
    void testTodoDto() {
        Assertions.assertEquals(1L, todoDto.getId());
        Assertions.assertEquals(2L, todoDto.getUserId());
        Assertions.assertEquals("testTodoDescription", todoDto.getDescription());
        Assertions.assertEquals(false, todoDto.isComplete());
        Assertions.assertEquals(calendar.getTime(), todoDto.getCreatedDate());
    }
    @Test
    void testEquals() {
        Assertions.assertTrue(todoDto.equals(equalTodoDto));
        Assertions.assertFalse(todoDto.equals(notEqualTodoDto));
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(equalTodoDto.hashCode(), todoDto.hashCode());
        Assertions.assertNotEquals(notEqualTodoDto.hashCode(), todoDto.hashCode());
    }
}