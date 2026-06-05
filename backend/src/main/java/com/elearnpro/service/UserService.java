package com.elearnpro.service;

import com.elearnpro.model.AppUser;
import com.elearnpro.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser currentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
    }
}
