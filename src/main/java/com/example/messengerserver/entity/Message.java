package com.example.messengerserver.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_message")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @Nullable
    @ElementCollection
    private List<String> pictures;
    private LocalDateTime sendingTime;
    @ManyToOne
    private AuthUser sender;
    @ManyToOne
    private AuthUser receiver;
    boolean isForwarded;
    @ManyToOne
    private Chat chat;

}
