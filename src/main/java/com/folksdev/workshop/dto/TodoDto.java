package com.folksdev.workshop.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
public class TodoDto {
    private Long id;
    private Long userId;
    private String description;
    private boolean isComplete;

    @CreationTimestamp
    private Date createdDate;
}
