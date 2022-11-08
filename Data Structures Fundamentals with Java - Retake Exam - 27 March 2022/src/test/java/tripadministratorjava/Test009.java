package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Test009 {

    private TripAdministratorImpl tripAdministrations;
    private final Company c1 = new Company("a", 2);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test
    public void TestAddCompanyPerf() {
        for (int i = 0; i < 10000; i++) {
            this.tripAdministrations.addCompany(new Company(i + "", i));
        }

        long start = System.currentTimeMillis();
        this.tripAdministrations.addCompany(c1);
        long stop = System.currentTimeMillis();
        assertTrue(stop - start <= 20);
    }
}