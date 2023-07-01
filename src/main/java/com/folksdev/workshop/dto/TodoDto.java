package com.folksdev.workshop.dto;

import com.folksdev.workshop.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class TodoDto {
    private Long id;
    private User user;
    private String description;
    private boolean isComplete;
    private Date createdDate;
}
