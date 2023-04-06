package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Abonnes implements Initializable {
    @FXML
    Button profileBouton, usersBouton, abonnesBouton, frequencesBouton, deconnexionBouton, ajouterBouton, deleteBouton, refreshBouton, editBouton;
    @FXML
    TableView <Abonne> abonnes;
    @FXML
    TableColumn<Abonne, String> idColumn, denominationColumn, adresseColumn, phoneColumn, emailColumn, idFrequenceColumn;
    ObservableList<Abonne> Abonnes = FXCollections.observableArrayList();
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    PreparedStatement statement;
    ResultSet resultSet;
    Main main = new Main();
    Abonne abonne;
    AjoutAbonne ajoutAbonne;
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
    public void matricules(ActionEvent event) throws IOException{
        main.changeScene("parametres.fxml");
    }
    public void deconnexion(ActionEvent event) throws IOException{
        main.changeScene("login.fxml");
    }
    public  void ajouter(ActionEvent event) throws IOException{
        main.showAjoutAbonne();
    }
    public void supprimer(ActionEvent event) throws IOException, SQLException {
        abonne = abonnes.getSelectionModel().getSelectedItem();
        query = "DELETE FROM `users` WHERE `users`.`id` = " + abonne.getId();
        statement = connection.prepareStatement(query);
        statement.execute();
        refresh();
    }
    public void modifier(ActionEvent event)throws IOException{
        abonne = abonnes.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ajoutAbonne.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        ajoutAbonne = fxmlLoader.getController();
        ajoutAbonne.setUpdate(true);
        ajoutAbonne.setTextFields(abonne.getId(), abonne.getDenomination(), abonne.getTelephone(), abonne.getEmail(),
                abonne.getAdresse());

        Parent parent = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    public void refresh() throws SQLException {
        Abonnes.clear();
        query ="SELECT * FROM `abonnes`";
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Abonnes.add(new Abonne(
                    resultSet.getInt("id"),
                    resultSet.getString("denomination"),
                    resultSet.getString("telephone"),
                    resultSet.getString("email"),
                    resultSet.getString("adresse")));
            abonnes.setItems(Abonnes);
        }
    }
    public void charger() throws SQLException{
        refresh();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        denominationColumn.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
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
