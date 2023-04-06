package com.example.frequencies;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
         //scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        stg.setTitle("Frequencies");
        stg.setResizable(false);
        stg.setScene(scene);
        stg.show();
    }

    public void changeScene(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        stg.setScene(scene);
        stg.setResizable(false);
        stg.centerOnScreen();

    }
    public void showAjoutUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("ajoutUser.fxml"));
        AnchorPane ajout = fxmlLoader.load();
        Stage ajoutObjet = new Stage();
        ajoutObjet.setTitle("Ajouter un utilisateur");
        ajoutObjet.setResizable(false);
        ajoutObjet.initModality(Modality.WINDOW_MODAL);
        ajoutObjet.initOwner(stg);
        Scene scene = new Scene(ajout);
        ajoutObjet.setScene(scene);
        ajoutObjet.showAndWait();

    }
    public void showAjoutFrequence() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("ajoutFrequence.fxml"));
        VBox ajout = fxmlLoader.load();
        Stage ajoutObjet = new Stage();
        ajoutObjet.setTitle("Ajouter une fréquence");
        ajoutObjet.setResizable(false);
        ajoutObjet.initModality(Modality.WINDOW_MODAL);
        ajoutObjet.initOwner(stg);
        Scene scene = new Scene(ajout);
        ajoutObjet.setScene(scene);
        ajoutObjet.showAndWait();

    }

    public void showAjoutAbonne() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("ajoutAbonne.fxml"));
        VBox ajout = fxmlLoader.load();
        Stage ajoutObjet = new Stage();
        ajoutObjet.setTitle("Ajouter un Abonné");
        ajoutObjet.setResizable(false);
        ajoutObjet.initModality(Modality.WINDOW_MODAL);
        ajoutObjet.initOwner(stg);
        Scene scene = new Scene(ajout);
        ajoutObjet.setScene(scene);
        ajoutObjet.showAndWait();

    }
    public  void showResult() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("result.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Stage result = new Stage();
        result.setTitle("Résultat");
        result.setResizable(false);
        result.initModality(Modality.WINDOW_MODAL);
        result.initOwner(stg);
        Scene scene = new Scene(pane);
        result.setScene(scene);
        result.show();
    }
    public static void main(String[] args) {launch();}
}