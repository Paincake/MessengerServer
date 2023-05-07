package com.example.messengerserver.entity;

import jakarta.annotation.Nullable;
import org.hibernate.annotations.Cascade;
import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_reply")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class Reply {
    private LocalDateTime replyDateTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private AuthUser repliedUser;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Form repliedForm;
    @Nullable
    private String text;
    @Nullable
    @ElementCollection
    private List<String> pictures;

}
