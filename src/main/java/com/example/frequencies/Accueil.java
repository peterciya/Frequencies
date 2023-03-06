package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Accueil {
    @FXML
    Button accueilBouton, usersBouton, abonnesBouton, frequencesBouton, matriculesBouton, deconnexionBouton, attrBouton, desattrBouton;

    Main main = new Main();
    public void accueil(ActionEvent event)throws IOException{
        main.changeScene("accueil.fxml");
    }
    public void users(ActionEvent event)throws IOException{
        main.changeScene("utilisateurs.fxml");
    }
    public void abonnes(ActionEvent event)throws IOException{
        main.changeScene("abonnees.fxml");
    }
    public void frequence(ActionEvent event)throws IOException{
        main.changeScene("frequencies.fxml");
    }
    public void matricules(ActionEvent event)throws IOException{
        main.changeScene("matricules.fxml");
    }
    public void deconnexion(ActionEvent event)throws IOException{
        main.changeScene("login.fxml");
    }
    public void attribuer(ActionEvent event)throws IOException{

    }
    public void desattribuer(ActionEvent event)throws IOException{

    }
}
