package com.example.frequencies;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultChannel {
    @FXML
    Label id, channel, telephone, mail, adresse, frequence;
    @FXML
    Button okButton;
    public void setLabels(String id, String channel, String telephone, String mail, String adresse, String frequence){
        this.id.setText(id);
        this.frequence.setText(frequence);
        this.mail.setText(mail);
        this.telephone.setText(telephone);
        this.adresse.setText(adresse);
        this.channel.setText(channel);
    }
}
