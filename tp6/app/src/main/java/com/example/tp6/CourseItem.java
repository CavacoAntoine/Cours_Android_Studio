package com.example.tp6;

public class CourseItem {
    private String nom;
    private int quantité;

    public CourseItem(String nom, int quantité) {
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

    public void decremente() throws IllegalStateException {
        if(this.quantité == 0) {
            throw new IllegalStateException("La quantité ne peux pas être inférieur à zéro.");
        }
        int i = this.quantité--;
    }
}
