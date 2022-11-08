package barbershopjava;

public class Barber {

    public String name;
    public int haircutPrice;
    public int stars;

    public Barber(String name, int haircutPrice, int stars) {
        this.name = name;
        this.haircutPrice = haircutPrice;
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Barber)) return false;

        Barber barber = (Barber) o;

        return name.equals(barber.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
