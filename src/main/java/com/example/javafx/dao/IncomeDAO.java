package com.example.javafx.dao;

import com.example.javafx.models.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class IncomeDAO {

    private static final String INSERT_INCOME = """
    INSERT INTO income (date, salary, aids, selfEmployed, passiveIncome, other)
    VALUES (?, ?, ?, ?, ?, ?);
    """;

    private static final String SELECT_ALL_INCOMES = "SELECT * FROM income";

    public static void insertIncome(Income income) {
        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(INSERT_INCOME)) {

            stmt.setString(1, income.getPeriode());
            stmt.setFloat(2, income.getSalaire());
            stmt.setFloat(3, income.getAides());
            stmt.setFloat(4, income.getAutoEntreprises());
            stmt.setFloat(5, income.getRevenusPassifs());
            stmt.setFloat(6, income.getAutres());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Income> getAllIncomes() {
        List<Income> incomes = new ArrayList<>();
        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_INCOMES);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("date");
                float salary = rs.getFloat("salary");
                float aids = rs.getFloat("aids");
                float selfEmployed = rs.getFloat("selfEmployed");
                float passiveIncome = rs.getFloat("passiveIncome");
                float other = rs.getFloat("other");

                float total = salary + aids + selfEmployed + passiveIncome + other;

                Income income = new Income(date, total, salary, aids, selfEmployed, passiveIncome, other);
                incomes.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomes;
    }

    public static List<Income> getIncomesForMonth(String month) {
        List<Income> incomes = new ArrayList<>();

        String query = "SELECT * FROM income WHERE date LIKE ?";

        try (Connection connection = Database.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, month + "/%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("date");
                    float salary = rs.getFloat("salary");
                    float aids = rs.getFloat("aids");
                    float selfEmployed = rs.getFloat("selfEmployed");
                    float passiveIncome = rs.getFloat("passiveIncome");
                    float other = rs.getFloat("other");

                    float total = salary + aids + selfEmployed + passiveIncome + other;

                    Income income = new Income(date, total, salary, aids, selfEmployed, passiveIncome, other);
                    incomes.add(income);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomes;
    }

}

