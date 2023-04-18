package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Frequences implements Initializable {
    @FXML
    Button profileBouton, usersBouton, abonnesBouton, frequencesBouton, deconnexionBouton, ajouterBouton, deleteBouton, refreshBouton, editBouton;
    @FXML
    TableView<Frequence> frequences;
    @FXML
    TableColumn<Frequence, String> idColumn, frequenceColumn, canalColumn, etatColumn;
    ObservableList<Frequence> Frequences = FXCollections.observableArrayList();
    Main main = new Main();
    Frequence frequence;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    PreparedStatement statement;
    ResultSet resultSet;
    String query;


    public void profile(ActionEvent event) throws IOException {
        main.changeScene("accueil.fxml");
    }
    public void users(ActionEvent event) throws IOException{
        main.changeScene("utilisateurs.fxml");
    }
    public void abonnes(ActionEvent event) throws IOException{
        main.changeScene("abonnees.fxml");
    }
    public void frequences(ActionEvent event) throws IOException{
        main.changeScene("frequences.fxml");
    }
    public void operations(ActionEvent event) throws IOException{
        main.changeScene("operations.fxml");
    }
    public void deconnexion(ActionEvent event) throws IOException{
        main.changeScene("login.fxml");
    }
    public  void ajouter(ActionEvent event) throws IOException{
        main.showAjoutFrequence();
    }
    public void supprimer(ActionEvent event) throws IOException, SQLException {
        frequence = frequences.getSelectionModel().getSelectedItem();
        query = "DELETE FROM `frequence` WHERE `frequences`.`id` = " + frequence.getId();
        statement = connection.prepareStatement(query);
        statement.execute();
        refresh();
    }
    public void modifier(ActionEvent event)throws IOException{

    }
    public void refresh() throws SQLException {
        Frequences.clear();
        query ="SELECT * FROM `frequence`";
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            Frequences.add(new Frequence(
                    resultSet.getInt("id"),
                    resultSet.getDouble("frequence"),
                    resultSet.getInt("canal"),
                    resultSet.getInt("etat")));
            frequences.setItems(Frequences);

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

    private void charger() throws SQLException {
        refresh();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frequenceColumn.setCellValueFactory(new PropertyValueFactory<>("frequence"));
        canalColumn.setCellValueFactory(new PropertyValueFactory<>("canal"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
    }
}
