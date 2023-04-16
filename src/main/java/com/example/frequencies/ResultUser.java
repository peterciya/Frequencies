package com.example.frequencies;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResultUser {
    @FXML
    Label id, nom, prenom, matricule, sexe, telephone, mail, adresse, nbFreq;
    @FXML
    Button okButton;
    public void setLabels(String id, String nom, String prenom, String matricule, String sexe, String telephone,
                          String mail, String adresse, String nbFreq){
        this.id.setText(id);
        this.nom.setText(nom);
        this.prenom.setText(prenom);
        this.matricule.setText(matricule);
        this.sexe.setText(sexe);
        this.telephone.setText(telephone);
        this.mail.setText(mail);
        this.adresse.setText(adresse);
        this.nbFreq.setText(nbFreq);
    }

}
