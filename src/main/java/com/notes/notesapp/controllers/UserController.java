package com.notes.notesapp.controllers;

import com.notes.notesapp.models.LoginUserModel;
import com.notes.notesapp.models.ReceiveUserModel;
import com.notes.notesapp.repositories.UserRepository;
import com.notes.notesapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/register")
    public void register(@RequestBody ReceiveUserModel user) {
        userService.registerUser(user);
    }

    @PostMapping("/users/login")
    public boolean login(@RequestBody LoginUserModel user, @RequestParam("remember-me") boolean rememberMe, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(user, request, response, rememberMe);
    }

    @GetMapping("/users/get-current-authenticated-user-email")
    public String getCurrentUser(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/users/get-user-id-by-email/{email}")
    public Long getUserId(@PathVariable String email) {
        return userRepository.getIdByEmail(email);
    }
}
