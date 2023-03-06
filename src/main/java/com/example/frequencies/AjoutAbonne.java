package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AjoutAbonne {
    @FXML
    TextField denominationTextField, phoneTextField, emailTextField, adresseTextField, frequenceTextField, messageTextField;
    @FXML
    Button validerBouton, nettoyerBouton;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    PreparedStatement statement;
    String query;
    boolean update = false;
    int abonneId;
    public void setTextFields(int id, String denomination, String telephone, String email, String adresse, int frequence){
        this.abonneId = id;
        denominationTextField.setText(denomination);
        phoneTextField.setText(telephone);
        emailTextField.setText(email);
        adresseTextField.setText(adresse);
        frequenceTextField.setText(String.valueOf(frequence));
    }
    public void setUpdate(Boolean update){this.update = update;}
    private void vider(){
        denominationTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        adresseTextField.clear();
        frequenceTextField.clear();
        messageTextField.clear();
    }
    public void nettoyer(ActionEvent event) throws IOException{
        this.vider();
    }
    public  void ajouter(ActionEvent event) throws IOException, SQLException {

        this.messageTextField.setText("");
        String reseaux[] = {"081","082","083","084","085","090","097","099"};
        ArrayList rx = new ArrayList();
        for (int i = 0; i < reseaux.length; i++){
            rx.add(reseaux[i]);
        }
        if ((this.phoneTextField.getText()).isEmpty() || (this.denominationTextField.getText()).isEmpty() ||
                (this.messageTextField.getText().isEmpty()) || (this.emailTextField.getText()).isEmpty() ||
                (this.adresseTextField.getText()).isEmpty() || (this.frequenceTextField.getText().isEmpty())) {
            messageTextField.setText("Rassurez-vous d'avoir rempli tous les champs!");
        } else if (!((this.phoneTextField.getText()).length() == 10)) {
            messageTextField.setText("Numéro de téléphone incorrect.");
        } else {
            String num = (this.phoneTextField.getText()).substring(0, 3);
            if (!(rx.contains(num))) {
                messageTextField.setText("Réseau téléphonique incorrect.");
            }  else {
                if (update == false){
                    query = "INSERT INTO `abonnes` (`denomination`, `telephone`, `email`, `adresse`, `frequence`) VALUE(?,?,?,?,?)";
                }else {
                    query ="UPDATE `abonnes` SET `denomination` = ?,`telephone` = ?, `email` = ?,`adresse` = ?," +
                            "`mdp` = ?, `frequence` = ? WHERE `abonnes`.`id` ="+abonneId+"";
                }

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, denominationTextField.getText());
                preparedStatement.setString(5, phoneTextField.getText());
                preparedStatement.setString(6, emailTextField.getText());
                preparedStatement.setString(7, adresseTextField.getText());
                preparedStatement.setInt(2, Integer.parseInt(frequenceTextField.getText()));
                preparedStatement.execute();
                this.vider();

            }
        }
    }
}
