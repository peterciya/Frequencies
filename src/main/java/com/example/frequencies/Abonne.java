package com.example.frequencies;

public class Abonne {
    int id;
    String denomination;
    String telephone;
    String email;
    String adresse;
    int id_frequence;

    public Abonne(int id, String denomination, String telephone, String email, String adresse) {
        this.id = id;
        this.denomination = denomination;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.id_frequence = id_frequence;
    }

    public int getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getId_frequence() {
        return id_frequence;
    }
}
