package tripadministratorjava;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Test011 {

    private TripAdministratorImpl tripAdministrations;

    private final Company c1 = new Company("a", 2);
    private final Company c2 = new Company("b", 5);
    private final Company c3 = new Company("c", 8);
    private final Trip t1 = new Trip("a", 1, Transportation.NONE, 1);
    private final Trip t2 = new Trip("b", 2, Transportation.BUS, 2);
    private final Trip t3 = new Trip("c", 3, Transportation.NONE, 3);
    private final Trip t4 = new Trip("d", 3, Transportation.PLANE, 3);
    private final Trip t5 = new Trip("e", 3, Transportation.NONE, 3);
    private final Trip t6 = new Trip("f", 3, Transportation.PLANE, 3);
    private final Trip t7 = new Trip("g", 3, Transportation.BUS, 3);
    private final Trip t8 = new Trip("h", 3, Transportation.BUS, 3);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddCompanyShouldThrowExceptionWhenTripOrganizationLimitIsEqualToAddedTrips() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        this.tripAdministrations.addTrip(c1, t3);
    }

    @Test
    public void TestGetCompaniesReturnEmptyCollection() {
        Collection<Company> companies = this.tripAdministrations.getCompanies();
        Assert.assertEquals(new ArrayList<>(), companies);
    }

    @Test
    public void TestGetCompanies() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        Collection<Company> companies = this.tripAdministrations.getCompanies();
        Assert.assertEquals(2, companies.size());
    }

    @Test
    public void TestGetTripsReturnEmptyCollection() {
        Collection<Trip> trips = this.tripAdministrations.getTrips();
        Assert.assertEquals(new HashSet<>(), trips);
    }

    @Test
    public void TestGetTrips() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        this.tripAdministrations.addTrip(c2, t3);
        Collection<Trip> trips = this.tripAdministrations.getTrips();
        Assert.assertEquals(3, trips.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRemoveCompanyShouldThrowExceptionWhenCompanyDoesNotExists() {
        this.tripAdministrations.removeCompany(c1);
    }

    @Test
    public void TestRemoveCompany() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.removeCompany(c1);
        Assert.assertFalse(this.tripAdministrations.exist(c1));
        Assert.assertTrue(this.tripAdministrations.exist(c2));
    }

    @Test
    public void TestRemoveCompanyAndAssociatedTrips() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        this.tripAdministrations.addTrip(c2, t3);
        this.tripAdministrations.removeCompany(c1);
        Assert.assertFalse(this.tripAdministrations.exist(c1));
        Collection<Trip> trips = this.tripAdministrations.getTrips();
        Assert.assertEquals(1, trips.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestExecuteTripShouldThrowExceptionWhenCompanyDoesNotExist() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.executeTrip(c2, t1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestExecuteTripShouldThrowExceptionWhenTripDoesNotExist() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.executeTrip(c1, t1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestExecuteTripShouldThrowExceptionWhenCompanyDoesNotOwnTheTrip() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.addTrip(c2, t1);
        this.tripAdministrations.executeTrip(c1, t1);
    }

    @Test
    public void TestExecuteTrip() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        this.tripAdministrations.executeTrip(c1, t1);
        Assert.assertFalse(this.tripAdministrations.exist(t1));
    }

    @Test
    public void TestGetTripsWithTransportationTypeNone() {
        this.tripAdministrations.addCompany(c3);
        this.tripAdministrations.addTrip(c3, t1);
        this.tripAdministrations.addTrip(c3, t2);
        this.tripAdministrations.addTrip(c3, t3);
        this.tripAdministrations.addTrip(c3, t4);
        this.tripAdministrations.addTrip(c3, t5);
        this.tripAdministrations.addTrip(c3, t6);
        this.tripAdministrations.addTrip(c3, t7);
        this.tripAdministrations.addTrip(c3, t8);
        Collection<Trip> trips = this.tripAdministrations.getTripsWithTransportationType(Transportation.NONE);
        Assert.assertEquals(3, trips.size());
        String[] expected = {"a", "c", "e"};
        int index = 0;
        for (Trip trip : trips) {
            Assert.assertEquals(expected[index++], trip.id);
        }
    }

    @Test
    public void TestGetCompaniesWithMoreThatNTrips() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.addCompany(c3);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        this.tripAdministrations.addTrip(c2, t3);
        this.tripAdministrations.addTrip(c2, t4);
        this.tripAdministrations.addTrip(c2, t5);
        this.tripAdministrations.addTrip(c3, t6);
        this.tripAdministrations.addTrip(c3, t7);
        this.tripAdministrations.addTrip(c3, t8);
        Collection<Company> companies = this.tripAdministrations.getCompaniesWithMoreThatNTrips(2);
        Assert.assertEquals(2, companies.size());
        String[] expected = {"b", "c"};
        int index = 0;
        for (Company company : companies) {
            Assert.assertEquals(expected[index++], company.name);
        }
    }

    @Test
    public void TestGetAllTripsInPriceRangeReturnEmptyCollectionWhenNoTrips() {
        Collection<Trip> trips = this.tripAdministrations.getAllTripsInPriceRange(1, 10);
        Assert.assertEquals(0, trips.size());
        Assert.assertEquals(new ArrayList<>(), trips);
    }

    @Test
    public void TestGetAllTripsInPriceRangeReturnEmptyCollectionWhenNoResult() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        Collection<Trip> trips = this.tripAdministrations.getAllTripsInPriceRange(3, 4);
        Assert.assertEquals(0, trips.size());
        Assert.assertEquals(new ArrayList<>(), trips);
    }

    @Test
    public void TestGetAllTripsInPriceRange() {
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c1, t2);
        Collection<Trip> trips = this.tripAdministrations.getAllTripsInPriceRange(2, 4);
        Assert.assertEquals(1, trips.size());
        String[] expected = {"b"};
        int index = 0;
        for (Trip trip : trips) {
            Assert.assertEquals(expected[index++], trip.id);
        }
    }
}