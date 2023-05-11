package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjoutUser implements Initializable {
    @FXML
    TextField prenomTextField, nomTextField, phoneTextField, emailTextField, adresseTextField, messageTextField, passwordField, matriculeTextField;
    @FXML Label  titleLabel;
    @FXML
    ChoiceBox <String> sexeChoiceBox;
    String query, query1;
    Timestamp timestamp = new Timestamp(new Date().getTime());
    DataBaseConnection dbConnection = new DataBaseConnection();
    Operation operation;
    Connection connection = dbConnection.getConnection();
    Connection connection1 = dbConnection.getConnection();
    int firstConnexion = 0;
    Main main = new Main();
    ObservableList<String> listsex = FXCollections.observableArrayList("Masculin","Féminin");
    boolean update;
    int userId, idOps, id, key;
    @FXML
    Button validerBouton, nettoyerBouton;
    public void setTextFields(int id, String nom, String prenom, String email, String adresse, String telephone, String pass,
                              String matricule, String sexe){
        this.userId = id;
        nomTextField.setText(nom);
        prenomTextField.setText(prenom);
        emailTextField.setText(email);
        adresseTextField.setText(adresse);
        phoneTextField.setText(telephone);
        passwordField.setText(pass);
        messageTextField.setText("");
        matriculeTextField.setText(matricule);
        sexeChoiceBox.setValue(sexe);
    }

    public void setUpdate(Boolean b){
        this.update = b;
    }
    private void vider(){
        nomTextField.setText("");
        prenomTextField.setText("");
        emailTextField.setText("");
        adresseTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        messageTextField.setText("");
        matriculeTextField.setText("");
        sexeChoiceBox.setValue("");
    }
    public void nettoyer(ActionEvent event)throws IOException{
        this.vider();

    }
    private void charge()throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users`");
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM `operation`");
        ResultSet resultSet = statement.executeQuery();
        ResultSet resultSet1 = statement1.executeQuery();
        while (resultSet.next()){
            if (resultSet.isLast()){
                id = resultSet.getInt( "id");
            }
        }
        while (resultSet1.next()){
            if (resultSet1.isLast()){
                idOps = resultSet1.getInt("id");
            }
        }
    }

    public void ajouter(ActionEvent event) throws IOException, SQLException {
        Alert alert;
        this.messageTextField.setText("");
        String reseaux[] = {"081","082","083","084","085","090","097","099"};
        ArrayList rx = new ArrayList();
        for (int i = 0; i < reseaux.length; i++){
            rx.add(reseaux[i]);
        }

        if (Login.userOnline.getId() != 3){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Permission");
            alert.setHeaderText("Opération refusée");
            alert.setContentText("Vous n'êtes pas authorisé(e) à cette opération, à moins que vous vous connectiez en tant qu'administrateur.");
            alert.show();
        }
        else if ((this.phoneTextField.getText()).isEmpty() || (this.nomTextField.getText()).isEmpty() || (this.passwordField.getText()).isEmpty() || (this.emailTextField.getText()).isEmpty() || (this.adresseTextField.getText()).isEmpty()){
            messageTextField.setText("Rassurez-vous d'avoir rempli tous les champs!");
        } else if (!((this.phoneTextField.getText()).length() == 10)){
            messageTextField.setText("Numéro de téléphone incorrect.");
        } else {
            String num = (this.phoneTextField.getText()).substring(0,3);
            if (!(rx.contains(num))) {
                messageTextField.setText("Réseau téléphonique non pris en charge.");
            }
            else {
                connection.setAutoCommit(false);
                connection1.setAutoCommit(false);
                if (update == false){
                    query = "INSERT INTO `users` (`prenom`, `nom`, `matricule`, `sexe`, `telephone`, `email`, `adresse`,`mdp`)" +
                            "VALUE(?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, prenomTextField.getText());
                    preparedStatement.setString(2, nomTextField.getText());
                    preparedStatement.setString(3, matriculeTextField.getText());
                    preparedStatement.setString(4, sexeChoiceBox.getValue());
                    preparedStatement.setString(5, phoneTextField.getText());
                    preparedStatement.setString(6, emailTextField.getText());
                    preparedStatement.setString(7, adresseTextField.getText());
                    preparedStatement.setString(8, passwordField.getText());
                    try {
                        preparedStatement.execute();
                        query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement2 = connection.prepareStatement(query1);
                        preparedStatement2.setString(1, "ajout");
                        preparedStatement2.setTimestamp(2, timestamp);
                        preparedStatement2.setInt(3, Login.getUserOnline().getId());
                        preparedStatement2.setInt(4, 0);
                        preparedStatement2.setInt(5, 0);
                        preparedStatement2.setInt(6, 0);
                        preparedStatement2.execute();
                        try {
                            connection.commit();
                            charge();
                            PreparedStatement preparedStatement1 = connection1.prepareStatement("UPDATE `operation` SET `idUser` = ? " +
                                    "WHERE `operation`.`id` = "+idOps+"");
                            preparedStatement1.setInt(1, id);
                            preparedStatement1.execute();
                            connection1.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    vider();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ajout");
                    alert.setHeaderText("Succès!");
                    alert.setContentText("Le nouvel utilisateur a été bien ajouté.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        alert.close();
                    }
            }
            else {
                    titleLabel.setText("Modifications");
                    query ="UPDATE `users` SET `prenom` = ?,`nom` = ?,`matricule` = ?,`sexe` = ?,`telephone` = ?," +
                            "`email` = ?,`adresse` = ?,`mdp` = ? WHERE `users`.`id` ="+userId+"";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, prenomTextField.getText());
                    preparedStatement.setString(2, nomTextField.getText());
                    preparedStatement.setString(3, matriculeTextField.getText());
                    preparedStatement.setString(4, sexeChoiceBox.getValue());
                    preparedStatement.setString(5, phoneTextField.getText());
                    preparedStatement.setString(6, emailTextField.getText());
                    preparedStatement.setString(7, adresseTextField.getText());
                    preparedStatement.setString(8, passwordField.getText());
                    try {
                        preparedStatement.execute();
                        query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";

                        PreparedStatement preparedStatement2 = connection.prepareStatement(query1);
                        preparedStatement2.setString(1, "modifications");
                        preparedStatement2.setTimestamp(2, timestamp);
                        preparedStatement2.setInt(3, Login.getUserOnline().getId());
                        preparedStatement2.setInt(4, 0);
                        preparedStatement2.setInt(5, 0);
                        preparedStatement2.setInt(5, 0);
                        preparedStatement2.setInt(6, userId);
                        preparedStatement2.execute();
                        connection.commit();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    vider();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modifications");
                    alert.setHeaderText("Succès!");
                    alert.setContentText("Les modifications ont été bien appliquées.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        alert.close();
                    }
                }
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexeChoiceBox.setItems(listsex);
    }
}
