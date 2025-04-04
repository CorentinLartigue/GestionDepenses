package com.example.javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HeaderController {

    @FXML
    private void switchToDepence(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        switchScene(stage, "expense-view.fxml");
    }

    @FXML
    private void switchToBoard(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        switchScene(stage, "dashboard-view.fxml");
    }

    @FXML
    private void switchToIncome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.control.MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        switchScene(stage, "income-view.fxml");
    }

    private void switchScene(Stage stage, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/" + fxmlFile));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1400, 800);

        stage.setScene(scene);
        stage.show();
    }
}
