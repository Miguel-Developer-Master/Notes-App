package com.notes.notesapp.repositories;

import com.notes.notesapp.models.LoginUserModel;
import com.notes.notesapp.models.ReceiveUserModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerUser(ReceiveUserModel user) {
        String SQL = "INSERT INTO users (email,display_name,password) VALUES (?,?,?)";
        jdbcTemplate.update(SQL, user.email, user.display_name, user.password);
    }

    public String getPassword(Long id) {
        String SQL = "SELECT password FROM users WHERE id = ?";
        String password = jdbcTemplate.queryForObject(SQL, String.class, id);

        return password;
    }

    public Long getIdByEmail(String email) {
        String SQL = "SELECT id FROM users WHERE email = ?";
        Long id = jdbcTemplate.queryForObject(SQL, Long.class, email);

        return id;
    }
}
