package com.example.javafx.controllers;

import com.example.javafx.dao.ExpenseDAO;
import com.example.javafx.models.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @FXML
    private TableView<Expense> expenseTable;

    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        logger.info("Initialisation du contrôleur ExpenseController.");
        try {
            expenses.addAll(ExpenseDAO.getAllExpenses());
            expenseTable.setItems(expenses);
            logger.info("Données des dépenses chargées avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des dépenses : ", e);
        }
    }

    @FXML
    public void addLine() {
        logger.info("Ajout d'une nouvelle ligne de dépense.");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafx/form-expense-view.fxml"));
            Stage formStage = new Stage();
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setTitle("Ajouter une dépense");
            formStage.setScene(new Scene(fxmlLoader.load(), 800, 600));

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/javafx/assets/app_icon.jpg")));
            formStage.getIcons().add(icon);

            FormulaireDialogController formController = fxmlLoader.getController();
            formStage.showAndWait();

            Optional<Expense> newExpense = formController.getNewExpense();
            newExpense.ifPresent(expense -> {
                ExpenseDAO.insertExpense(expense);
                expenses.add(expense);
                logger.info("Nouvelle dépense ajoutée : {}", expense);
            });
        } catch (IOException e) {
            logger.error("Erreur lors de l'ouverture du formulaire de dépense : ", e);
        }
    }
}
