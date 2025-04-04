package com.example.javafx.controllers;

import com.example.javafx.dao.IncomeDAO;
import com.example.javafx.models.Income;
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

public class IncomeController {

    private static final Logger logger = LoggerFactory.getLogger(IncomeController.class);

    @FXML
    private TableView<Income> incomeTable;

    private final ObservableList<Income> incomes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        logger.info("Initialisation du contrôleur IncomesController.");
        try {
            incomes.addAll(IncomeDAO.getAllIncomes());
            incomeTable.setItems(incomes);
            logger.info("Données des revenus chargées avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors du chargement des revenus : ", e);
        }
    }

    @FXML
    public void addIncome() {
        logger.info("Ajout d'une nouvelle ligne de revenus.");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafx/form-income-view.fxml"));
            Stage formStage = new Stage();
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setTitle("Ajouter un revenu");
            formStage.setScene(new Scene(fxmlLoader.load(), 800, 600));

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/javafx/assets/app_icon.jpg")));
            formStage.getIcons().add(icon);

            IncomeFormulaireDialog formController = fxmlLoader.getController();
            formStage.showAndWait();

            Optional<Income> newIncome = formController.getNewIncome();
            newIncome.ifPresent(income -> {
                IncomeDAO.insertIncome(income);
                incomes.add(income);
                logger.info("Nouveau revenu ajoutée : {}", income);
            });
        } catch (IOException e) {
            logger.error("Erreur lors de l'ouverture du formulaire de revenus : ", e);
        }
    }
}
