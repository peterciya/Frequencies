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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Users implements Initializable {
    @FXML
    Button profileBouton, usersBouton, abonnesBouton, frequencesBouton, deconnexionBouton, ajouterBouton, deleteBouton, refreshBouton, editBouton;
    @FXML
    TableView <User> users;
    @FXML
    TableColumn <User, String> idColumn, prenomColumn, nomColumn, matColumn, sexeColumn, phoneColumn, emailColumn, adressColumn, mdpColumn;
    ObservableList<User> Users = FXCollections.observableArrayList();
    Main main = new Main();
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    ResultSet resultSet;
    PreparedStatement statement;
    User user;
    AjoutUser ajoutUser;
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
            main.changeScene("matricules.fxml");
        }
        public void deconnexion(ActionEvent event) throws IOException{
            main.changeScene("login.fxml");
        }
        public  void ajouter(ActionEvent event) throws IOException{
            main.showAjoutUser();
        }
        public void modifier(ActionEvent event) throws IOException{
            user = users.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ajoutUser.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e){
                Logger.getLogger(AjoutUser.class.getName()).log(Level.SEVERE, null, e);
            }
            ajoutUser = fxmlLoader.getController();
            ajoutUser.setUpdate(true);
            //ajoutUser.setTextFields(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getAdresse(),
                    //user.getTelephone(), user.getMdp(), user.getMatricule(), user.getSexe());

            Parent parent = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }
        public void refresh() throws SQLException{
            Users.clear();
            query ="SELECT * FROM `users`";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
             while (resultSet.next()){
                Users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("prenom"),
                        resultSet.getString("nom"),
                        resultSet.getString("matricule"),
                        resultSet.getString("sexe"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email"),
                        resultSet.getString("adresse"),
                        resultSet.getString("mdp")));
                users.setItems(Users);

             }

        }
        public void supprimer(ActionEvent event) throws IOException, SQLException {
            user = users.getSelectionModel().getSelectedItem();
            query = "DELETE FROM `users` WHERE `users`.`id` = " + user.getId();
            statement = connection.prepareStatement(query);
            statement.execute();
            refresh();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            charger();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    private void charger() throws SQLException{
        refresh();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        matColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        mdpColumn.setCellValueFactory(new PropertyValueFactory<>("mdp"));

    }
}
