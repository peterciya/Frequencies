package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    AjoutFrequence ajoutFrequence;
    Timestamp timestamp = new Timestamp(new Date().getTime());
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    PreparedStatement statement, statement1;
    ResultSet resultSet;
    String query, query1;


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
    public  void ajouter(ActionEvent event) throws IOException, SQLException{
        main.showAjoutFrequence();
        refresh();
    }
    public void supprimer(ActionEvent event) throws IOException, SQLException {

        connection.setAutoCommit(false);
        frequence = frequences.getSelectionModel().getSelectedItem();

        Alert alert, alert1;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de fréquence.");
        alert.setContentText("Etes-vous sûrs de vouloir supprimer cette fréquence?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            query = "DELETE FROM `frequence` WHERE `frequence`.`id` = " + frequence.getId();
            query1 = "INSERT INTO `operation` (`type`, `date`, `idUser`, `idAbonne`, `idFrequence`) VALUE(?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);
            statement1 = connection.prepareStatement(query1);

            statement1.setString(1, "suppression");
            statement1.setTimestamp(2, timestamp);
            statement1.setInt(3, Login.getUserOnline().getId());
            statement1.setInt(4, 0);
            statement1.setInt(5, frequence.getId());
            try {
                statement.execute();
                statement1.execute();
                connection.commit();
                refresh();
                alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Succès");
                alert1.setHeaderText("Succès!");
                alert1.setContentText("La fréquence a été supprimée avec succès.");
                Optional<ButtonType> result1 = alert1.showAndWait();
                if (result1.get() == ButtonType.OK){
                    alert1.close();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            alert.close();
        }
    }
    public void modifier(ActionEvent event)throws IOException{
        frequence = frequences.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ajoutFrequence.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e){
            Logger.getLogger(AjoutUser.class.getName()).log(Level.SEVERE, null, e);
        }
        ajoutFrequence = fxmlLoader.getController();
        ajoutFrequence.setUpdate(true);
        ajoutFrequence.setTextFields(frequence.getId(), frequence.getFrequence(), frequence.getCanal());
        Parent parent = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
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
