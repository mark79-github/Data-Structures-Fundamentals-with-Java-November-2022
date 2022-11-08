package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Test001 {

    private TripAdministratorImpl tripAdministrations;

    private final Company c1 = new Company("a", 2);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test
    public void TestAddCompany() {
        this.tripAdministrations.addCompany(c1);
        assertTrue(this.tripAdministrations.exist(c1));
    }
}