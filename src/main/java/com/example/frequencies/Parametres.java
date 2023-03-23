package com.example.frequencies;

import javafx.event.ActionEvent;

import java.io.IOException;

public class Parametres {
    Main main = new Main();
    public void accueil(ActionEvent event)throws IOException {
        main.changeScene("accueil.fxml");
    }
    public void users(ActionEvent event)throws IOException{
        main.changeScene("utilisateurs.fxml");
    }
    public void abonnes(ActionEvent event)throws IOException{
        main.changeScene("abonnees.fxml");
    }
    public void frequence(ActionEvent event)throws IOException{
        main.changeScene("frequences.fxml");
    }
    public void matricules(ActionEvent event)throws IOException{
        main.changeScene("parametres.fxml");
    }
    public void deconnexion(ActionEvent event)throws IOException{
        main.changeScene("login.fxml");
    }
}
