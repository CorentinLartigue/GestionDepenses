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

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ExpenseController {

    @FXML
    private TableView<Expense> expenseTable;

    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        expenses.addAll(ExpenseDAO.getAllExpenses());
        expenseTable.setItems(expenses);
    }

    @FXML
    public void addLine() throws IOException {
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
        });
    }
}
