package com.notes.notesapp.services;

import com.notes.notesapp.models.LoginUserModel;
import com.notes.notesapp.models.ReceiveUserModel;
import com.notes.notesapp.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final RememberMeServices rememberMeServices;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            RememberMeServices rememberMeServices) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.rememberMeServices = rememberMeServices;
    }

    public void registerUser(ReceiveUserModel user) {
        String hashedPassword = passwordEncoder.encode(user.password);
        user.password = hashedPassword;

        userRepository.registerUser(user);
    }

    public boolean login(LoginUserModel user, HttpServletRequest request, HttpServletResponse response, boolean rememberMe) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            user.email,
                            user.password
                    )
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(
                    context, request, response
            );

            if (rememberMe) {
                rememberMeServices.loginSuccess(request, response, authentication);
            }


            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}
