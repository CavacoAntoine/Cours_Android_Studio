package com.example.tp6;

public class courseItem {
    private String nom;
    private int quantité;

    public courseItem(String nom, int quantité) {
        this.nom = nom;
        this.quantité = quantité;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantité() {
        return quantité;
    }

    public void incremente() {
        this.quantité++;
    }

    public void decremente() {
        if(this.quantité == 0) {
            throw new IllegalStateException("La quantité ne peux pas être inférieur à zéro.");
        }
        this.quantité--;
    }
}
