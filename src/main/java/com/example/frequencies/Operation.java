package com.example.frequencies;

import java.util.Date;

public class Operation {
    private int id;
    private String type;
    private Date date;
    private int idUserConnected;
    private int idAbonne;
    private int idFrequence;
    private int idUser;

    public Operation(int id, String type, Date date, int idUserConnected, int idAbonne, int idFrequence, int idUser) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.idUserConnected = idUserConnected;
        this.idAbonne = idAbonne;
        this.idFrequence = idFrequence;
        this.idUser = idUser;
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

    public int getIdUserConnected() {
        return idUserConnected;
    }

    public void setIdUserConnected(int idUserConnected) {
        this.idUserConnected = idUserConnected;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
