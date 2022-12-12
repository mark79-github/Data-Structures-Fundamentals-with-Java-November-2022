package org.softuni.exam.entities;

public class Airline {
    private String id;

    private String name;

    private double rating;

    public Airline(String id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airline)) return false;

        Airline airline = (Airline) o;

        return getId().equals(airline.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}