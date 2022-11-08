package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

public class Test005 {

    private TripAdministratorImpl tripAdministrations;
    private final Company c1 = new Company("a", 2);
    private final Trip t1 = new Trip("a", 1, Transportation.NONE, 1);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddTripTwiceThrowException() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t1);
    }
}