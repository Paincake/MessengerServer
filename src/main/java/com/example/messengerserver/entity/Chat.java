package com.example.messengerserver.entity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_chat")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthUser> users;
    @OneToMany(mappedBy = "chat")
    @Nullable
    private List<Message> messageList;
    private boolean isPrivate;


    private LocalDateTime lastMessageDateTime;

}
