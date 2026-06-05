package com.elearnpro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String name,
        @Email String email,
        @NotBlank String password,
        String niveau,
        String domaineInteret
) {}
