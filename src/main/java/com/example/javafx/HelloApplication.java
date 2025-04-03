package com.example.javafx;

import com.example.javafx.dao.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database.isOK();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("expense-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Tableau de gestion!");
        stage.setScene(scene);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/app_icon.jpg")));
        stage.getIcons().add(icon);

        stage.show();

    }


    public static void main(String[] args) {

        launch();
    }
}