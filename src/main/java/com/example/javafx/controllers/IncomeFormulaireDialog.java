package com.example.javafx.controllers;

import com.example.javafx.models.Income;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

public class IncomeFormulaireDialog {

    private static final Logger logger = LoggerFactory.getLogger(IncomeFormulaireDialog.class);

    @FXML private DatePicker periodeField;
    @FXML private TextField salaireField;
    @FXML private TextField aidesField;
    @FXML private TextField autoEntreprisesField;
    @FXML private TextField revenusPassifsField;
    @FXML private TextField autresField;
    @FXML private Button validateButton;

    private Income newIncome;
    private final Pattern decimalPattern = Pattern.compile("\\d+(\\.\\d{1,2})?");

    @FXML
    public void initialize() {
        logger.info("Initialisation du formulaire de revenus.");

        periodeField.valueProperty().addListener((obs, oldVal, newVal) -> validateForm());
        addNumericValidation(salaireField);
        addNumericValidation(aidesField);
        addNumericValidation(autoEntreprisesField);
        addNumericValidation(revenusPassifsField);
        addNumericValidation(autresField);

        validateForm();
    }

    private void addNumericValidation(TextField field) {
        field.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = periodeField.getValue() != null
                && isDecimal(salaireField.getText())
                && isDecimal(aidesField.getText())
                && isDecimal(autoEntreprisesField.getText())
                && isDecimal(revenusPassifsField.getText())
                && isDecimal(autresField.getText());

        validateButton.setDisable(!isValid);
    }

    private boolean isDecimal(String value) {
        return value != null && decimalPattern.matcher(value).matches();
    }

    @FXML
    public void handleOk() {
        try {
            float total = Float.parseFloat(salaireField.getText()) +
                    Float.parseFloat(aidesField.getText()) +
                    Float.parseFloat(autoEntreprisesField.getText()) +
                    Float.parseFloat(revenusPassifsField.getText()) +
                    Float.parseFloat(autresField.getText());

            newIncome = new Income(
                    periodeField.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                    total,
                    Float.parseFloat(salaireField.getText()),
                    Float.parseFloat(aidesField.getText()),
                    Float.parseFloat(autoEntreprisesField.getText()),
                    Float.parseFloat(revenusPassifsField.getText()),
                    Float.parseFloat(autresField.getText())
            );

            logger.info("Nouveau revenus créée avec succès : {}", newIncome);
            closeForm();
        } catch (Exception e) {
            logger.error("Erreur de saisie dans le formulaire : ", e);
        }
    }

    @FXML
    public void handleCancel() {
        logger.info("Annulation de l'ajout d'un revenu.");
        newIncome = null;
        closeForm();
    }

    private void closeForm() {
        Stage stage = (Stage) periodeField.getScene().getWindow();
        stage.close();
    }

    public Optional<Income> getNewIncome() {
        return Optional.ofNullable(newIncome);
    }
}
