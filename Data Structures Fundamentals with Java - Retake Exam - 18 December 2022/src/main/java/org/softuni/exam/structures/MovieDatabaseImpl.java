package org.softuni.exam.structures;

import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {
    private final Set<Movie> movies;
    private final Map<Actor, Set<Movie>> actorsWithMovies;
    private final Map<Actor, Double> actorsWithMaxBudget;
    private final Set<Actor> newbieActors;

    public MovieDatabaseImpl() {
        this.movies = new LinkedHashSet<>();
        this.actorsWithMovies = new LinkedHashMap<>();
        this.actorsWithMaxBudget = new LinkedHashMap<>();
        this.newbieActors = new LinkedHashSet<>();
    }

    @Override
    public void addActor(Actor actor) {
        this.newbieActors.add(actor);
        this.actorsWithMovies.put(actor, new HashSet<>());
        this.actorsWithMaxBudget.put(actor, Double.MIN_VALUE);
    }

    @Override
    public void addMovie(Actor actor, Movie movie) throws IllegalArgumentException {
        if (!this.contains(actor)) {
            throw new IllegalArgumentException();
        }
        this.movies.add(movie);
        this.actorsWithMovies.get(actor).add(movie);
        Double currentMaxBudget = this.actorsWithMaxBudget.get(actor);
        if (movie.getBudget() > currentMaxBudget) {
            this.actorsWithMaxBudget.put(actor, movie.getBudget());
        }
        this.newbieActors.remove(actor);
    }

    @Override
    public boolean contains(Actor actor) {
        return this.actorsWithMovies.containsKey(actor);
    }

    @Override
    public boolean contains(Movie movie) {
        return this.movies.contains(movie);
    }

    @Override
    public Iterable<Movie> getAllMovies() {
        return this.movies;
    }

    @Override
    public Iterable<Actor> getNewbieActors() {
        return this.newbieActors;
    }

    @Override
    public Iterable<Movie> getMoviesOrderedByBudgetThenByRating() {
        return this.movies
                .stream()
                .sorted((o1, o2) -> {
                    if (Double.compare(o2.getBudget(), o1.getBudget()) == 0) {
                        return Double.compare(o2.getRating(), o1.getRating());
                    }
                    return Double.compare(o2.getBudget(), o1.getBudget());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Actor> getActorsOrderedByMaxMovieBudgetThenByMoviesCount() {
        return this.actorsWithMaxBudget.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (Double.compare(o2.getValue(), o1.getValue()) == 0) {
                        int o1MoviesCount = this.actorsWithMovies.get(o1.getKey()).size();
                        int o2MoviesCount = this.actorsWithMovies.get(o2.getKey()).size();
                        return o2MoviesCount - o1MoviesCount;
                    }
                    return Double.compare(o2.getValue(), o1.getValue());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Movie> getMoviesInRangeOfBudget(double lower, double upper) {
        return this.movies
                .stream()
                .filter(movie -> movie.getBudget() >= lower && movie.getBudget() <= upper)
                .sorted(((o1, o2) -> Double.compare(o2.getRating(), o1.getRating())))
                .collect(Collectors.toList());
    }
}
