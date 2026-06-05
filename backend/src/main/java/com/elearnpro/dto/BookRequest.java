package com.elearnpro.dto;

public record BookRequest(
        String titre,
        String auteur,
        String domaine,
        String niveau,
        String description,
        String lien,
        String couverture,
        boolean gratuit
) {}
