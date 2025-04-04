package com.example.javafx.controllers;

import com.example.javafx.dao.ExpenseDAO;
import com.example.javafx.models.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardController {

    @FXML
    private Label dashboardTitle;

    @FXML
    private ChoiceBox<String> monthChoiceBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    public void initialize() {
        System.out.println("Tableau de bord chargé !");

        populateMonthChoiceBox();

        monthChoiceBox.setOnAction(event -> {
            System.out.println("Mois sélectionné: " + monthChoiceBox.getValue());
            loadExpensesForSelectedMonth();
        });

        loadExpensesForSelectedMonth();
    }


    private void populateMonthChoiceBox() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 12; i++) {
            LocalDate month = currentDate.minusMonths(i);
            monthChoiceBox.getItems().add(month.format(formatter));
        }

        String currentMonth = currentDate.format(formatter);
        monthChoiceBox.setValue(currentMonth);
    }


    private void loadExpensesForSelectedMonth() {
        String selectedMonth = monthChoiceBox.getValue();
        if (selectedMonth != null) {
            System.out.println("Chargement des dépenses pour le mois : " + selectedMonth);
            List<Expense> expenses = ExpenseDAO.getExpensesForMonth(selectedMonth);
            if (expenses.isEmpty()) {
                System.out.println("Aucune dépense trouvée pour le mois: " + selectedMonth);
            }
            updateCharts(expenses);
        }
    }


    private void updateCharts(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            return;
        }

        pieChart.getData().clear();
        for (Expense expense : expenses) {
            pieChart.getData().add(new PieChart.Data("Logement", expense.getLogement()));
            pieChart.getData().add(new PieChart.Data("Nourriture", expense.getNourriture()));
            pieChart.getData().add(new PieChart.Data("Sorties", expense.getSorties()));
            pieChart.getData().add(new PieChart.Data("Voiture", expense.getVoiture()));
            pieChart.getData().add(new PieChart.Data("Voyage", expense.getVoyage()));
            pieChart.getData().add(new PieChart.Data("Impôts", expense.getImpots()));
            pieChart.getData().add(new PieChart.Data("Autres", expense.getAutres()));
        }

        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Dépenses par catégorie");

        for (Expense expense : expenses) {
            series.getData().add(new XYChart.Data<>("Logement", expense.getLogement()));
            series.getData().add(new XYChart.Data<>("Nourriture", expense.getNourriture()));
            series.getData().add(new XYChart.Data<>("Sorties", expense.getSorties()));
            series.getData().add(new XYChart.Data<>("Voiture", expense.getVoiture()));
            series.getData().add(new XYChart.Data<>("Voyage", expense.getVoyage()));
            series.getData().add(new XYChart.Data<>("Impôts", expense.getImpots()));
            series.getData().add(new XYChart.Data<>("Autres", expense.getAutres()));
        }

        lineChart.getData().add(series);
    }

}
