package com.example.frequencies;

import java.util.Date;

public class Operation {
    private int id;
    private String type;
    private Date date;
    private int idUser;
    private int idAbonne;
    private int idFrequence;

    public Operation(int id, String type, Date date, int idUser, int idAbonne, int idFrequence) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.idUser = idUser;
        this.idAbonne = idAbonne;
        this.idFrequence = idFrequence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(int idAbonne) {
        this.idAbonne = idAbonne;
    }

    public int getIdFrequence() {
        return idFrequence;
    }

    public void setIdFrequence(int idFrequence) {
        this.idFrequence = idFrequence;
    }
}
