package com.example.messengerserver.repository;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findChatByUsersContaining(AuthUser user);
    List<Chat> findChatsByUsersContaining(AuthUser user);
    Chat findChatByUsersIn(Collection<Set<AuthUser>> users);
}
