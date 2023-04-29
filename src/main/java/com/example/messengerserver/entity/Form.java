package com.example.messengerserver.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_form")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Getter
@Setter
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AuthUser author;

    @Nullable
    @ElementCollection
    private List<String> pictures;

    @OneToMany(mappedBy = "repliedForm")
    @Nullable
    private List<Reply> replyList;
    private boolean isPrivate;
    private String text;
    private LocalDateTime submitTime;

}
