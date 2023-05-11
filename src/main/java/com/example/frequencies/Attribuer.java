package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Attribuer implements Initializable {
    @FXML
    ChoiceBox<Double> frequenceChoiceBox;
    @FXML
    TextField denominationTextField, phoneTextField, mailTextField, adressTextField;
    @FXML
    Label messageLabel;
    @FXML Button validerButton;
    ObservableList<Double> frequencesAttribuees = FXCollections.observableArrayList();
    ObservableList<Double> frequencesNonAttribuees = FXCollections.observableArrayList();
    ObservableList<String> abonnes = FXCollections.observableArrayList();
    ArrayList<Frequence> frequencies = new ArrayList<>();
    ArrayList<Abonne> abonnees = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();
    Timestamp timestamp = new Timestamp(new Date().getTime());
    Operation operation;
    Abonne abonne;
    Frequence frequence;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    Connection connection1 = dbConnection.getConnection();
    String query, query1, query2;
    private int id, idOps, idFreq;
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
                    frequencesNonAttribuees.add(frequence.getFrequence());
                frequencesAttribuees.add(frequence.getFrequence());
                frequencies.add(frequence);
            }
            frequenceChoiceBox.setItems(frequencesNonAttribuees);

            while (resultSet1.next()) {
                if (resultSet1.isLast())
                    id = resultSet1.getInt("id");
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
                if (resultSet2.isLast())
                    idOps = resultSet2.getInt("id");
                operation = new Operation(
                        resultSet2.getInt("id"),
                        resultSet2.getString("type"),
                        resultSet2.getDate("date"),
                        resultSet2.getInt("userConnected"),
                        resultSet2.getInt("idAbonne"),
                        resultSet2.getInt("idFrequence"),
                        resultSet2.getInt("idUser"));
                operations.add(operation);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void attribution(ActionEvent event)throws SQLException{
        charger();
        String reseaux[] = {"081","082","083","084","085","090","097","099"};
        ArrayList rx = new ArrayList();
        for (int i = 0; i < reseaux.length; i++){
            rx.add(reseaux[i]);
        }
        if (denominationTextField.getText().isEmpty() || phoneTextField.getText().isEmpty() ||
                mailTextField.getText().isEmpty() || adressTextField.getText().isEmpty() ||
                frequenceChoiceBox.getSelectionModel().isEmpty()){
            messageLabel.setText("Rassurez-vous de remplir tous les champs!");
        } else if (abonnes.contains(denominationTextField.getText())) {
            messageLabel.setText("Cette chaîne existe déjà, essayez avec une autre.");
        } else if (!((phoneTextField.getText().length()) == 10)) {
            messageLabel.setText("numéro de téléphone incoorect.");
        } else {
            String num = phoneTextField.getText().substring(0,3);
            if (!(rx.contains(num))){
                messageLabel.setText("Réseau téléphonique non pris enccharge.");
            } else {
                connection.setAutoCommit(false);
                connection1.setAutoCommit(false);

                query = "INSERT INTO `abonnes` (`denomination`, `telephone`, `email`, `adresse`) VALUE (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, denominationTextField.getText());
                preparedStatement.setString(2, phoneTextField.getText());
                preparedStatement.setString(3, mailTextField.getText());
                preparedStatement.setString(4, adressTextField.getText());
                try {
                    preparedStatement.execute();
                    query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query1);
                    preparedStatement2.setString(1, "attribution");
                    preparedStatement2.setTimestamp(2, timestamp);
                    preparedStatement2.setInt(3, Login.getUserOnline().getId());
                    preparedStatement2.setInt(4, 0);
                    preparedStatement2.setInt(5, frequenceChoiceBox.getSelectionModel().getSelectedIndex());
                    preparedStatement2.setInt(6, 0);
                    preparedStatement2.execute();
                    try {
                        connection.commit();
                        //frequencesNonAttribuees.clear();
                        charger();
                        PreparedStatement ps = connection.prepareStatement("SELECT * FROM `frequence`");
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()){
                            frequence = new Frequence(
                                    rs.getInt("id"),
                                    rs.getDouble("frequence"),
                                    rs.getInt("canal"),
                                    rs.getInt("etat"));
                            if (frequenceChoiceBox.getSelectionModel().getSelectedItem() == frequence.getFrequence()){
                                idFreq = frequence.getId();
                            }
                        }

                        PreparedStatement preparedStatement1 = connection1.prepareStatement("UPDATE `operation` SET `idAbonne` = ?" +
                                ",`idFrequence` = ? WHERE `operation`.`id` = "+idOps+"");
                        PreparedStatement preparedStatement3 = connection1.prepareStatement("UPDATE `frequence` SET `etat` = ? " +
                                "WHERE `frequence`.`id` = "+idFreq+"");
                        preparedStatement1.setInt(1, id);
                        preparedStatement1.setInt(2, idFreq);
                        preparedStatement3.setInt(1, 1);
                        preparedStatement3.execute();
                        preparedStatement1.execute();
                        charger();
                        connection1.commit();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
