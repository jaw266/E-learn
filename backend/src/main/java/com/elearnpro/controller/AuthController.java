package com.elearnpro.controller;

import com.elearnpro.dto.AuthRequest;
import com.elearnpro.dto.AuthResponse;
import com.elearnpro.dto.RegisterRequest;
import com.elearnpro.model.AppUser;
import com.elearnpro.repository.UserRepository;
import com.elearnpro.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String pwdError = validatePassword(request.password(), request.name(), request.email());
        if (pwdError != null) {
            return ResponseEntity.badRequest().body(Map.of("message", pwdError));
        }
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Un compte avec cet email existe déjà."));
        }
        AppUser user = new AppUser(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.niveau() == null ? "Débutant" : request.niveau(),
                request.domaineInteret() == null ? "Data & IA" : request.domaineInteret(),
                "USER"
        );
        userRepository.save(user);
        String token = jwtService.generateToken(User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole()).build());
        return ResponseEntity.ok(toResponse(token, user));
    }

    private String validatePassword(String password, String name, String email) {
        if (password.length() < 8)
            return "Le mot de passe doit contenir au moins 8 caractères.";
        if (!password.matches(".*[A-Z].*"))
            return "Le mot de passe doit contenir au moins une lettre majuscule.";
        if (!password.matches(".*[a-z].*"))
            return "Le mot de passe doit contenir au moins une lettre minuscule.";
        if (!password.matches(".*[0-9].*"))
            return "Le mot de passe doit contenir au moins un chiffre.";
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"))
            return "Le mot de passe doit contenir au moins un caractère spécial (!@#$%...).";
        String emailPrefix = email.contains("@") ? email.split("@")[0].toLowerCase() : email.toLowerCase();
        if (password.toLowerCase().contains(name.toLowerCase()))
            return "Le mot de passe ne doit pas contenir votre nom.";
        if (password.toLowerCase().contains(emailPrefix))
            return "Le mot de passe ne doit pas contenir votre adresse email.";
        return null;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        AppUser user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtService.generateToken(User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole()).build());
        return toResponse(token, user);
    }

    private AuthResponse toResponse(String token, AppUser user) {
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getNiveau(), user.getDomaineInteret(), user.getRole());
    }
}
