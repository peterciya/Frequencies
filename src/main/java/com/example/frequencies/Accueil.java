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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accueil implements Initializable {
    @FXML ListView<String> listFreqT, listFreqAtt, listFreqNonAtt, listAbonnes, searchListFreq, searchListAbonnes, searchListUsers;
    ArrayList<Frequence> frequencies = new ArrayList<>();
    ArrayList<Abonne> abonnees = new ArrayList<>();
    ArrayList<User> arrayusers = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();
    ObservableList<String> freqT =  FXCollections.observableArrayList();
    ObservableList<String> freqAtt = FXCollections.observableArrayList();
    ObservableList<String> freqNonAtt = FXCollections.observableArrayList();
    ObservableList<String> abonnes = FXCollections.observableArrayList();
    ObservableList<String> listusers = FXCollections.observableArrayList();
    FilteredList<String> filteredSearchListFreq, filteredSearchListAbonnes, filteredSearchListUsers;
    SortedList<String> sortedSearchListFreq, sortedSearchListAbonnes, sortedSearchListUsers;
    @FXML
    Button accueilBouton, usersBouton, abonnesBouton, frequencesBouton, matriculesBouton, deconnexionBouton;
    @FXML Button seeTotalFreqButton, seeTotalFreqAttButton, seeTotalFreqNonAttButton, seeTotalAbonnesButton,
            searchFrequencyButton, searchAbonneButton, searchUserButton;
    @FXML
    TextField searchFrequencyField, searchAbonnesField, searchUsersField;
    @FXML
    Label tFreq, tFreqAtt, tFreqNonAtt, tAbonnes, tUsers;
    DataBaseConnection dbConnection = new DataBaseConnection();
    Connection connection = dbConnection.getConnection();
    String query, query1, query2, query3;
    int compteurFrequence, compteurFrequenceAtt, compteurFrequenceNonAtt, compteurAbonne, compteurUser, compteurOps = 0;
    Main main = new Main();
    Frequence frequence;
    Operation operation;
    Frequence frequence1;
    ResultFreq resultFreq;
    ResultUser resultUser;
    ResultChannel resultChannel;
    Statement statement, statement1, statement2, statement3;
    ResultSet resultSet, resultSet1, resultSet2, resultSet3;
    Abonne abonne;
    Abonne abonne1;
    User user;
    User user1;

    public void charger()throws SQLException{
        try {

            query = "SELECT * FROM `frequence`";
            query1 = "SELECT * FROM `abonnes`";
            query2 = "SELECT * FROM `users`";
            query3 = "SELECT * FROM `operation`";

            statement = connection.createStatement();
            statement1 = connection.createStatement();
            statement2 = connection.createStatement();
            statement3 = connection.createStatement();

            resultSet = statement.executeQuery(query);
            resultSet1 = statement1.executeQuery(query1);
            resultSet2 = statement2.executeQuery(query2);
            resultSet3 = statement3.executeQuery(query3);

            while (resultSet.next()){
                    compteurFrequence++;
                    frequence = new Frequence(
                            resultSet.getInt("id"),
                            resultSet.getDouble("frequence"),
                            resultSet.getInt("canal"),
                            resultSet.getInt("etat"));
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
            filteredSearchListAbonnes = new FilteredList<>(abonnes, b -> true);
            searchAbonnesField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredSearchListAbonnes.setPredicate(abonnes -> {
                    if(newValue.isEmpty() | newValue.isBlank() | newValue == null)
                        return true;
                    String keyword = newValue.toLowerCase();
                    if(abonnes.indexOf(keyword) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));
            sortedSearchListAbonnes = new SortedList<>(filteredSearchListAbonnes);
            searchListAbonnes.setItems(sortedSearchListAbonnes);

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
            filteredSearchListUsers = new FilteredList<>(listusers, b -> true);
            searchUsersField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filteredSearchListUsers.setPredicate(listusers -> {
                    if(newValue.isEmpty() | newValue.isBlank() | newValue == null)
                        return true;
                    String keyword = newValue.toLowerCase();
                    if(arrayusers.indexOf(keyword) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            }));

            while (resultSet3.next()){
                compteurOps++;
                operation = new Operation(
                        resultSet3.getInt("id"),
                        resultSet3.getString("type"),
                        resultSet3.getDate("date"),
                        resultSet3.getInt("userConnected"),
                        resultSet3.getInt("idAbonne"),
                        resultSet3.getInt("idFrequence"),
                        resultSet3.getInt("idUser"));
                operations.add(operation);
            }
            sortedSearchListUsers = new SortedList<>(filteredSearchListUsers);
            searchListUsers.setItems(sortedSearchListUsers);

            tFreqAtt.setText(String.valueOf(compteurFrequenceAtt));
            tFreqNonAtt.setText(String.valueOf(compteurFrequenceNonAtt));
            tFreq.setText(String.valueOf(compteurFrequence));
            tAbonnes.setText(String.valueOf(compteurAbonne));
            tUsers.setText(String.valueOf(compteurUser));

            searchListFreq.setVisible(false);
            searchListUsers.setVisible(false);
            searchListAbonnes.setVisible(false);
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

        searchListAbonnes.setOnMouseEntered(mouseEvent -> searchListAbonnes.setVisible(true));
        searchListAbonnes.setOnMouseClicked(mouseEvent -> searchAbonnesField.setText(searchListAbonnes.getSelectionModel().getSelectedItem()));
        searchListAbonnes.setOnMouseExited(mouseEvent -> searchListAbonnes.setVisible(true));
        searchAbonneButton.setOnMouseExited(mouseEvent -> searchListAbonnes.setVisible(false));

        searchListUsers.setOnMouseEntered(mouseEvent -> searchListUsers.setVisible(true));
        searchListUsers.setOnMouseClicked(mouseEvent -> searchUsersField.setText(searchListUsers.getSelectionModel().getSelectedItem()));
        searchListUsers.setOnMouseExited(mouseEvent -> searchListUsers.setVisible(true));
        searchUserButton.setOnMouseExited(mouseEvent -> searchListUsers.setVisible(false));

    }
    public void search(ActionEvent event) throws IOException, SQLException {
        searchFrequencyButton.setOnMouseClicked(mouseEvent -> {
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
                alert.setContentText("Il semble que la fréquence que vous cherchez n'existe pas dans notre base de données; Reéssayez avec une autre.");
                alert.show();
            } else {
                for (int i = 0, q = frequencies.size(); i < q; i++){
                   if (Double.valueOf(searchFrequencyField.getText()).equals(frequencies.get(i).getFrequence())){
                       frequence1 = frequencies.get(i);
                       for (int j = 0, r = operations.size(); j < r; j++){
                           if (operations.get(j).getIdFrequence() == frequence1.getId()){
                               operation = operations.get(j);
                               for (int k = 0, s = abonnees.size() ; k < s; k++){
                                   if (frequence1.getEtat() > 0 & (operation.getIdAbonne() == abonnees.get(k).getId())){
                                       abonne1 = abonnees.get(k);
                                   }
                               }
                               for (int l = 0, t = arrayusers.size(); l < t; l++){
                                   if (arrayusers.get(l).getId() == operation.getIdUserConnected()) {
                                       user1 = arrayusers.get(l);
                                   }
                               }
                           }

                       }
                   }
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("resultFreq.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                }
                resultFreq = fxmlLoader.getController();
                if ( frequence1.getEtat() > 0){
                    resultFreq.setLabels(String.valueOf(frequence1.getId()), String.valueOf(frequence1.getFrequence()),
                            String.valueOf(frequence1.getCanal()), String.valueOf(operation.getDate()),
                            String.valueOf(frequence1.getEtat()), String.valueOf(abonne1.getDenomination()),
                            (user1.getPrenom() + " "+ user1.getNom()));
                } else {
                    resultFreq.setLabels(String.valueOf(frequence1.getId()), String.valueOf(frequence1.getFrequence()),
                            String.valueOf(frequence1.getCanal()), String.valueOf(operation.getDate()),
                            String.valueOf(frequence1.getEtat()), ("Aucune"),
                            (user1.getPrenom() + " "+ user1.getNom()));
                }

                resultFreq.setInfoFrequence();
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
                resultFreq.okButton.setOnMouseClicked(okButtonMouseEvent -> stage.close());
            }
        });
        searchAbonneButton.setOnMouseClicked(mouseEvent -> {
            ArrayList<String> list = new ArrayList<>(abonnes);
            Alert alert;
            if(searchAbonnesField.getText().isBlank() || searchAbonnesField.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Recherche nulle");
                alert.setHeaderText("Le champ de recherche est vide.");
                alert.setContentText("Veuillez taper un nom de chaîne, puis recherchez!");
                alert.show();
            } else if (!(list.contains(searchAbonnesField.getText()))) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Chaîne introuvable");
                alert.setHeaderText("Chaîne introuvable.");
                alert.setContentText("Il semble que la chaîne que vous cherchez n'existe pas dans notre base de données; Reéssayez avec une autre.");
                alert.show();
            } else {
                for (int i = 0, s = abonnees.size(); i < s; i++){
                    if (abonnees.get(i).getDenomination().equals(String.valueOf(searchAbonnesField.getText()))) {
                        abonne1 = abonnees.get(i);
                        for (int k = 0, t = operations.size(); k < t; k++){
                            if(operations.get(k).getIdAbonne() == abonne1.getId()){
                                operation = operations.get(k);
                            }
                        }
                        for (int l = 0, v = frequencies.size(); l < v; l++){
                            if (frequencies.get(l).getId() == operation.getIdFrequence()){
                                frequence1 = frequencies.get(l);
                            }
                        }
                    }
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("resultChannel.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                }
                resultChannel = fxmlLoader.getController();
                resultChannel.setLabels(String.valueOf(abonne1.getId()), abonne1.getDenomination(),
                        abonne1.getTelephone(), abonne1.getEmail(), abonne1.getAdresse(), String.valueOf(frequence1.getFrequence()));
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
                resultChannel.okButton.setOnMouseClicked(okButtonMouseEvent -> stage.close());
            }
        });
        searchUserButton.setOnMouseClicked(mouseEvent -> {
            ArrayList<String> list = new ArrayList<>(listusers);
            Alert alert;
            int nbfreq = 0;
            if(searchUsersField.getText().isBlank() || searchUsersField.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Recherche nulle");
                alert.setHeaderText("Le champ de recherche est vide.");
                alert.setContentText("Veuillez taper un nom d'utilisateur, puis recherchez!");
                alert.show();
            } else if (!(list.contains(searchUsersField.getText()))) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Utilisateur introuvable");
                alert.setHeaderText("Utilisateur introuvable.");
                alert.setContentText("Il semble que l'utilisateur que vous cherchez n'existe pas dans notre base de données; Reéssayez avec une autre.");
                alert.show();
            } else {
                for (int i = 0, z = arrayusers.size(); i < z; i++){
                    if ((arrayusers.get(i).getPrenom() + " " + arrayusers.get(i).getNom()).equals(String.valueOf(searchUsersField.getText()))) {
                        user1 = arrayusers.get(i);
                        for (int k = 0, x = operations.size(); k < x; k++){
                            if(operations.get(k).getIdUserConnected() == user1.getId() & operations.get(k).getType().equals("ajout")){
                                nbfreq++;
                            }
                        }
                    }
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("resultUser.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                }
                resultUser = fxmlLoader.getController();
                resultUser.setLabels(String.valueOf(user1.getId()), user1.getNom(), user1.getPrenom(), user1.getMatricule(),
                        user1.getSexe(), user1.getTelephone(), user1.getEmail(), user1.getAdresse(), String.valueOf(nbfreq));
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
                resultUser.okButton.setOnMouseClicked(okButtonMouseEvent -> stage.close());
            }
        });

    }

    public void attribuer(ActionEvent event)throws IOException{
        main.showAttribution();
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
    public void operations(ActionEvent event)throws IOException{
        main.changeScene("operations.fxml");
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

        searchUsersField.setOnMouseClicked(mouseEvent -> searchListUsers.setVisible(true));
        searchUsersField.setOnMouseExited(mouseEvent -> searchListUsers.setVisible(false));
        searchListUsers.setOnMouseExited(mouseEvent -> searchListUsers.setVisible(false));

        searchAbonnesField.setOnMouseClicked(mouseEvent -> searchListAbonnes.setVisible(true));
        searchAbonnesField.setOnMouseExited(mouseEvent -> searchListAbonnes.setVisible(false));
        searchListAbonnes.setOnMouseExited(mouseEvent -> searchListAbonnes.setVisible(false));
    }
}
