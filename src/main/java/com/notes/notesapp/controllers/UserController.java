package com.notes.notesapp.controllers;

import com.notes.notesapp.models.LoginUserModel;
import com.notes.notesapp.models.ReceiveUserModel;
import com.notes.notesapp.repositories.UserRepository;
import com.notes.notesapp.services.CustomUserDetailsService;
import com.notes.notesapp.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(
                          UserRepository userRepository,
                          UserService userService,
                          CustomUserDetailsService customUserDetailsService
                          )
    {
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public void register(@RequestBody ReceiveUserModel user) {
        userService.registerUser(user);
    }

    @PostMapping("/users/login/")
    public boolean login(@RequestBody LoginUserModel user) {
        return userService.login(user);
    }
}
