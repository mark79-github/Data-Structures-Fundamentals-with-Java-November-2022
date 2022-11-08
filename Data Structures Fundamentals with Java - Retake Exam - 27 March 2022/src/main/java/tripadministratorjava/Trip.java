package tripadministratorjava;

public class Trip {

    public String id;
    public int peopleLimit;
    public Transportation transportation;
    public int price;

    public Trip(String id, int peopleLimit, Transportation transportation, int price) {
        this.id = id;
        this.peopleLimit = peopleLimit;
        this.transportation = transportation;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;

        Trip trip = (Trip) o;

        return id.equals(trip.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
