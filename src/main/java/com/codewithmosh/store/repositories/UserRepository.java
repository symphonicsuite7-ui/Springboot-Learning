package com.codewithmosh.store.repositories;

import com.codewithmosh.store.repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
