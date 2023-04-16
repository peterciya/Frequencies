package com.example.frequencies;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultFreq {
    @FXML
    Label id, frequence, canal, date, etat, channel, idUser, info;
    @FXML
    Button okButton;

    public void setLabels(String id, String frequence, String canal, String date, String etat, String channel, String idUser){
        this.id.setText(id);
        this.frequence.setText(frequence);
        this.canal.setText(canal);
        this.date.setText(date);
        this.etat.setText(etat);
        this.channel.setText(channel);
        this.idUser.setText(idUser);
    }
    public void setInfoFrequence(){
        if (Integer.parseInt(this.etat.getText()) < 1){
            info.setText("Cette fréquence n'est pas encore attribuée!");
        } else {
            info.setText("Cette fréquence est déjà attribuée!");
        }
    }
}
