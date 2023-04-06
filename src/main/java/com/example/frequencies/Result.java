package com.example.frequencies;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Result {
    @FXML
    Label id, frequence, date, etat, idAbonne, idUser;
    Accueil accueil;
    User user;
    Abonne abonne;
    Frequence freq;
    public void setLabels(String id, String frequence, String date, String etat, String idAbonne, String idUser){
        this.id.setText(id);
        this.frequence.setText(frequence);
        this.date.setText(date);
        this.etat.setText(etat);
        this.idAbonne.setText(idAbonne);
        this.idUser.setText(idUser);
    }

    public Label getEtat() {
        return etat;
    }

    public Label getIdAbonne() {
        return idAbonne;
    }
}
