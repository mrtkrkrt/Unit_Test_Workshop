package com.folksdev.workshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
public class TodoDto {
    private Long id;
    private Long userId;

    @Size(min = 5, max = 1000)
    @NotBlank
    private String description;


    private boolean isComplete;

    @CreationTimestamp
    private Date createdDate;
}
