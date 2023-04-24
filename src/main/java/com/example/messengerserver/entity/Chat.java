package com.example.messengerserver.entity;
import java.time.LocalDateTime;
import java.util.Set;

public class Chat {
    private Long id;
    private Set<AuthUser> users;
    private LocalDateTime lastMessageDateTime;
}
