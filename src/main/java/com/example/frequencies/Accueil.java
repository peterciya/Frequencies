package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Accueil implements Initializable {
    @FXML
    Button accueilBouton, usersBouton, abonnesBouton, frequencesBouton, matriculesBouton, deconnexionBouton;
    @FXML
    Label tFreq, tFreqAtt, tFreqNonAtt, tUsers, tAbonnes;

    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query;
    String query1;
    String query2;
    int compteurFrequence = 0;
    int compteurFrequenceAtt = 0;
    int compteurFrequenceNonAtt = 0;
    int compteurAbonne = 0;
    int compteurUser = 0;
    Main main = new Main();
    Frequence frequence;
    Abonne abonne;
    User user;
    public void charger()throws SQLException{
        try {
            query = "SELECT * FROM `frequence`";
            query1 = "SELECT * FROM `abonnes`";
            query2 = "SELECT * FROM `users`";

            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            ResultSet resultSet1 = statement1.executeQuery(query1);
            ResultSet resultSet2 = statement2.executeQuery(query2);

            while (resultSet.next()){
                    compteurFrequence++;
                    frequence = new Frequence(
                            resultSet.getInt("id"),
                            resultSet.getDouble("frequence"),
                            resultSet.getDate("dateAjout"),
                            resultSet.getInt("etat"),
                            resultSet.getInt("idAbonne"),
                            resultSet.getInt("idUser"));
                    if (frequence.getEtat() == 1){
                        compteurFrequenceAtt++;
                    } else
                        compteurFrequenceNonAtt++;
            }
            while (resultSet1.next())
                    compteurAbonne++;
            while (resultSet2.next())
                compteurUser++;
            tFreqAtt.setText(String.valueOf(compteurFrequenceAtt));
            tFreqNonAtt.setText(String.valueOf(compteurFrequenceNonAtt));
            tFreq.setText(String.valueOf(compteurFrequence));
            tAbonnes.setText(String.valueOf(compteurAbonne));
            tUsers.setText(String.valueOf(compteurUser));

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
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
        main.changeScene("frequences.fxml");
    }
    public void matricules(ActionEvent event)throws IOException{
        main.changeScene("parametres.fxml");
    }
    public void deconnexion(ActionEvent event)throws IOException{
        main.changeScene("login.fxml");
    }

    public void desattribuer(ActionEvent event)throws IOException{

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            charger();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
