package com.folksdev.workshop.repository;

import com.folksdev.workshop.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(
            value = "SELECT * FROM todos WHERE isComplete=0",
            nativeQuery = true
    )
    List<Todo> getAllIncompleteTodos();

    @Query(
            value = "SELECT * FROM todos WHERE isComplete=1",
            nativeQuery = true
    )
    List<Todo> getAllCompleteTodos();
}
