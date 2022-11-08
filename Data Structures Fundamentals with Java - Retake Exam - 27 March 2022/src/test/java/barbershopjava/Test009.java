package barbershopjava;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class Test009 {
    private BarberShopImpl barberShop;
    private final Barber b1 = new Barber("a", 1, 1);
    private final Barber b2 = new Barber("b", 1, 2);
    private final Barber b3 = new Barber("c", 3, 2);
    private final Barber b4 = new Barber("d", 2, 2);
    private final Barber b5 = new Barber("e", 1, 4);
    private final Barber b6 = new Barber("f", 3, 3);
    private final Barber b7 = new Barber("g", 2, 3);
    private final Barber b8 = new Barber("h", 1, 3);
    private final Client c1 = new Client("a", 1, Gender.MALE);
    private final Client c2 = new Client("b", 2, Gender.FEMALE);
    private final Client c3 = new Client("c", 2, Gender.MALE);
    private final Client c4 = new Client("d", 2, Gender.OTHER);
    private final Client c5 = new Client("e", 3, Gender.OTHER);
    private final Client c6 = new Client("f", 4, Gender.MALE);
    private final Client c7 = new Client("g", 3, Gender.MALE);
    private final Client c8 = new Client("h", 3, Gender.FEMALE);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddClientWithSameNameShouldThrowException() {
        this.barberShop.addClient(c1);
        Client client = new Client("a", 2, Gender.FEMALE);
        this.barberShop.addClient(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddBarberWithSameNameShouldThrowException() {
        this.barberShop.addBarber(b1);
        Barber barber = new Barber("a", 2, 3);
        this.barberShop.addBarber(barber);
    }

    @Test
    public void TestGetBarbers() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);
        this.barberShop.addBarber(b3);
        Collection<Barber> barbers = this.barberShop.getBarbers();
        Assert.assertEquals(3, barbers.size());
    }

    @Test
    public void TestGetClients() {
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);
        this.barberShop.addClient(c3);
        this.barberShop.addClient(c4);
        Collection<Client> clients = this.barberShop.getClients();
        Assert.assertEquals(4, clients.size());
    }


    @Test
    public void TestDeleteAllClientsFromBarber() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);
        this.barberShop.assignClient(b1, c1);
        this.barberShop.assignClient(b1, c2);
        Assert.assertEquals(2, this.barberShop.getClients().size());
        this.barberShop.deleteAllClientsFrom(b1);
        this.barberShop.getClients().forEach(client -> Assert.assertNull(client.barber));
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestDeleteAllClientsFromBarberShouldThrowExceptionWhenBarberDoesNotExist() {
        this.barberShop.addBarber(b2);
        this.barberShop.deleteAllClientsFrom(b1);
    }

    @Test
    public void TestGetClientsWithNoBarber() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        Collection<Client> clients = this.barberShop.getClientsWithNoBarber();
        Assert.assertEquals(1, clients.size());
        this.barberShop.assignClient(b1, c1);
        clients = this.barberShop.getClientsWithNoBarber();
        Assert.assertEquals(0, clients.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAssignClientShouldThrowExceptionWhenBarberNotExists() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.assignClient(b2, c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAssignClientShouldThrowExceptionWhenClientNotExists() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.assignClient(b1, c2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAssignClientShouldThrowExceptionWhenBothNotExists() {
        this.barberShop.assignClient(b1, c1);
    }

    @Test
    public void TestAssignClient() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.assignClient(b1, c1);
        Collection<Client> clientsWithNoBarber = this.barberShop.getClientsWithNoBarber();
        Assert.assertEquals(0, clientsWithNoBarber.size());
        Assert.assertNotNull(c1.barber);
    }

    @Test
    public void TestAssignClientWhenClientIsAlreadyAssignedToBarber() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);
        this.barberShop.addClient(c1);
        this.barberShop.assignClient(b1, c1);
        this.barberShop.assignClient(b2, c1);
        Collection<Barber> barbers = this.barberShop.getAllBarbersSortedWithClientsCountDesc();
        String[] expected = {"b", "a"};
        int index = 0;
        for (Barber barber : barbers) {
            Assert.assertEquals(barber.name, expected[index++]);
        }
    }

    @Test
    public void TestGetAllBarbersSortedWithStarsDescendingAndHaircutPriceAscReturnEmptyListWhenNoBarbers() {
        Collection<Barber> barbers = this.barberShop.getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc();
        Assert.assertEquals(barbers, new ArrayList<>());
    }

    @Test
    public void TestGetAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);
        this.barberShop.addBarber(b3);
        this.barberShop.addBarber(b4);
        this.barberShop.addBarber(b5);
        this.barberShop.addBarber(b6);
        this.barberShop.addBarber(b7);
        this.barberShop.addBarber(b8);
        Collection<Barber> barbers = this.barberShop.getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc();
        String[] expected = {"e", "h", "g", "f", "b", "d", "c", "a"};
        int index = 0;
        for (Barber barber : barbers) {
            Assert.assertEquals(barber.name, expected[index++]);
        }
    }

    @Test
    public void TestGetClientsSortedByAgeDescAndBarbersStarsDescReturnEmptyCollectionWhenNoClients() {
        Collection<Client> clients = this.barberShop.getClientsSortedByAgeDescAndBarbersStarsDesc();
        Assert.assertEquals(clients, new ArrayList<>());
    }

    @Test
    public void TestGetClientsSortedByAgeDescAndBarbersStarsDesc() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);
        this.barberShop.addClient(c3);
        this.barberShop.addClient(c4);
        this.barberShop.addClient(c5);
        this.barberShop.addClient(c6);
        this.barberShop.addClient(c7);
        this.barberShop.addClient(c8);

        Collection<Client> clients = this.barberShop.getClientsSortedByAgeDescAndBarbersStarsDesc();
        String[] expected = {"f", "e", "g", "h", "b", "c", "d", "a"};
        int index = 0;
        for (Client client : clients) {
            Assert.assertEquals(client.name, expected[index++]);
        }
    }

    @Test
    public void TestGetClientsSortedByAgeDescAndBarbersStarsDescWhenThereAreClientsWithNoBarber() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);
        this.barberShop.addBarber(b5);
        this.barberShop.addBarber(b6);
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);
        this.barberShop.addClient(c3);
        this.barberShop.addClient(c4);
        this.barberShop.addClient(c5);
        this.barberShop.addClient(c6);
        this.barberShop.addClient(c7);
        this.barberShop.addClient(c8);
        this.barberShop.assignClient(b1, c1);
        this.barberShop.assignClient(b6, c2);
        this.barberShop.assignClient(b2, c3);
        this.barberShop.assignClient(b5, c4);
        this.barberShop.assignClient(b1, c5);
        this.barberShop.assignClient(b1, c6);
        this.barberShop.assignClient(b1, c7);
        this.barberShop.assignClient(b1, c8);
        Collection<Client> clients = this.barberShop.getClientsSortedByAgeDescAndBarbersStarsDesc();
        String[] expected = {"f", "e", "g", "h", "d", "b", "c", "a"};
        int index = 0;
        for (Client client : clients) {
            Assert.assertEquals(client.name, expected[index++]);
        }
    }

}
