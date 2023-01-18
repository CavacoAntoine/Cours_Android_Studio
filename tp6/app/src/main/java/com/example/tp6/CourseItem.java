package com.example.tp6;

public class CourseItem {
    private String nom;
    private int quantité;

    public CourseItem(String nom, int quantité) {
        this.nom = nom;
        this.quantité = quantité;
    }

    public CourseItem(String nom) {
        this.nom = nom;
        this.quantité = 0;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantité() {
        return quantité;
    }

    public void incremente(int add) {
        this.quantité += add;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        return this.getNom().equals(((CourseItem) obj).getNom());
    }
}
