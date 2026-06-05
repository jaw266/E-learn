package com.elearnpro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AppUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    private int note;
    @Column(length = 800)
    private String commentaire;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Rating() {}

    public Rating(AppUser user, Course course, int note, String commentaire) {
        this.user = user;
        this.course = course;
        this.note = note;
        this.commentaire = commentaire;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
