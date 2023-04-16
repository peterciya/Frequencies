package com.example.frequencies;

import java.util.Date;

public class Frequence {
    private int id;
    private double frequence;
    private int canal;
    private int etat;

    public Frequence(int id, double frequence, int canal, int etat) {
        this.id = id;
        this.frequence = frequence;
        this.canal = canal;
        this.etat = etat;
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

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        this.canal = canal;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Frequence{" +
                "id=" + id +
                ", frequence=" + frequence +
                ", canal=" + canal +
                ", etat=" + etat +
                '}';
    }
}