package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login{
    @FXML TextField loginTextField, passField;
    @FXML Button validerBouton;
    @FXML
    Label warningLabel;

    public void checkAll(ActionEvent event) throws IOException {

        Main main = new Main();
        DataBaseConnection dbconnection = new DataBaseConnection();
        Connection connection = dbconnection.getConnection();

        String myQuery = "SELECT matricule, mdp FROM users";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(myQuery);
            System.out.println("OK");

            while (resultSet.next()) {
                String resultSetLogin = resultSet.getString("matricule");
                String resultSetPwd = resultSet.getString("mdp");

                if((loginTextField.getText().toString()).isEmpty() || (passField.getText().toString()).isEmpty()){
                    warningLabel.setText("Remplissez tous les champs.");
                }
                else if(!(loginTextField.getText().toString().equals(resultSetLogin))
                        || !(passField.getText().toString().equals(resultSetPwd))){
                    warningLabel.setText("Login ou Mot de passe invalide!");

                }
                else {
                    main.changeScene("accueil.fxml");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}