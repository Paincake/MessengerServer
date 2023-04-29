package com.example.messengerserver.repository;

import com.example.messengerserver.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {

}
