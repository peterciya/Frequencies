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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Users implements Initializable {
    @FXML
    Button profileBouton, usersBouton, abonnesBouton, frequencesBouton, deconnexionBouton, ajouterBouton, deleteBouton, refreshBouton, editBouton;
    @FXML
    TableView <User> users;
    Timestamp timestamp = new Timestamp(new Date().getTime());
    @FXML
    TableColumn <User, String> idColumn, prenomColumn, nomColumn, matColumn, sexeColumn, phoneColumn, emailColumn, adressColumn, mdpColumn;
    ObservableList<User> Users = FXCollections.observableArrayList();
    ArrayList<User> arrayUsers = new ArrayList<>();
    Main main = new Main();
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    ResultSet resultSet, resultSet1;
    PreparedStatement statement, statement1;
    User user;
    AjoutUser ajoutUser;
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
        public  void ajouter(ActionEvent event) throws IOException{
            main.showAjoutUser();
        }
        public void modifier(ActionEvent event) throws IOException{
            user = users.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ajoutUtilisateur.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e){
                Logger.getLogger(AjoutUser.class.getName()).log(Level.SEVERE, null, e);
            }
            ajoutUser = fxmlLoader.getController();
            ajoutUser.setUpdate(true);
            ajoutUser.setTextFields(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getAdresse(),
                    user.getTelephone(), user.getMdp(), user.getMatricule(), user.getSexe());

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
        public void supprimer(ActionEvent event) throws SQLException {

            connection.setAutoCommit(false);
            user = users.getSelectionModel().getSelectedItem();

            Alert alert, alert1;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Suppression de fréquence.");
            alert.setContentText("Etes-vous sûrs de vouloir supprimer cet utilisateur?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                query = "DELETE FROM `users` WHERE `users`.`id` = " + user.getId();
                query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";

                statement = connection.prepareStatement(query);
                statement1 = connection.prepareStatement(query1);
                statement1.setString(1, "suppression");
                statement1.setTimestamp(2, timestamp);
                statement1.setInt(3, Login.userOnline.getId());
                statement1.setInt(4, 0);
                statement1.setInt(5, 0);
                statement1.setInt(6, user.getId());
                try {
                    statement1.execute();
                    statement.execute();
                    connection.commit();
                    refresh();
                    alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Succès");
                    alert1.setHeaderText("Succès!");
                    alert1.setContentText("L'utilisateur a été supprimé avec succès.");
                    Optional<ButtonType> result1 = alert1.showAndWait();
                    if (result1.get() == ButtonType.OK){
                        alert1.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                refresh();
            } else {
                alert.close();
            }
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
