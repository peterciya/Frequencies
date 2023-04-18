package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AjoutUser implements Initializable {
    @FXML
    TextField prenomTextField, nomTextField, phoneTextField, emailTextField, adresseTextField, messageTextField,passwordField1, passwordField2, matriculeTextField;
    @FXML
    ChoiceBox <String> sexeChoiceBox;
    String query;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();

    ObservableList<String> listsex = FXCollections.observableArrayList("Masculin","Féminin");
    boolean update = false;
    int userId;
    @FXML
    Button validerBouton, nettoyerBouton;
    public void setTextFields(int id, String nom, String prenom, String email, String adresse, String telephone, String pass,
                              String matricule, String sexe){
        this.userId = id;
        nomTextField.setText(nom);
        prenomTextField.setText(prenom);
        emailTextField.setText(email);
        adresseTextField.setText(adresse);
        phoneTextField.setText(telephone);
        passwordField1.setText(pass);
        passwordField2.setText(pass);
        messageTextField.setText("");
        matriculeTextField.setText(matricule);
        sexeChoiceBox.setValue(sexe);
    }
    public void setUpdate(Boolean update){
        this.update = update;
    }
    private void vider(){
        nomTextField.setText("");
        prenomTextField.setText("");
        emailTextField.setText("");
        adresseTextField.setText("");
        phoneTextField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        messageTextField.setText("");
        matriculeTextField.setText("");
        sexeChoiceBox.setValue("");
    }
    public void nettoyer(ActionEvent event)throws IOException{
        this.vider();

    }

    public void ajouter(ActionEvent event) throws IOException, SQLException {

        this.messageTextField.setText("");
        String reseaux[] = {"081","082","083","084","085","090","097","099"};
        ArrayList rx = new ArrayList();
        for (int i = 0; i < reseaux.length; i++){
            rx.add(reseaux[i]);
        }

        if ((this.phoneTextField.getText()).isEmpty() || (this.nomTextField.getText()).isEmpty() || (this.passwordField1.getText()).isEmpty() ||
                (this.passwordField2.getText()).isEmpty() || (this.emailTextField.getText()).isEmpty() || (this.adresseTextField.getText()).isEmpty()){
            messageTextField.setText("Rassurez-vous d'avoir rempli tous les champs!");
        } else if (!((this.phoneTextField.getText()).length() == 10)){
            messageTextField.setText("Numéro de téléphone incorrect.");
        } else if (!(this.passwordField1.getText().toString().equals(this.passwordField2.getText().toString()))) {
            messageTextField.setText("les 2 mots de passes ne sont pas identiques.");

        } else {
            String num = (this.phoneTextField.getText()).substring(0,3);
            if (!(rx.contains(num))) {
                messageTextField.setText("Réseau téléphonique incorrect.");
            }
            else {
                if (update == false){
                    query = "INSERT INTO `users` (`prenom`, `nom`, `matricule`, `sexe`, `telephone`, `email`, `adresse`,`mdp`)" +
                            "VALUE(?,?,?,?,?,?,?,?)";
                }else {
                    query ="UPDATE `users` SET `prenom` = ?,`nom` = ?,`matricule` = ?,`sexe` = ?,`telephone` = ?," +
                            "`email` = ?,`adresse` = ?,`mdp` = ? WHERE `users`.`id` ="+userId+"";
                }

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, prenomTextField.getText());
                preparedStatement.setString(2, nomTextField.getText());
                preparedStatement.setString(3, matriculeTextField.getText());
                preparedStatement.setString(4, sexeChoiceBox.getValue());
                preparedStatement.setString(5, phoneTextField.getText());
                preparedStatement.setString(6, emailTextField.getText());
                preparedStatement.setString(7, adresseTextField.getText());
                preparedStatement.setString(8, passwordField1.getText());
                preparedStatement.execute();
                this.vider();

            }
        }


    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexeChoiceBox.setItems(listsex);
    }
}
