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

    public ListItem(String nom) {
        this.nom = nom;
        this.latitude = 0;
        this.longitude = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        return this.nom.equals(((ListItem) o).getNom());
    }
}
