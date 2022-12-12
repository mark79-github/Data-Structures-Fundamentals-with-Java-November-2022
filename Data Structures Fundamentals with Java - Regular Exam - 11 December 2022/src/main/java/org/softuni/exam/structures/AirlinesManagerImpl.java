package org.softuni.exam.structures;


import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {

    private final Map<String, Airline> airlines;
    private final Map<String, Flight> allFlights;
    private final Map<String, Flight> completedFlights;
    private final Map<String, Flight> uncompletedFlights;
    private final Map<Airline, Map<String, Flight>> airlineWithFlights;

    public AirlinesManagerImpl() {
        this.airlines = new HashMap<>();
        this.allFlights = new HashMap<>();
        this.completedFlights = new HashMap<>();
        this.uncompletedFlights = new HashMap<>();
        this.airlineWithFlights = new HashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        this.airlines.put(airline.getId(), airline);
        this.airlineWithFlights.put(airline, new HashMap<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!this.contains(airline)) {
            throw new IllegalArgumentException();
        }
        flight.setAirline(airline);
        this.allFlights.put(flight.getId(), flight);
        this.airlineWithFlights.get(airline).put(flight.getId(), flight);
        if (flight.isCompleted()) {
            this.completedFlights.put(flight.getId(), flight);
        } else {
            this.uncompletedFlights.put(flight.getId(), flight);
        }
    }

    @Override
    public boolean contains(Airline airline) {
        return this.airlines.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return this.allFlights.containsKey(flight.getId());
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!this.contains(airline)) {
            throw new IllegalArgumentException();
        }
        this.airlines.remove(airline.getId());
        this.airlineWithFlights.get(airline)
                .forEach((s, flight) -> {
                    this.allFlights.remove(s);
                    this.completedFlights.remove(s);
                    this.uncompletedFlights.remove(s);
                });
        this.airlineWithFlights.remove(airline);
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return this.allFlights.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!this.contains(airline)) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(flight)) {
            throw new IllegalArgumentException();
        }
        flight.setCompleted(true);
        this.completedFlights.put(flight.getId(), flight);
        this.uncompletedFlights.remove(flight.getId());
        return flight;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return this.completedFlights.values();
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return this.allFlights.values()
                .stream()
                .sorted(Comparator.comparing(Flight::isCompleted).thenComparing(Flight::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return this.airlineWithFlights.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (Double.compare(o2.getKey().getRating(), o1.getKey().getRating()) == 0) {
                        if (o2.getValue().size() == o1.getValue().size()) {
                            return o1.getKey().getName().compareTo(o2.getKey().getName());
                        }
                        return Integer.compare(o2.getValue().size(), o1.getValue().size());
                    }
                    return Double.compare(o2.getKey().getRating(), o1.getKey().getRating());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return this.uncompletedFlights.values()
                .stream()
                .filter(flight -> flight.getOrigin().equals(origin) && flight.getDestination().equals(destination))
                .map(Flight::getAirline)
                .collect(Collectors.toSet());
    }
}
