package com.folksdev.workshop.repository;

import com.folksdev.workshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
