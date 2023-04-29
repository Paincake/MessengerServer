package com.example.messengerserver.repository;

import com.example.messengerserver.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
