package com.example.frequencies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accueil implements Initializable {
    @FXML ListView<String> listFreqT, listFreqAtt, listFreqNonAtt, listAbonnes, searchListFreq;
    ArrayList<Frequence> frequencies = new ArrayList<>();
    ArrayList<Abonne> abonnees = new ArrayList<>();
    ArrayList<User> arrayusers = new ArrayList<>();
    ObservableList<String> freqT =  FXCollections.observableArrayList();
    ObservableList<String> freqAtt = FXCollections.observableArrayList();
    ObservableList<String> freqNonAtt = FXCollections.observableArrayList();
    ObservableList<String> abonnes = FXCollections.observableArrayList();
    ObservableList<String> listusers = FXCollections.observableArrayList();
    FilteredList<String> filteredSearchListFreq;
    SortedList<String> sortedSearchListFreq;
    @FXML
    Button accueilBouton, usersBouton, abonnesBouton, frequencesBouton, matriculesBouton, deconnexionBouton;
    @FXML Button seeTotalFreqButton, seeTotalFreqAttButton, seeTotalFreqNonAttButton, seeTotalAbonnesButton, searchFrequencyButton;
    @FXML
    TextField searchFrequencyField, searchAbonnesButton, searchUsersButton;
    @FXML
    Label tFreq, tFreqAtt, tFreqNonAtt, tAbonnes, tUsers;

    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query;
    String query1;
    String query2;
    int compteurFrequence = 0;
    int compteurFrequenceAtt = 0;
    int compteurFrequenceNonAtt = 0;
    int compteurAbonne = 0;
    int compteurUser = 0;
    Main main = new Main();
    Frequence frequence;
    Frequence frequence1;
    Result result;
    Stage stage;

    Abonne abonne;
    Abonne abonne1;
    User user;
    User user1;

    public void charger()throws SQLException{
        try {
            query = "SELECT * FROM `frequence`";
            query1 = "SELECT * FROM `abonnes`";
            query2 = "SELECT * FROM `users`";

            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            ResultSet resultSet1 = statement1.executeQuery(query1);
            ResultSet resultSet2 = statement2.executeQuery(query2);

            while (resultSet.next()){
                    compteurFrequence++;
                    frequence = new Frequence(
                            resultSet.getInt("id"),
                            resultSet.getDouble("frequence"),
                            resultSet.getDate("dateAjout"),
                            resultSet.getInt("etat"),
                            resultSet.getInt("idAbonne"),
                            resultSet.getInt("idUser"));
                    freqT.add(String.valueOf(frequence.getFrequence()));
                    frequencies.add(frequence);

                    if (frequence.getEtat() == 1){
                        compteurFrequenceAtt++;
                        freqAtt.add(String.valueOf(frequence.getFrequence()));
                    } else if (frequence.getEtat() == 0) {
                        compteurFrequenceNonAtt++;
                        freqNonAtt.add(String.valueOf(frequence.getFrequence()));
                    }
            }
            filteredSearchListFreq = new FilteredList<>(freqT, b -> true);
            searchFrequencyField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredSearchListFreq.setPredicate(freqT -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null)
                        return true;
                    String keyword = newValue.toLowerCase();
                    if (freqT.indexOf(keyword) > -1){
                        return true;
                    }else {
                        return false;
                    }
                });
            });
            sortedSearchListFreq = new SortedList<>(filteredSearchListFreq);
            searchListFreq.setItems(sortedSearchListFreq);

            listFreqT.setItems(freqT);
            listFreqAtt.setItems(freqAtt);
            listFreqNonAtt.setItems(freqNonAtt);

            while (resultSet1.next()){
                compteurAbonne++;
                abonne = new Abonne(
                        resultSet1.getInt("id"),
                        resultSet1.getString("denomination"),
                        resultSet1.getString("telephone"),
                        resultSet1.getString("email"),
                        resultSet1.getString("adresse"));
                abonnes.add(abonne.getDenomination());
                abonnees.add(abonne);
            }
            listAbonnes.setItems(abonnes);
            while (resultSet2.next()){
                compteurUser++;
                user = new User(
                        resultSet2.getInt("id"),
                        resultSet2.getString("prenom"),
                        resultSet2.getString("nom"),
                        resultSet2.getString("matricule"),
                        resultSet2.getString("sexe"),
                        resultSet2.getString("telephone"),
                        resultSet2.getString("email"),
                        resultSet2.getString("adresse"),
                        resultSet2.getString("mdp"));
                listusers.add(user.getPrenom() + " " + user.getNom());
                arrayusers.add(user);
            }

            tFreqAtt.setText(String.valueOf(compteurFrequenceAtt));
            tFreqNonAtt.setText(String.valueOf(compteurFrequenceNonAtt));
            tFreq.setText(String.valueOf(compteurFrequence));
            tAbonnes.setText(String.valueOf(compteurAbonne));
            tUsers.setText(String.valueOf(compteurUser));

            searchListFreq.setVisible(false);
            listFreqT.setVisible(false);
            listFreqAtt.setVisible(false);
            listFreqNonAtt.setVisible(false);
            listAbonnes.setVisible(false);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void clic(ActionEvent event)throws IOException{
        seeTotalAbonnesButton.setOnMouseClicked(mouseEvent -> listAbonnes.setVisible(true));
        seeTotalFreqButton.setOnMouseClicked(mouseEvent -> listFreqT.setVisible(true));
        seeTotalFreqAttButton.setOnMouseClicked(mouseEvent -> listFreqAtt.setVisible(true));
        seeTotalFreqNonAttButton.setOnMouseClicked(mouseEvent -> listFreqNonAtt.setVisible(true));
        searchFrequencyField.setOnMouseExited(mouseEvent -> searchListFreq.setVisible(false));

    }
    private void displayList(){
        listFreqT.setOnMouseEntered(mouseEvent -> {listFreqT.setVisible(true);});
        listFreqT.setOnMouseExited(mouseEvent -> {listFreqT.setVisible(false);});
        listFreqAtt.setOnMouseEntered(mouseEvent -> {listFreqAtt.setVisible(true);});
        listFreqAtt.setOnMouseExited(mouseEvent -> {listFreqAtt.setVisible(false);});
        listFreqNonAtt.setOnMouseEntered(mouseEvent -> {listFreqNonAtt.setVisible(true);});
        listFreqNonAtt.setOnMouseExited(mouseEvent -> {listFreqNonAtt.setVisible(false);});
        listAbonnes.setOnMouseEntered(mouseEvent -> {listAbonnes.setVisible(true);});
        listAbonnes.setOnMouseExited(mouseEvent -> {listAbonnes.setVisible(false);});
        searchListFreq.setOnMouseEntered(mouseEvent -> searchListFreq.setVisible(true));
        searchListFreq.setOnMouseClicked(mouseEvent -> searchFrequencyField.setText(searchListFreq.getSelectionModel().getSelectedItem()));
        searchListFreq.setOnMouseExited(mouseEvent -> searchListFreq.setVisible(true));
        searchFrequencyButton.setOnMouseClicked(mouseEvent -> searchListFreq.setVisible(false));
    }
    public void search(ActionEvent event) throws IOException{
        ArrayList<String> list = new ArrayList<>(freqT);
        Alert alert;
        if(searchFrequencyField.getText().isBlank() || searchFrequencyField.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Recherche nulle");
            alert.setHeaderText("Le champ de recherche est vide.");
            alert.setContentText("Veuillez taper une fréquence, puis recherchez!");
            alert.show();
        } else if (!(list.contains(searchFrequencyField.getText()))) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fréquence introuvable");
            alert.setHeaderText("Fréquence introuvable.");
            alert.setContentText("Il semble que la fréquence que vous cherchez n'existe pas; Reéssayez avec une autre.");
            alert.show();
        } else {
            for (int i = 0; i < frequencies.size(); i++){
                if (String.valueOf(frequencies.get(i).getFrequence()).equals(String.valueOf(searchFrequencyField.getText()))){
                    frequence1 = frequencies.get(i);
                    if (frequence1.getIdAbonne() > 0){
                        for (int k = 0; k < abonnees.size(); k++){
                            if (frequence1.getIdAbonne() == abonnees.get(k).getId()){
                                abonne1 = abonnees.get(k);
                            }
                        }
                    }
                    for (int j = 0; j < arrayusers.size(); j++){
                        if (frequence1.getIdUser() == arrayusers.get(j).getId()){
                            user1 = arrayusers.get(j);
                        }
                    }
                }
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("result.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
            result = fxmlLoader.getController();
            result.setLabels(String.valueOf(frequence1.getId()), String.valueOf(frequence1.getFrequence()),
                        String.valueOf(frequence1.getDateAjout()), String.valueOf(frequence1.getEtat()),
                        String.valueOf(frequence1.getIdAbonne()), String.valueOf(frequence1.getIdUser()));
            result.setInfo();
            Parent parent = fxmlLoader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            result.okButton.setOnMouseClicked(mouseEvent -> stage.close());
        }
    }
    public void accueil(ActionEvent event)throws IOException{
        main.changeScene("accueil.fxml");
    }
    public void users(ActionEvent event)throws IOException{
        main.changeScene("utilisateurs.fxml");
    }
    public void abonnes(ActionEvent event)throws IOException{
        main.changeScene("abonnees.fxml");
    }
    public void frequence(ActionEvent event)throws IOException{
        main.changeScene("frequences.fxml");
    }
    public void matricules(ActionEvent event)throws IOException{
        main.changeScene("parametres.fxml");
    }
    public void deconnexion(ActionEvent event)throws IOException{
        main.changeScene("login.fxml");
    }

    public void desattribuer(ActionEvent event)throws IOException{

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            charger();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayList();
        searchFrequencyField.setOnMouseClicked(mouseEvent -> searchListFreq.setVisible(true));
        searchFrequencyField.setOnMouseExited(mouseEvent -> searchListFreq.setVisible(false));
        searchListFreq.setOnMouseExited(mouseEvent -> searchListFreq.setVisible(false));
    }
}
