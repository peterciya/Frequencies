package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjoutFrequence{
    @FXML
    Button vldBouton, nettoyerBouton;
    @FXML
    TextField frequenceTextField, messageTextField, date;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query;
    PreparedStatement statement;
    Main main = new Main();

    public void ajouter(ActionEvent event) throws IOException, SQLException {
        if (Double.parseDouble(frequenceTextField.getText()) < 70.0 & Double.parseDouble(frequenceTextField.getText()) > 499.99){
            this.messageTextField.setText("Entrez une fréquence qui soit comprise entre 70.0 499.99 Mhz.");
        } else {
            query = "INSERT INTO `users` ()";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, Double.parseDouble(frequenceTextField.getText()));
            this.vider();
            statement.execute();

        }
    }
    public void nettoyer(ActionEvent event)throws IOException{
        this.vider();
    }

    private void vider(){
        this.messageTextField.setText("");
        this.frequenceTextField.setText("");
    }

}
