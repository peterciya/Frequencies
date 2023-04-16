package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Attribuer implements Initializable {
    @FXML
    ChoiceBox<String> freqchoice, channelchoice;
    ObservableList<String> frequences = FXCollections.observableArrayList();
    ObservableList<String> frequencesNonAtt = FXCollections.observableArrayList();
    ObservableList<String> abonnes = FXCollections.observableArrayList();
    ArrayList<Frequence> frequencies = new ArrayList<>();
    ArrayList<Abonne> abonnees = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();
    Operation operation;
    Abonne abonne;
    Frequence frequence;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query, query1, query2;
    @FXML
    Button okButton;
    public void charger()throws SQLException{
        try {
            query = "SELECT * FROM `frequence`";
            query1 = "SELECT * FROM `abonnes`";
            query2 = "SELECT * FROM `operation`";

            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            ResultSet resultSet1 = statement1.executeQuery(query1);
            ResultSet resultSet2 = statement2.executeQuery(query2);

            while (resultSet.next()) {
                frequence = new Frequence(
                        resultSet.getInt("id"),
                        resultSet.getDouble("frequence"),
                        resultSet.getInt("canal"),
                        resultSet.getInt("etat"));
                if (frequence.getEtat() < 1)
                    frequencesNonAtt.add(String.valueOf(frequence.getFrequence()));
                frequences.add(String.valueOf(frequence.getFrequence()));
                frequencies.add(frequence);
            }

            while (resultSet1.next()) {
                abonne = new Abonne(
                        resultSet1.getInt("id"),
                        resultSet1.getString("denomination"),
                        resultSet1.getString("telephone"),
                        resultSet1.getString("email"),
                        resultSet1.getString("adresse"));
                abonnes.add(abonne.getDenomination());
                abonnees.add(abonne);
            }

            while (resultSet2.next()) {
                operation = new Operation(
                        resultSet2.getInt("id"),
                        resultSet2.getString("type"),
                        resultSet2.getDate("date"),
                        resultSet2.getInt("idUser"),
                        resultSet2.getInt("idAbonne"),
                        resultSet2.getInt("idFrequence"));
                operations.add(operation);
            }
            channelchoice.setItems(abonnes);
            freqchoice.setItems(frequencesNonAtt);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void attribution(ActionEvent event)throws SQLException{
        connection.setAutoCommit(false);
        Alert alert;
        for (int i = 0; i < frequencies.size(); i++) {
            if(String.valueOf(frequencies.get(i).getFrequence()).equals(freqchoice.getValue()))
                frequence = frequencies.get(i);
                /*query = "INSERT INTO `abonnes` (`denomination`, `telephone`, `email`, `adresse`) VALUE(?,?,?,?)";
                        PreparedStatement ps = connection1.prepareStatement(query);
                        ps.setString(1, "denomination");
                        ps.setString(2, "telephone");
                        ps.setString(3, "email");
                        ps.setString(4, "adresse");
                        ps.execute();*/
        }
        for (int j = 0; j < abonnees.size(); j++) {
            if (String.valueOf(abonnees.get(j).getDenomination()).equals(channelchoice.getValue()))
                abonne = abonnees.get(j);
        }
        for (int k = 0; k < operations.size(); k++){
            if (frequence.getId() == operations.get(k).getId())
                operation = operations.get(k);
        }
        if (abonne.getId() != operation.getIdAbonne()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ATTRIBUTION");
            alert.setHeaderText("Chaîne Affectée!");
            alert.setContentText("Cette chaîne utilise déjà une autre fréquence. Veuillez créer une nouvelle chaîne, puis reéssayez!");
            alert.show();
        } else {
            query = "UPDATE `frequence` SET `etat` = ? WHERE`frequence`.`id` =" + frequence.getId() + "";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, 1);
            query1 = "INSERT INTO `operation` (`type`, `date`, `idUser`, `idAbonne`) VALUE(?,?,?,?)";
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setString(1,"ajout");
            ps1.setDate(2, Date.valueOf("date"));
            ps1.setInt(3, Login.getUserOnline().getId());
            ps1.setInt(4, abonne.getId());
            try {
                ps.execute();
                try {
                    ps1.execute();
                    connection.commit();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
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
