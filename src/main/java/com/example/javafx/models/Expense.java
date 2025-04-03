package com.example.javafx.models;

import java.util.Date;

public class Expense {

    private String periode;
    private Float total;
    private Float logement;
    private Float nourriture;

    private Float sorties;
    private Float voiture;
    private Float voyage;

    private Float impots;
    private Float autres;


    public Expense(String periode, Float total, Float logement, Float nourriture, Float sorties, Float voiture, Float voyage, Float impots, Float autres) {
        this.periode = periode;
        this.total = total;
        this.logement = logement;
        this.nourriture = nourriture;
        this.sorties = sorties;
        this.voiture = voiture;
        this.voyage = voyage;
        this.impots = impots;
        this.autres = autres;
    }

    public Expense() {
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

    public Float getLogement() {
        return logement;
    }

    public void setLogement(Float logement) {
        this.logement = logement;
    }

    public Float getNourriture() {
        return nourriture;
    }

    public void setNourriture(Float nourriture) {
        this.nourriture = nourriture;
    }

    public Float getSorties() {
        return sorties;
    }

    public void setSorties(Float sorties) {
        this.sorties = sorties;
    }

    public Float getVoiture() {
        return voiture;
    }

    public void setVoiture(Float voiture) {
        this.voiture = voiture;
    }

    public Float getVoyage() {
        return voyage;
    }

    public void setVoyage(Float voyage) {
        this.voyage = voyage;
    }

    public Float getImpots() {
        return impots;
    }

    public void setImpots(Float impots) {
        this.impots = impots;
    }

    public Float getAutres() {
        return autres;
    }

    public void setAutres(Float autres) {
        this.autres = autres;
    }
}
