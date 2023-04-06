package com.example.frequencies;

import java.util.Date;

public class Frequence {
    private int id;
    private double frequence;
    private Date dateAjout;
    private int etat;
    private int idAbonne;
    private int idUser;

    public Frequence(int id, double frequence, Date date_ajout, int etat, int id_abonne, int id_user) {
        this.id = id;
        this.frequence = frequence;
        this.dateAjout = date_ajout;
        this.etat = etat;
        this.idAbonne = id_abonne;
        this.idUser = id_user;
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

    @Override
    public String toString() {
        return "Frequence{" +
                "id=" + id +
                ", frequence=" + frequence +
                ", dateAjout=" + dateAjout +
                ", etat=" + etat +
                ", idAbonne=" + idAbonne +
                ", idUser=" + idUser +
                '}';
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
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
