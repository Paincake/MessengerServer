package com.example.messengerserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name="t_role")
public class Role implements GrantedAuthority {
    @jakarta.persistence.Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy="roles")
    private Set<AuthUser> authUsers;
    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthUser> getUsers() {
        return authUsers;
    }

    public void setUsers(Set<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }

    @Override
    public String getAuthority() {
        return getName();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
