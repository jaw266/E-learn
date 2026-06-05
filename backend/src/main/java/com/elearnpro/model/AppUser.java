package com.elearnpro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String niveau;
    private String domaineInteret;
    private String role = "USER";

    public AppUser() {}

    public AppUser(String name, String email, String password, String niveau, String domaineInteret, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.niveau = niveau;
        this.domaineInteret = domaineInteret;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getDomaineInteret() { return domaineInteret; }
    public void setDomaineInteret(String domaineInteret) { this.domaineInteret = domaineInteret; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
