package org.softuni.exam.entities;

public class Movie {
    private String id;

    private int durationInMinutes;

    private String title;

    private double rating;

    private double budget;

    public Movie(String id, int durationInMinutes, String title, double rating, double budget) {
        this.id = id;
        this.durationInMinutes = durationInMinutes;
        this.title = title;
        this.rating = rating;
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getDurationInMinutes() != movie.getDurationInMinutes()) return false;
        if (Double.compare(movie.getRating(), getRating()) != 0) return false;
        if (Double.compare(movie.getBudget(), getBudget()) != 0) return false;
        if (!getId().equals(movie.getId())) return false;
        return getTitle().equals(movie.getTitle());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getDurationInMinutes();
        result = 31 * result + getTitle().hashCode();
        temp = Double.doubleToLongBits(getRating());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getBudget());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
