package com.example.messengerserver.repository;

import com.example.messengerserver.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AuthUser, Long> {
    AuthUser findByUsername(String username);
}
