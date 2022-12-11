package vaccopsjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Doctor {

    public final String name;
    public final int popularity;

    private final List<Patient> patients;

    public Doctor(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
        this.patients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(this.patients);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public int getPatientsCount() {
        return this.patients.size();
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
