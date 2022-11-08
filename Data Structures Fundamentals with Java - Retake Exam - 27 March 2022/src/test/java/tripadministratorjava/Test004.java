package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Test004 {

    private TripAdministratorImpl tripAdministrations;
    private final Company c1 = new Company("a", 2);
    private final Trip t1 = new Trip("a", 1, Transportation.NONE, 1);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }


    @Test
    public void TestAddTrip() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);

        assertTrue(this.tripAdministrations.exist(t1));
    }
}