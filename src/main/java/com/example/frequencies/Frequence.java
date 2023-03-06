package com.example.frequencies;

import java.util.Date;

public class Frequence {
    private int id;
    private double frequence;
    private Date dateAjout;
    private String etat;
    private int idAbonne;
    private int idUser;

    public Frequence(int id, double frequence, Date dateAjout, String etat, int idAbonne, int idUser) {
        this.id = id;
        this.frequence = frequence;
        this.dateAjout = dateAjout;
        this.etat = etat;
        this.idAbonne = idAbonne;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFrequence() {
        return frequence;
    }

    public void setFrequence(double frequence) {
        this.frequence = frequence;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(int idAbonne) {
        this.idAbonne = idAbonne;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
