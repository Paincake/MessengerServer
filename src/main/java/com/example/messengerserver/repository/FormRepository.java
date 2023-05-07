package com.example.messengerserver.repository;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findFormsByAuthorNotAndIdNotIn(AuthUser user, List<Long> ids);
    List<Form> findFormsByIdNotIn(List<Long> ids);
    List<Form> findFormsByAuthorNotIn(List<AuthUser> user);

}
