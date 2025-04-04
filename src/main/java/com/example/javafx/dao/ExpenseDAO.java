package com.example.javafx.dao;

import com.example.javafx.models.Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ExpenseDAO {

    private static final String INSERT_EXPENSE =
            """
            INSERT INTO expense (date, housing, food, goingOut, transportation, travel, tax, other)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private static final String SELECT_ALL_EXPENSES =
            "SELECT * FROM expense";


    public static void insertExpense(Expense expense) {
        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(INSERT_EXPENSE)) {

            stmt.setString(1, expense.getPeriode());
            stmt.setFloat(2, expense.getLogement());
            stmt.setFloat(3, expense.getNourriture());
            stmt.setFloat(4, expense.getSorties());
            stmt.setFloat(5, expense.getVoiture());
            stmt.setFloat(6, expense.getVoyage());
            stmt.setFloat(7, expense.getImpots());
            stmt.setFloat(8, expense.getAutres());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_EXPENSES);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String periode = rs.getString("date");
                float logement = rs.getFloat("housing");
                float nourriture = rs.getFloat("food");
                float sorties = rs.getFloat("goingOut");
                float voiture = rs.getFloat("transportation");
                float voyage = rs.getFloat("travel");
                float impots = rs.getFloat("tax");
                float autres = rs.getFloat("other");
                float total =logement + nourriture + sorties + voiture + voyage + impots + autres;
                Expense expense = new Expense(periode, total, logement, nourriture, sorties, voiture, voyage, impots, autres);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public static List<Expense> getExpensesForMonth(String month) {
        List<Expense> expenses = new ArrayList<>();

        String query = "SELECT * FROM expense WHERE date LIKE ?";

        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, month + "/%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String periode = rs.getString("date");
                    float logement = rs.getFloat("housing");
                    float nourriture = rs.getFloat("food");
                    float sorties = rs.getFloat("goingOut");
                    float voiture = rs.getFloat("transportation");
                    float voyage = rs.getFloat("travel");
                    float impots = rs.getFloat("tax");
                    float autres = rs.getFloat("other");
                    float total = logement + nourriture + sorties + voiture + voyage + impots + autres;

                    Expense expense = new Expense(periode, total, logement, nourriture, sorties, voiture, voyage, impots, autres);
                    expenses.add(expense);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

}
