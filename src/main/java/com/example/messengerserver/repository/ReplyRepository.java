package com.example.messengerserver.repository;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByRepliedUser(AuthUser user);
}
