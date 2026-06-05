package com.elearnpro.model;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;

    @Column(length = 1200)
    private String description;

    private String categorie;
    private String niveau;
    private double noteMoyenne;
    private int dureeHeures;
    private boolean gratuit = true;
    private String imageUrl;

    public Course() {}

    public Course(String titre, String description, String categorie, String niveau, double noteMoyenne, int dureeHeures, boolean gratuit, String imageUrl) {
        this.titre = titre;
        this.description = description;
        this.categorie = categorie;
        this.niveau = niveau;
        this.noteMoyenne = noteMoyenne;
        this.dureeHeures = dureeHeures;
        this.gratuit = gratuit;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public double getNoteMoyenne() { return noteMoyenne; }
    public void setNoteMoyenne(double noteMoyenne) { this.noteMoyenne = noteMoyenne; }
    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(int dureeHeures) { this.dureeHeures = dureeHeures; }
    public boolean isGratuit() { return gratuit; }
    public void setGratuit(boolean gratuit) { this.gratuit = gratuit; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
