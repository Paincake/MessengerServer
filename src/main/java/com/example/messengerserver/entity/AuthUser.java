package com.example.messengerserver.entity;

import com.example.messengerserver.cache.MessageCache;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;


import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_userauth")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@Getter
@Setter
public class AuthUser implements UserDetails {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Nullable
    @Column(columnDefinition="TEXT")
    private String avatarImage;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Nullable
    private List<Chat> chats;

    @OneToMany(mappedBy = "sender")
    @Nullable
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    @Nullable
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @Nullable
    private List<Form> forms;

    @OneToMany(mappedBy = "repliedUser", fetch = FetchType.EAGER)
    @Nullable
    private List<Reply> replies;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

}
