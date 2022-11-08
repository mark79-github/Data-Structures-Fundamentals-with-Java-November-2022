package tripadministratorjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class Test008 {

    private TripAdministratorImpl tripAdministrations;
    private final Company c1 = new Company("a", 2);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test
    public void TestExistCompanyForNotAddingAnyCompany() {
        assertFalse(this.tripAdministrations.exist(c1));
    }
}