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
        if (exist(b)) {
            throw new IllegalArgumentException();
        }
        barbers.put(b, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (exist(c)) {
            throw new IllegalArgumentException();
        }
        clients.add(c);
    }

    @Override
    public boolean exist(Barber b) {
        return barbers.containsKey(b);
    }

    @Override
    public boolean exist(Client c) {
        return clients.contains(c);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return new ArrayList<>(barbers.keySet());
    }

    @Override
    public Collection<Client> getClients() {
        return new ArrayList<>(clients);
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!exist(b) || !exist(c)) {
            throw new IllegalArgumentException();
        }
        if (c.barber != null) {
            Barber barber = c.barber;
            this.barbers.get(barber).remove(c);
        }
        barbers.get(b).add(c);
        c.barber = b;
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!exist(b)) {
            throw new IllegalArgumentException();
        }
        List<Client> clientList = barbers.get(b);
        for (Client client : clientList) {
            clients.remove(client);
        }
        barbers.put(b, new ArrayList<>());
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return clients
                .stream()
                .filter(client -> client.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return barbers.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        return barbers.keySet()
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
        return clients
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
