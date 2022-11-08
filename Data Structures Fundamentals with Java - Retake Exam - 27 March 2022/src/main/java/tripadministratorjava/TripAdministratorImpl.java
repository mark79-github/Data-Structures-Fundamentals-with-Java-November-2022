package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {

    private final Map<Company, List<Trip>> companies;
    private final Set<Trip> trips;


    public TripAdministratorImpl() {
        companies = new LinkedHashMap<>();
        trips = new LinkedHashSet<>();
    }

    @Override
    public void addCompany(Company c) {
        if (exist(c)) {
            throw new IllegalArgumentException();
        }
        companies.put(c, new LinkedList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!exist(c)) {
            throw new IllegalArgumentException();
        }
        if (trips.contains(t)) {
            throw new IllegalArgumentException();
        }
        if (c.tripOrganizationLimit == companies.get(c).size()) {
            throw new IllegalArgumentException();
        }
        if (c.tripOrganizationLimit > companies.get(c).size()) {
            companies.get(c).add(t);
            trips.add(t);
        }
    }

    @Override
    public boolean exist(Company c) {
        return companies.containsKey(c);
    }

    @Override
    public boolean exist(Trip t) {
        return trips.contains(t);
    }

    @Override
    public void removeCompany(Company c) {
        if (!exist(c)) {
            throw new IllegalArgumentException();
        }
        List<Trip> tripList = companies.get(c);
        companies.remove(c);
        for (Trip trip : tripList) {
            long count = companies.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().contains(trip))
                    .count();
            if (count == 0) {
                trips.remove(trip);
            }
        }
    }

    @Override
    public Collection<Company> getCompanies() {
        return new LinkedList<>(companies.keySet());
    }

    @Override
    public Collection<Trip> getTrips() {
        return trips;
    }

    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(t)) {
            throw new IllegalArgumentException();
        }
        if (!companies.get(c).contains(t)) {
            throw new IllegalArgumentException();
        }
        companies.get(c).remove(t);
        if (!tripExists(t)) {
            trips.remove(t);
        }
    }

    private boolean tripExists(Trip trip) {
        for (Map.Entry<Company, List<Trip>> company : companies.entrySet()) {
            List<Trip> tripList = company.getValue();
            if (tripList.contains(trip)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        return companies.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return trips.stream()
                .filter(trip -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return trips.stream()
                .filter(trip -> trip.price >= lo && trip.price <= hi)
                .collect(Collectors.toList());
    }
}
