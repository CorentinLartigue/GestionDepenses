package com.example.javafx.controllers;

import com.example.javafx.dao.ExpenseDAO;
import com.example.javafx.dao.IncomeDAO;
import com.example.javafx.models.Expense;
import com.example.javafx.models.Income;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
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
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        System.out.println("Tableau de bord chargé !");
        populateMonthChoiceBox();

        monthChoiceBox.setOnAction(event -> {
            System.out.println("Mois sélectionné: " + monthChoiceBox.getValue());
            loadExpensesForSelectedMonth();  // Met à jour uniquement le PieChart
        });

        loadExpensesForSelectedMonth(); // Mise à jour initiale du PieChart
        updateLineChart();              // Chargement initial du LineChart
        loadLast12MonthsData();         // Chargement initial du BarChart
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
            updatePieChart(expenses);
        }
    }

    private void updatePieChart(List<Expense> expensesForSelectedMonth) {
        pieChart.getData().clear();
        pieChart.setLegendVisible(false);

        if (!expensesForSelectedMonth.isEmpty()) {
            Expense expense = expensesForSelectedMonth.get(0);
            pieChart.getData().add(new PieChart.Data("Logement", expense.getLogement()));
            pieChart.getData().add(new PieChart.Data("Nourriture", expense.getNourriture()));
            pieChart.getData().add(new PieChart.Data("Sorties", expense.getSorties()));
            pieChart.getData().add(new PieChart.Data("Voiture", expense.getVoiture()));
            pieChart.getData().add(new PieChart.Data("Voyage", expense.getVoyage()));
            pieChart.getData().add(new PieChart.Data("Impôts", expense.getImpots()));
            pieChart.getData().add(new PieChart.Data("Autres", expense.getAutres()));
        }
    }

    private void updateLineChart() {
        lineChart.getData().clear();

        XYChart.Series<String, Number> logementSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> nourritureSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> sortiesSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> voitureSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> voyageSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> impotsSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> autresSeries = new XYChart.Series<>();

        logementSeries.setName("Logement");
        nourritureSeries.setName("Nourriture");
        sortiesSeries.setName("Sorties");
        voitureSeries.setName("Voiture");
        voyageSeries.setName("Voyage");
        impotsSeries.setName("Impôts");
        autresSeries.setName("Autres");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDate currentDate = LocalDate.now();

        for (int i = 11; i >= 0; i--) {
            LocalDate month = currentDate.minusMonths(i);
            String formattedMonth = month.format(formatter);
            List<Expense> monthlyExpenses = ExpenseDAO.getExpensesForMonth(formattedMonth);

            float logement = 0, nourriture = 0, sorties = 0, voiture = 0, voyage = 0, impots = 0, autres = 0;

            for (Expense e : monthlyExpenses) {
                logement += e.getLogement();
                nourriture += e.getNourriture();
                sorties += e.getSorties();
                voiture += e.getVoiture();
                voyage += e.getVoyage();
                impots += e.getImpots();
                autres += e.getAutres();
            }

            logementSeries.getData().add(new XYChart.Data<>(formattedMonth, logement));
            nourritureSeries.getData().add(new XYChart.Data<>(formattedMonth, nourriture));
            sortiesSeries.getData().add(new XYChart.Data<>(formattedMonth, sorties));
            voitureSeries.getData().add(new XYChart.Data<>(formattedMonth, voiture));
            voyageSeries.getData().add(new XYChart.Data<>(formattedMonth, voyage));
            impotsSeries.getData().add(new XYChart.Data<>(formattedMonth, impots));
            autresSeries.getData().add(new XYChart.Data<>(formattedMonth, autres));
        }

        lineChart.getData().addAll(
                logementSeries,
                nourritureSeries,
                sortiesSeries,
                voitureSeries,
                voyageSeries,
                impotsSeries,
                autresSeries
        );
    }

    private void loadLast12MonthsData() {
        if (barChart == null) {
            System.out.println("BarChart n'est pas initialisé !");
            return;
        }

        barChart.getData().clear();

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();

        expenseSeries.setName("Dépenses Totales");
        incomeSeries.setName("Revenus Totaux");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        LocalDate currentDate = LocalDate.now();

        for (int i = 11; i >= 0; i--) {
            LocalDate month = currentDate.minusMonths(i);
            String formattedMonth = month.format(formatter);

            List<Expense> monthlyExpenses = ExpenseDAO.getExpensesForMonth(formattedMonth);
            float totalExpenses = 0;
            for (Expense expense : monthlyExpenses) {
                totalExpenses += expense.getTotal();
            }

            List<Income> monthlyIncomes = IncomeDAO.getIncomesForMonth(formattedMonth);
            float totalIncomes = 0;
            for (Income income : monthlyIncomes) {
                totalIncomes += income.getTotal();
            }

            expenseSeries.getData().add(new XYChart.Data<>(formattedMonth, totalExpenses));
            incomeSeries.getData().add(new XYChart.Data<>(formattedMonth, totalIncomes));
        }

        barChart.getData().addAll(expenseSeries, incomeSeries);
    }
}
