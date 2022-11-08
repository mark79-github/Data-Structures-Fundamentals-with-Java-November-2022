package barbershopjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test020 {

    private BarberShopImpl barberShop;
    private final Barber b1 = new Barber("a", 1, 1);
    private final Client c1 = new Client("a", 1, Gender.MALE);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();
    }

    @Test
    public void TestAssignClient() {
        this.barberShop.addBarber(b1);
        this.barberShop.addClient(c1);
        this.barberShop.assignClient(b1, c1);
        this.barberShop.deleteAllClientsFrom(b1);

        assertEquals(0, this.barberShop.getClientsWithNoBarber().size());
    }
}