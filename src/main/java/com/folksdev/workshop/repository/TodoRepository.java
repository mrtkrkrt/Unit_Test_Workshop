package com.folksdev.workshop.repository;

import com.folksdev.workshop.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT * FROM todos WHERE isComplete=0")
    List<Todo> getAllIncompleteTodos();

    @Query("SELECT * FROM todos WHERE isComplete=1")
    List<Todo> getAllCompleteTodos();
}
