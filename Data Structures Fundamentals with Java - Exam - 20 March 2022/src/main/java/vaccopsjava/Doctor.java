package vaccopsjava;

public class Doctor {

    public final String name;
    public final int popularity;

    public Doctor(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;

        Doctor doctor = (Doctor) o;

        return name.equals(doctor.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
