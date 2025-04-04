package com.example.javafx;

import com.example.javafx.dao.Database;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {

        if (Database.isOK()) {
            logger.info("La base de données et les tables sont prêtes !");
        } else {
            logger.error("Erreur de connexion à la base de données.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafx/dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Tableau de Bord");
        stage.setScene(scene);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/javafx/assets/app_icon.jpg")));
        stage.getIcons().add(icon);

        stage.show();
    }

    public static String getLogDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        String logDir;

        if (os.contains("win")) {
            logDir = System.getenv("APPDATA") + "\\GestionDepence\\logs";
        } else if (os.contains("mac")) {
            logDir = System.getProperty("user.home") + "/Library/Application Support/GestionDepence/logs";
        } else if (os.contains("nix") || os.contains("nux")) {
            logDir = System.getProperty("user.home") + "/.local/share/GestionDepence/logs";
        } else {
            logDir = System.getProperty("user.home") + "/GestionDepence/logs";
        }

        File logFolder = new File(logDir);
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        }

        return logDir;
    }

    public static void main(String[] args) {
        System.setProperty("log.dir", getLogDirectory());

        PropertyConfigurator.configure(HelloApplication.class.getResourceAsStream("/com/example/javafx/log4j.properties"));
        launch();
    }
}
