package com.example.javafx.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.*;


public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static final String DB_NAME = "database.db";
    private static final String LOCATION = getDatabasePath();

    public static boolean isOK() {
        if (!checkDrivers()) return false;
        if (!checkConnection()) return false;
        return createTableIfNotExists();
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            logger.info("Drivers SQLite chargés avec succès.");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Erreur lors du chargement des drivers SQLite : ", e);
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            if (connection != null) {
                logger.info("Connexion à la base de données réussie.");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Erreur de connexion à la base de données : ", e);
        }
        return false;
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
        try (Connection connection = Database.connect();
             PreparedStatement statement = connection.prepareStatement(createTables)) {
            statement.executeUpdate();
            logger.info("Table 'expense' vérifiée/créée avec succès.");
            return true;
        } catch (SQLException exception) {
            logger.error("Erreur lors de la création des tables : ", exception);
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        try {
            Connection connection = DriverManager.getConnection(dbPrefix + LOCATION);
            logger.info("Connexion à la base de données établie : {}", LOCATION);
            return connection;
        } catch (SQLException exception) {
            logger.error("Impossible de se connecter à la base de données : ", exception);
            return null;
        }
    }

    private static String getDatabasePath() {
        String appData;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            appData = System.getenv("APPDATA");
            if (appData == null) {
                appData = System.getProperty("user.home") + "\\AppData\\Roaming";
            }
        } else if (os.contains("mac")) {
            appData = System.getProperty("user.home") + "/Library/Application Support";
        } else if (os.contains("nix") || os.contains("nux")) {
            appData = System.getProperty("user.home") + "/.local/share";
        } else {
            appData = System.getProperty("user.home");
        }

        String appFolder = appData + File.separator + "GestionDepence";
        File folder = new File(appFolder);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                logger.info("Dossier créé pour stocker la base de données : {}", appFolder);
            } else {
                logger.warn("Impossible de créer le dossier pour la base de données.");
            }
        }

        String databasePath = appFolder + File.separator + DB_NAME;
        logger.info("Chemin de la base de données : {}", databasePath);
        return databasePath;
    }

}
