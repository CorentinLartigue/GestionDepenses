package com.example.javafx.models;

public class Income {

    private String periode;
    private Float total;
    private Float salaire;
    private Float aides;
    private Float autoEntreprises;
    private Float revenusPassifs;
    private Float autres;


    public Income(String periode, Float autres, Float total, Float salaire, Float aides, Float autoEntreprises, Float revenusPassifs) {
        this.periode = periode;
        this.autres = autres;
        this.total = total;
        this.salaire = salaire;
        this.aides = aides;
        this.autoEntreprises = autoEntreprises;
        this.revenusPassifs = revenusPassifs;
    }

    public Income() {
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getSalaire() {
        return salaire;
    }

    public void setSalaire(Float salaire) {
        this.salaire = salaire;
    }

    public Float getAides() {
        return aides;
    }

    public void setAides(Float aides) {
        this.aides = aides;
    }

    public Float getAutoEntreprises() {
        return autoEntreprises;
    }

    public void setAutoEntreprises(Float autoEntreprises) {
        this.autoEntreprises = autoEntreprises;
    }

    public Float getRevenusPassifs() {
        return revenusPassifs;
    }

    public void setRevenusPassifs(Float revenusPassifs) {
        this.revenusPassifs = revenusPassifs;
    }

    public Float getAutres() {
        return autres;
    }

    public void setAutres(Float autres) {
        this.autres = autres;
    }
}
