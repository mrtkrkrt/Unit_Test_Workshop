package com.folksdev.workshop.converter;

import com.folksdev.workshop.dto.TodoDto;
import com.folksdev.workshop.exception.UserNotFoundException;
import com.folksdev.workshop.model.Todo;
import com.folksdev.workshop.model.User;
import com.folksdev.workshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TodoConverterTest {

    @Mock
    private UserRepository userRepository;

    private TodoDto todoDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        todoDto = new TodoDto();
        todoDto.setComplete(false);
        todoDto.setUserId(1L);
        todoDto.setDescription("testDescription");

        user = new User();
        user.setId(1L);
        user.setUsername("testUsername");
    }

    @DisplayName("Convert todoDto to Todo with valid user")
    @Test
    void toData_validUser() {
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(Mockito.anyLong());
        Todo todo = TodoConverter.toData(todoDto, userRepository);

        Assertions.assertNotNull(todo);
        Assertions.assertEquals("testDescription", todo.getDescription());
        Assertions.assertFalse(todo.isComplete());
        Assertions.assertEquals(1L, todo.getUser().getId());
        Assertions.assertEquals("testUsername", todo.getUser().getUsername());
    }

    @DisplayName("Convert todoDto to Todo with invalid user. Expect throw error")
    @Test
    void toData_invalidUser() {
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(Mockito.anyLong());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(UserNotFoundException.class, () -> {
            TodoConverter.toData(todoDto, userRepository);
        });

        Assertions.assertTrue(userNotFoundException.getMessage().contains(String.valueOf(todoDto.getUserId())));
    }
}