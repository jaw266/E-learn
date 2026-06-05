package com.elearnpro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AppUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;

    private int progression = 0;
    private boolean completed = false;
    private LocalDateTime dateDebut = LocalDateTime.now();

    public Enrollment() {}

    public Enrollment(AppUser user, Course course) {
        this.user = user;
        this.course = course;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public int getProgression() { return progression; }
    public void setProgression(int progression) { this.progression = progression; this.completed = progression >= 100; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
}
