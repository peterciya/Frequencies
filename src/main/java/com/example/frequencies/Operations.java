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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Operations implements Initializable {
    @FXML
    Button profileBouton, usersBouton, abonnesBouton, frequencesBouton, deconnexionBouton, ajouterBouton, deleteBouton, refreshBouton, editBouton, operationsBouton;
    @FXML
    TableView<Operation> operations;
    @FXML
    TableColumn<Operation, String> idColumn, typeColumn, dateColumn, idUserColumn, idAbonneColumn, idFrequenceColumn;
    ObservableList<Operation> Operations = FXCollections.observableArrayList();
    Main main = new Main();
    Operation operation;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    PreparedStatement statement;
    ResultSet resultSet;
    String query;


    public void accueil(ActionEvent event) throws IOException {
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
        operation = operations.getSelectionModel().getSelectedItem();
        query = "DELETE FROM `frequence` WHERE `frequences`.`id` = " + operation.getId();
        statement = connection.prepareStatement(query);
        statement.execute();
        refresh();
    }
    public void modifier(ActionEvent event)throws IOException{

    }
    public void refresh() throws SQLException {
        Operations.clear();
        query ="SELECT * FROM `operation`";
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            Operations.add(new Operation(
                    resultSet.getInt("id"),
                    resultSet.getString("type"),
                    resultSet.getDate("date"),
                    resultSet.getInt("idUser"),
                    resultSet.getInt("idAbonne"),
                    resultSet.getInt("idFrequence")));
            operations.setItems(Operations);

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
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idAbonneColumn.setCellValueFactory(new PropertyValueFactory<>("idAbonne"));
        idFrequenceColumn.setCellValueFactory(new PropertyValueFactory<>("idFrequence"));
    }
}
