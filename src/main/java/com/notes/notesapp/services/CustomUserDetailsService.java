package com.notes.notesapp.services;

import com.notes.notesapp.repositories.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            BigDecimal id = userRepository.getIdByEmail(email);
            String hashedPassword = userRepository.getPassword(id);

            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(hashedPassword)
                    .roles("USER")
                    .build();
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}