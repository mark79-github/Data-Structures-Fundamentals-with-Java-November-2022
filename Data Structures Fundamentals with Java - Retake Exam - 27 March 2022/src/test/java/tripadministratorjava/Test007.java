package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

public class Test007 {

    private TripAdministratorImpl tripAdministrations;
    private final Company c2 = new Company("b", 1);
    private final Trip t1 = new Trip("a", 1, Transportation.NONE, 1);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddTripForNonExistentCompany() {
        this.tripAdministrations.addTrip(c2, t1);
    }
}