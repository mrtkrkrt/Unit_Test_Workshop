package com.folksdev.workshop.repository;

import com.folksdev.workshop.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
