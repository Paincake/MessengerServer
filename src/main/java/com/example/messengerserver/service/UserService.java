package com.example.messengerserver.service;

import com.example.messengerserver.entity.AuthUser;
import com.example.messengerserver.entity.Role;
import com.example.messengerserver.repository.RoleRepository;
import com.example.messengerserver.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = userRepository.findByUsername(username);
        if(authUser == null){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return authUser;
    }

    public AuthUser findUserById(Long userId){
        Optional<AuthUser> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new AuthUser());
    }

    public List<AuthUser> findAllUsers(){
        return userRepository.findAll();
    }

    public boolean saveUser(AuthUser authUser){
        AuthUser authUserFromDb = userRepository.findByUsername(authUser.getUsername());
        if(authUserFromDb != null){
            return false;
        }
        Role role = new Role(1L, "ROLE_USER");
        if(!roleRepository.existsById(role.getId())) roleRepository.save(role);
        authUser.setRoles(Collections.singleton(role));
        authUser.setPassword(bCryptPasswordEncoder.encode(authUser.getPassword()));
        userRepository.save(authUser);
        return true;
    }

    public List<AuthUser> usergtList(Long idMin) {
        return entityManager.createQuery("SELECT u FROM AuthUser u WHERE u.id > :paramId", AuthUser.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
