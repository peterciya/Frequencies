package com.example.frequencies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class AjoutFrequence{
    @FXML
    Button validerButton;
    @FXML
    Label titleLabel, messageLabel;
    @FXML
    TextField frequencyTextField, canalTextField;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    Connection connection1 = dbConnection.getConnection();
    Frequence frequence;
    ArrayList<Double> frequences = new ArrayList<>();
    String query, query1;
    int id, idOps;
    Timestamp timestamp = new Timestamp(new Date().getTime());
    PreparedStatement ps, ps1;
    private boolean update;
    private int idFrequency;

    public void setUpdate(Boolean b){ this.update = b; }
    public void setTextFields(int idFrequency, double frequence, int canal) {
        this.idFrequency = idFrequency;
        this.canalTextField.setText(String.valueOf(canal));
        this.frequencyTextField.setText(String.valueOf(frequence));
    }
    private void charge()throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `frequence`");
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM `operation`");
        ResultSet resultSet = statement.executeQuery();
        ResultSet resultSet1 = statement1.executeQuery();
        while (resultSet.next()){
            if (resultSet.isLast()){
                id = resultSet.getInt("id");
            }
        }

        while (resultSet1.next()){
            if (resultSet1.isLast()){
                idOps = resultSet1.getInt("id");
            }
        }

    }
    public void ajouter(ActionEvent event) throws IOException, SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM `frequence`");
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()){
            frequences.add(resultSet.getDouble("frequence"));

        }
        
        if (frequencyTextField.getText().isEmpty() || canalTextField.getText().isEmpty()){
            messageLabel.setText("Rassurez-vous de remplir tous les champs!");
        }else if (Double.valueOf(frequencyTextField.getText()) > 3000.0 || Double.valueOf(frequencyTextField.getText()) < 30.0) {
            this.messageLabel.setText("Entrez une fréquence qui soit comprise entre 30 et 3000 Mhz.");
        } else if ( Integer.valueOf(canalTextField.getText()) < 0 || Integer.valueOf(canalTextField.getText()) > 10000){
            this.messageLabel.setText("Entrez un canal correct.");
        } else if (frequences.contains(Double.valueOf(frequencyTextField.getText()))) {
            this.messageLabel.setText("Cette fréquence existe déjà, essayez avec une autre.");
        } else {
            Alert alert;
            connection.setAutoCommit(false);
            connection1.setAutoCommit(false);
            if (update == false){

                query = "INSERT INTO `frequence` (`frequence`, `canal`) VALUE(?, ?)";
                ps = connection.prepareStatement(query);
                ps.setDouble(1, Double.parseDouble(frequencyTextField.getText()));
                ps.setInt(2, Integer.parseInt(canalTextField.getText()));
                try {
                    ps.execute();
                    query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";
                    ps1 = connection.prepareStatement(query1);
                    ps1.setString(1, "ajout");
                    ps1.setTimestamp(2, timestamp);
                    ps1.setInt(3, Login.getUserOnline().getId());
                    ps1.setInt(4, 0);
                    ps1.setInt(5, 0);
                    ps1.setInt(6, 0);
                    ps1.execute();
                    try {
                        connection.commit();
                        charge();
                        PreparedStatement preparedStatement1 = connection1.prepareStatement("UPDATE `operation` SET `idFrequence` = ? " +
                                "WHERE `operation`.`id` = "+idOps+"");
                        preparedStatement1.setInt(1, id);
                        System.out.println(id);
                        System.out.println(idOps);
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
                alert.setContentText("La nouvelle fréquence a été bien ajoutée.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    alert.close();
                }


            } else {

                titleLabel.setText("Modifications");
                query = "UPDATE `frequence` SET `frequence` = ?, `canal` = ? WHERE `frequence`.`id` = "+idFrequency+"";
                ps = connection.prepareStatement(query);
                ps.setDouble(1, Double.parseDouble(frequencyTextField.getText()));
                ps.setInt(2, Integer.parseInt(canalTextField.getText()));
                try {
                    ps.execute();
                    query1 = "INSERT INTO `operation` (`type`, `date`, `userConnected`, `idAbonne`, `idFrequence`, `idUser`) VALUE(?, ?, ?, ?, ?, ?)";
                    ps1 = connection.prepareStatement(query1);
                    ps1.setString(1, "modifications");
                    ps1.setTimestamp(2, timestamp);
                    ps1.setInt(3, Login.getUserOnline().getId());
                    ps1.setInt(4, 0);
                    ps1.setInt(5, idFrequency);
                    ps1.setInt(6, 0);
                    ps1.execute();
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                vider();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modifications");
                alert.setHeaderText("Succès!");
                alert.setContentText("Les modifications ont été bien appliquée.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    alert.close();
                }
            }
        }
    }

    private void vider(){
        this.messageLabel.setText("");
        this.frequencyTextField.clear();
        this.canalTextField.clear();
    }

}
