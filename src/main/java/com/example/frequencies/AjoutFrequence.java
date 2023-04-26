package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Optional;

public class AjoutFrequence{
    @FXML
    Button validerButton;
    @FXML
    Label titleLabel, messageLabel;
    @FXML
    TextField frequencyTextField, canalTextField;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query, query1;
    Timestamp timestamp = new Timestamp(new Date().getTime());
    PreparedStatement ps, ps1;
    private boolean update;
    private int idFrequency;

    public void setUpdate(Boolean b){ this.update = b; }
    public void setTextFields(int idFrequency, double frequence, int canal) {
        this.idFrequency = idFrequency;
        this.canalTextField.setText(String.valueOf(canal));
        this.frequencyTextField.setText(String.valueOf(frequence));
    }

    public void ajouter(ActionEvent event) throws IOException, SQLException {
        if (frequencyTextField.getText().isEmpty() || canalTextField.getText().isEmpty()){
            messageLabel.setText("Rassurez-vous de remplir tous les champs!");
        }else if (Double.valueOf(frequencyTextField.getText()) > 3000.0 || Double.valueOf(frequencyTextField.getText()) < 30.0) {
            this.messageLabel.setText("Entrez une fréquence qui soit comprise entre 30 et 3000 Mhz.");
        } else if ( Integer.valueOf(canalTextField.getText()) < 0 || Integer.valueOf(canalTextField.getText()) > 10000){
            this.messageLabel.setText("Entrez un canal correct.");
        }
        else {
            Alert alert;
            connection.setAutoCommit(false);
            if (update == false){
                query = "INSERT INTO `frequence` (`frequence`, `canal`) VALUE(?, ?)";
                query1 = "INSERT INTO `operation` (`type`, `date`, `idUser`, `idAbonne`, `idFrequence`) VALUE(?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(query);
                ps1 = connection.prepareStatement(query1);
                ps1.setString(1, "ajout");
                ps1.setTimestamp(2, timestamp);
                ps1.setInt(3, Login.getUserOnline().getId());
                ps1.setInt(4, 0);
                ps1.setInt(5, 0);
                ps.setDouble(1, Double.parseDouble(frequencyTextField.getText()));
                ps.setInt(2, Integer.parseInt(canalTextField.getText()));
            } else {
                titleLabel.setText("Modifications");
                query = "UPDATE `frequence` SET `frequence` = ?, `canal` = ? WHERE `frequence`.`id` = "+idFrequency+"";
                query1 = "INSERT INTO `operation` (`type`, `date`, `idUser`, `idAbonne`, `idFrequence`) VALUE(?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(query);
                ps1 = connection.prepareStatement(query1);
                ps1.setString(1, "modification");
                ps1.setTimestamp(2, timestamp);
                ps1.setInt(3, Login.getUserOnline().getId());
                ps1.setInt(4, 0);
                ps1.setInt(5, idFrequency);
                ps.setDouble(1, Double.parseDouble(frequencyTextField.getText()));
                ps.setInt(2, Integer.parseInt(canalTextField.getText()));
            }

            try {
                ps.execute();
                ps1.execute();
                connection.commit();
                this.vider();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Succès!");
                alert.setContentText("La fréquence a été bien ajoutée.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    alert.close();

                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void vider(){
        this.messageLabel.setText("");
        this.frequencyTextField.clear();
        this.canalTextField.clear();
    }

}
