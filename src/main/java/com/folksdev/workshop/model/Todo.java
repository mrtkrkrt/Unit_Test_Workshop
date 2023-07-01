package com.folksdev.workshop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "todos", schema = "workshop")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isComplete;

    @CreationTimestamp
    private Date createdDate;
}
