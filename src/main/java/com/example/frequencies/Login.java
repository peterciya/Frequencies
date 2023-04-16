package com.example.frequencies;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML TextField loginTextField, passTextField;
    @FXML
    PasswordField passField;
    @FXML Button validerBouton, eyeButton, eyeButton1;
    @FXML
    Label warningLabel;
    User user;

    public static User getUserOnline() {
        return userOnline;
    }

    static User userOnline;


    private void hide(){
        passTextField.setVisible(false);
        eyeButton1.setVisible(false);
    }

    public void checkAll(ActionEvent event) throws IOException {

        Main main = new Main();
        DataBaseConnection dbconnection = new DataBaseConnection();
        Connection connection = dbconnection.getConnection();

        String myQuery = "SELECT * FROM users";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(myQuery);
            System.out.println("OK");

            while (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("matricule"),
                        resultSet.getString("sexe"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email"),
                        resultSet.getString("adresse"),
                        resultSet.getString("mdp"));

                if((loginTextField.getText().toString()).isEmpty() || (passField.getText().toString()).isEmpty()){
                    warningLabel.setText("Veuillez remplir tous les champs.");
                    warningLabel.setTextFill(Paint.valueOf("#FF4141"));
                }
                else if(!(loginTextField.getText().toString().equals(user.getMatricule()))
                        || !(passField.getText().toString().equals(user.getMdp()))){
                    warningLabel.setText("Login ou mot de passe invalide!");
                    warningLabel.setTextFill(Paint.valueOf("#FF4141"));

                }
                else {
                    userOnline = this.user;
                    main.changeScene("accueil.fxml");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void printNewUser(){
        System.out.println(userOnline.getId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hide();
        eyeButton.setOnMousePressed(mouseEvent -> {
            passTextField.setText(passField.getText());
            passField.setVisible(false);
            eyeButton.setVisible(false);
            passTextField.setVisible(true);
            eyeButton1.setVisible(true);
        });

        eyeButton.setOnMouseReleased(mouseEvent -> {
            passField.setText(passTextField.getText());
            passTextField.setVisible(false);
            eyeButton1.setVisible(false);
            passField.setVisible(true);
            eyeButton.setVisible(true);
        });
    }
}