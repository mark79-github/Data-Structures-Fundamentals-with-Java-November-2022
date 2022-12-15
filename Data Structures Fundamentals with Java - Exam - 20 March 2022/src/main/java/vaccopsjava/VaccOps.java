package vaccopsjava;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {

    private final Map<String, Doctor> doctors;
    private final Map<String, Patient> patients;

    public VaccOps() {
        this.doctors = new HashMap<>();
        this.patients = new HashMap<>();
    }

    public void addDoctor(Doctor d) {
        if (this.exist(d)) {
            throw new IllegalArgumentException();
        }
        this.doctors.put(d.name, d);
    }

    public void addPatient(Doctor d, Patient p) {
        if (!this.exist(d)) {
            throw new IllegalArgumentException();
        }
        if (this.exist(p)) {
            throw new IllegalArgumentException();
        }
        p.setDoctor(d);
        this.doctors.get(d.name).addPatient(p);
        this.patients.put(p.name, p);
    }

    public Collection<Doctor> getDoctors() {
        return this.doctors.values();
    }

    public Collection<Patient> getPatients() {
        return this.patients.values();
    }

    public boolean exist(Doctor d) {
        return this.doctors.containsKey(d.name);
    }

    public boolean exist(Patient p) {
        return this.patients.containsKey(p.name);
    }


    public Doctor removeDoctor(String name) {
        if (!this.doctors.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        Doctor doctor = this.doctors.get(name);
        doctor.getPatients().forEach(patient -> this.patients.remove(patient.name));
        this.doctors.remove(name);
        return doctor;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!this.exist(from)) {
            throw new IllegalArgumentException();
        }
        if (!this.exist(to)) {
            throw new IllegalArgumentException();
        }
        if (!this.exist(p)) {
            throw new IllegalArgumentException();
        }
        Doctor doctorFrom = this.doctors.get(from.name);
        Doctor doctorTo = this.doctors.get(to.name);
        doctorFrom.removePatient(p);
        doctorTo.addPatient(p);
    }

    public Collection<Doctor> getDoctorsByPopularity(int popularity) {
        return this.getDoctors()
                .stream()
                .filter(doctor -> doctor.popularity == popularity)
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.getPatients()
                .stream()
                .filter(patient -> patient.town.equalsIgnoreCase(town))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.getPatients()
                .stream()
                .filter(patient -> patient.age >= lo && patient.age <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        return this.getDoctors()
                .stream()
                .sorted(Comparator.comparingInt(Doctor::getPatientsCount).reversed().thenComparing(Doctor::getName))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return this.getPatients()
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.getDoctor().getPopularity() - o2.getDoctor().getPopularity() == 0) {
                        if (o2.getHeight() - o1.getHeight() == 0) {
                            return o1.getAge() - o2.getAge();
                        }
                        return o2.getHeight() - o1.getHeight();
                    }
                    return o1.getDoctor().getPopularity() - o2.getDoctor().getPopularity();
                })
                .collect(Collectors.toList());
    }

}
