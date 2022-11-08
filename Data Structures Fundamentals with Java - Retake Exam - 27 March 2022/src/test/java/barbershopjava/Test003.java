package barbershopjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test003 {

    private BarberShopImpl barberShop;
    private final Barber b1 = new Barber("a", 1, 1);
    private final Barber b2 = new Barber("b", 2, 3);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();
    }

    @Test
    public void TestAddManyBarbers() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b2);

        assertEquals(2, this.barberShop.getBarbers().size());
    }
}