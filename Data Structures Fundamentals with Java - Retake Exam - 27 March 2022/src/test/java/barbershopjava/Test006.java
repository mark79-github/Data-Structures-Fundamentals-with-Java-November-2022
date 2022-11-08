package barbershopjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test006 {

    private BarberShopImpl barberShop;
    private final Client c1 = new Client("a", 1, Gender.MALE);
    private final Client c2 = new Client("b", 1, Gender.FEMALE);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();
    }

    @Test
    public void TestAddManyClients() {
        this.barberShop.addClient(c1);
        this.barberShop.addClient(c2);

        assertEquals(2, this.barberShop.getClients().size());
    }
}