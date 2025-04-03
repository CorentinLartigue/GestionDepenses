package com.example.javafx.controllers;

import com.example.javafx.models.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class FormulaireDialogController {

    @FXML private DatePicker periodeField;
    @FXML private TextField logementField;
    @FXML private TextField nourritureField;
    @FXML private TextField sortiesField;
    @FXML private TextField voitureField;
    @FXML private TextField voyageField;
    @FXML private TextField impotsField;
    @FXML private TextField autresField;
    @FXML private Button validateButton;

    private Expense newExpense;
    private final Pattern decimalPattern = Pattern.compile("\\d+(\\.\\d{1,2})?");

    @FXML
    public void initialize() {

        periodeField.valueProperty().addListener((obs, oldVal, newVal) -> validateForm());
        addNumericValidation(logementField);
        addNumericValidation(nourritureField);
        addNumericValidation(sortiesField);
        addNumericValidation(voitureField);
        addNumericValidation(voyageField);
        addNumericValidation(impotsField);
        addNumericValidation(autresField);

        validateForm();
    }

    private void addNumericValidation(TextField field) {
        field.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = periodeField.getValue() != null
                && isDecimal(logementField.getText())
                && isDecimal(nourritureField.getText())
                && isDecimal(sortiesField.getText())
                && isDecimal(voitureField.getText())
                && isDecimal(voyageField.getText())
                && isDecimal(impotsField.getText())
                && isDecimal(autresField.getText());

        validateButton.setDisable(!isValid);
    }

    private boolean isDecimal(String value) {
        return value != null && decimalPattern.matcher(value).matches();
    }

    @FXML
    public void handleOk() {
        try {

            float total = Float.parseFloat(logementField.getText()) +
                    Float.parseFloat(nourritureField.getText()) +
                    Float.parseFloat(sortiesField.getText()) +
                    Float.parseFloat(voitureField.getText()) +
                    Float.parseFloat(voyageField.getText()) +
                    Float.parseFloat(impotsField.getText()) +
                    Float.parseFloat(autresField.getText());

            newExpense = new Expense(
                    periodeField.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                    total,
                    Float.parseFloat(logementField.getText()),
                    Float.parseFloat(nourritureField.getText()),
                    Float.parseFloat(sortiesField.getText()),
                    Float.parseFloat(voitureField.getText()),
                    Float.parseFloat(voyageField.getText()),
                    Float.parseFloat(impotsField.getText()),
                    Float.parseFloat(autresField.getText())
            );
            closeForm();
        } catch (Exception e) {
            System.out.println("Erreur de saisie : " + e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        newExpense = null;
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) periodeField.getScene().getWindow();
        stage.close();
    }

    public Optional<Expense> getNewExpense() {
        return Optional.ofNullable(newExpense);
    }
}
