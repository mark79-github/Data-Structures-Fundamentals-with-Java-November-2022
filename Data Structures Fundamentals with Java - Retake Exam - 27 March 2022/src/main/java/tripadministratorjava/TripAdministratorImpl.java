package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {

    private final Map<Company, List<Trip>> companies;
    private final Set<Trip> trips;

    public TripAdministratorImpl() {
        this.companies = new LinkedHashMap<>();
        this.trips = new LinkedHashSet<>();
    }

    @Override
    public void addCompany(Company c) {
        if (exist(c)) {
            throw new IllegalArgumentException();
        }
        this.companies.put(c, new LinkedList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!this.exist(c)) {
            throw new IllegalArgumentException();
        }
        if (this.exist(t)) {
            throw new IllegalArgumentException();
        }
        if (c.tripOrganizationLimit == companies.get(c).size()) {
            throw new IllegalArgumentException();
        } else if (c.tripOrganizationLimit > companies.get(c).size()) {
            companies.get(c).add(t);
            trips.add(t);
        }
    }

    @Override
    public boolean exist(Company c) {
        return this.companies.containsKey(c);
    }

    @Override
    public boolean exist(Trip t) {
        return this.trips.contains(t);
    }

    @Override
    public void removeCompany(Company c) {
        if (!this.exist(c)) {
            throw new IllegalArgumentException();
        }
        List<Trip> companyTrips = this.companies.get(c);
        this.companies.remove(c);
        companyTrips.forEach(trip -> {
            boolean tripExists = this.companies.values()
                    .stream()
                    .anyMatch(tripList -> tripList.contains(trip));
            if (!tripExists) {
                this.trips.remove(trip);
            }
        });
    }

    @Override
    public Collection<Company> getCompanies() {
        return new LinkedList<>(this.companies.keySet());
    }

    @Override
    public Collection<Trip> getTrips() {
        return this.trips;
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!this.exist(c) || !this.exist(t)) {
            throw new IllegalArgumentException();
        }
        if (!this.companies.get(c).contains(t)) {
            throw new IllegalArgumentException();
        }
        this.companies.get(c).remove(t);
        if (!tripExists(t)) {
            this.trips.remove(t);
        }
    }

    private boolean tripExists(Trip trip) {
        return this.companies
                .values()
                .stream()
                .anyMatch(entry -> entry.contains(trip));
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return this.companies
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return this.trips
                .stream()
                .filter(trip -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return this.trips
                .stream()
                .filter(trip -> trip.price >= lo && trip.price <= hi)
                .collect(Collectors.toList());
    }
}
