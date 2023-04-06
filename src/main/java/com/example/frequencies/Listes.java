package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Listes implements Initializable {
    @FXML
    ListView <String> freqList;
    ObservableList<String> frequencies = FXCollections.observableArrayList();
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    Frequence frequence;
    String query;


    public void getData()throws SQLException{
        try {
            query = "SELECT * FROM `frequence`";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                frequence = new Frequence(
                        resultSet.getInt("id"),
                        resultSet.getDouble("frequence"),
                        resultSet.getDate("dateAjout"),
                        resultSet.getInt("etat"),
                        resultSet.getInt("idAbonne"),
                        resultSet.getInt("idUser"));
                frequencies.add(String.valueOf(frequence.getFrequence()));
            }
            freqList.setItems(frequencies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
