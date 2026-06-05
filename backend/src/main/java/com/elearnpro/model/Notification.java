package com.elearnpro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AppUser user;

    private String message;
    private boolean lu = false;
    private LocalDateTime dateCreation = LocalDateTime.now();

    public Notification() {}

    public Notification(AppUser user, String message) {
        this.user = user;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isLu() { return lu; }
    public void setLu(boolean lu) { this.lu = lu; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
