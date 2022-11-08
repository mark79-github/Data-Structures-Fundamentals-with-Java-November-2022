package barbershopjava;

import org.junit.Before;
import org.junit.Test;

public class Test002 {

    private BarberShopImpl barberShop;
    private final Barber b1 = new Barber("a", 1, 1);

    @Before
    public void Setup() {
        this.barberShop = new BarberShopImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddBarberTwice() {
        this.barberShop.addBarber(b1);
        this.barberShop.addBarber(b1);
    }
}