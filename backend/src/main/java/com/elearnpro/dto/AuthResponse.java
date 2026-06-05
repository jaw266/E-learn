package com.elearnpro.dto;

public record AuthResponse(
        String token,
        String name,
        String email,
        String niveau,
        String domaineInteret,
        String role
) {}
