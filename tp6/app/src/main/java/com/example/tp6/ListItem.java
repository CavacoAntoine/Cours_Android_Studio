package com.example.tp6;

public class ListItem {
    private String nom;
    private double latitude;
    private double longitude;

    public ListItem(String nom, double latitude, double longitude) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
