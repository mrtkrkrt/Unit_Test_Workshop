package com.folksdev.workshop.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TodoDto {
    private Long id;
    private Long userId;
    private String description;
    private boolean isComplete;
    private Date createdDate;
}
