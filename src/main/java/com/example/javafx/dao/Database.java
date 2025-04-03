package com.example.javafx.dao;

import org.sqlite.JDBC;

import java.io.File;
import java.sql.*;

public class Database {

    private static final String DB_NAME = "database.db";
    private static final String LOCATION = getDatabasePath();

    public static void main(String[] args) {
        if (Database.isOK()) {
            System.out.println("La base de données et les tables sont prêtes !");
        } else {
            System.out.println("Erreur de connexion à la base de données.");
        }
    }

    public static boolean isOK() {
        if (!checkDrivers()) return false;
        if (!checkConnection()) return false;
        return createTableIfNotExists();
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createTables = """
            CREATE TABLE IF NOT EXISTS expense(
                date TEXT NOT NULL,
                housing REAL NOT NULL,
                food REAL NOT NULL,
                goingOut REAL NOT NULL,
                transportation REAL NOT NULL,
                travel REAL NOT NULL,
                tax REAL NOT NULL,
                other REAL NOT NULL
            );
        """;
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(createTables);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + LOCATION);
        } catch (SQLException exception) {
            return null;
        }
        return connection;
    }

    private static String getDatabasePath() {
        String appData;

        // Vérifie si le système d'exploitation est Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Sur Windows, on utilise %APPDATA% (AppData\Roaming)
            appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home") + "\\AppData\\Roaming";
            }
        }
        // Vérifie si le système d'exploitation est macOS
        else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            // Sur macOS, on utilise ~/Library/Application Support
            appData = System.getProperty("user.home") + "/Library/Application Support";
        }
        // Si c'est Linux
        else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux")) {
            // Sur Linux, on peut utiliser ~/.local/share pour les données de l'application
            appData = System.getProperty("user.home") + "/.local/share";
        }
        else {
            // Si un autre OS est détecté, on met un chemin par défaut
            appData = System.getProperty("user.home");
        }

        // Chemin du dossier de l'application
        String appFolder = appData + File.separator + "GestionDepence";

        // Crée le dossier s'il n'existe pas déjà
        File folder = new File(appFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Renvoie le chemin complet vers la base de données
        return appFolder + File.separator + DB_NAME;
    }

}
