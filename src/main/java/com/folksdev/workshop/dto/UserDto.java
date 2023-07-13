package com.folksdev.workshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @Size(min = 3, max = 30)
    @NotBlank
    private String username;
}
