package com.example.frequencies;

public class User {
    private int id;
    private String prenom;
    private String nom;
    private String matricule;
    private String sexe;
    private String telephone;
    private String email;
    private String adresse;
    private String mdp;

    public User(int id, String prenom, String nom, String matricule, String sexe, String telephone, String email, String adresse, String mdp) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.matricule = matricule;
        this.sexe = sexe;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
