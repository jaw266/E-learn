package com.elearnpro.model;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String auteur;
    private String domaine;
    private String niveau;

    @Column(length = 1200)
    private String description;

    private String lien;
    private String couverture;
    private boolean gratuit = true;

    public Book() {}

    public Book(String titre, String auteur, String domaine, String niveau, String description, String lien, String couverture, boolean gratuit) {
        this.titre = titre;
        this.auteur = auteur;
        this.domaine = domaine;
        this.niveau = niveau;
        this.description = description;
        this.lien = lien;
        this.couverture = couverture;
        this.gratuit = gratuit;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLien() { return lien; }
    public void setLien(String lien) { this.lien = lien; }
    public String getCouverture() { return couverture; }
    public void setCouverture(String couverture) { this.couverture = couverture; }
    public boolean isGratuit() { return gratuit; }
    public void setGratuit(boolean gratuit) { this.gratuit = gratuit; }
}
