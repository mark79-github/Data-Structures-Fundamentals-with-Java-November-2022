package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {

    private final Map<Barber, List<Client>> barbers;
    private final List<Client> clients;

    public BarberShopImpl() {
        this.barbers = new HashMap<>();
        this.clients = new ArrayList<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (this.exist(b)) {
            throw new IllegalArgumentException();
        }
        this.barbers.put(b, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (this.exist(c)) {
            throw new IllegalArgumentException();
        }
        this.clients.add(c);
    }

    @Override
    public boolean exist(Barber b) {
        return this.barbers.containsKey(b);
    }

    @Override
    public boolean exist(Client c) {
        return this.clients.contains(c);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return new ArrayList<>(this.barbers.keySet());
    }

    @Override
    public Collection<Client> getClients() {
        return new ArrayList<>(this.clients);
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!this.exist(b) || !this.exist(c)) {
            throw new IllegalArgumentException();
        }
        if (c.barber != null) {
            Barber barber = c.barber;
            this.barbers.get(barber).remove(c);
        }
        c.barber = b;
        this.barbers.get(b).add(c);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!this.exist(b)) {
            throw new IllegalArgumentException();
        }
        this.barbers.get(b).forEach(this.clients::remove);
        this.barbers.put(b, new ArrayList<>());
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return this.clients
                .stream()
                .filter(client -> client.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return this.barbers.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return this.barbers.keySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.stars - o1.stars == 0) {
                        return o1.haircutPrice - o2.haircutPrice;
                    }
                    return o2.stars - o1.stars;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
        return this.clients
                .stream()
                .filter(client -> client.barber != null)
                .sorted((o1, o2) -> {
                    if (o2.age - o1.age == 0) {
                        return o2.barber.stars - o1.barber.stars;
                    }
                    return o2.age - o1.age;
                })
                .collect(Collectors.toList());
    }
}
