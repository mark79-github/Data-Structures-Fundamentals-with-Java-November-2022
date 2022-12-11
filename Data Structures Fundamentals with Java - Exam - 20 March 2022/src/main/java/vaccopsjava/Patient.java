package vaccopsjava;

public class Patient {

    public final String name;
    public final int height;
    public final int age;
    public final String town;
    private Doctor doctor;

    public Patient(String name, int height, int age, String town) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }

    public String getTown() {
        return town;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;

        Patient patient = (Patient) o;

        return name.equals(patient.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
