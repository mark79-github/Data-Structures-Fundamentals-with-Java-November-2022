package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

public class Test002 {

    private TripAdministratorImpl tripAdministrations;

    private final Company c1 = new Company("a", 2);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddCompanyTwiceThrowException() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c1);
    }
}