package com.example.frequencies;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class Result {
    @FXML
    Label id, frequence, date, etat, idAbonne, idUser, info;
    @FXML
    Button okButton;

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
    public void setInfo(){
        if (Integer.parseInt(this.etat.getText()) < 1){
            info.setText("Cette fréquence n'est pas encore attribuée!");
        } else {
            info.setText("Cette fréquence est déjà attribuée!");
        }
    }

    public Label getEtat() {
        return etat;
    }

    public Label getIdAbonne() {
        return idAbonne;
    }
}
