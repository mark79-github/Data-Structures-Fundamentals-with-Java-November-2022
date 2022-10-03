package vaccopsjava;

import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {

    private final Map<Doctor, List<Patient>> doctors;
    private final List<Patient> patients;

    public VaccOps() {
        this.doctors = new HashMap<>();
        this.patients = new ArrayList<>();
    }

    public void addDoctor(Doctor d) {
        if (this.doctors.containsKey(d)) {
            throw new IllegalArgumentException();
        }
        this.doctors.put(d, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        if (!this.doctors.containsKey(d)) {
            throw new IllegalArgumentException();
        }
        if (this.patients.contains(p)) {
            throw new IllegalArgumentException();
        }
        List<Patient> patientList = this.doctors.get(d);
        patientList.add(p);
        this.patients.add(p);
    }

    public Collection<Doctor> getDoctors() {
        return this.doctors.keySet();
    }

    public Collection<Patient> getPatients() {
        return this.patients;
    }

    public boolean exist(Doctor d) {
        return this.doctors.containsKey(d);
    }

    public boolean exist(Patient p) {
        return this.patients.contains(p);
    }

    public Doctor removeDoctor(String name) {
        Doctor doctor = this.doctors.keySet()
                .stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        for (Patient patient : this.doctors.get(doctor)) {
            this.patients.remove(patient);
        }
        this.doctors.remove(doctor);
        return doctor;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        boolean fromDoctorExists = this.exist(from);
        boolean toDoctorExists = this.exist(to);
        boolean patientExists = this.exist(p);

        if (!fromDoctorExists || !toDoctorExists || !patientExists) {
            throw new IllegalArgumentException();
        }

        if (this.doctors.get(from).remove(p)) {
            this.doctors.get(to).add(p);
        }
    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return this.doctors.keySet()
                .stream()
                .filter(d -> d.getPopularity() == populariry)
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.patients.stream()
                .filter(p -> {
                    String townLC = town.toLowerCase();
                    String patientTownLC = p.getTown().toLowerCase();
                    return townLC.equals(patientTownLC);
                })
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.patients.stream()
                .filter(p -> p.getAge() >= lo && p.getAge() <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        return this.doctors
                .entrySet()
                .stream()
                .sorted((f, s) -> {
                    if (s.getValue().size() - f.getValue().size() == 0) {
                        return f.getKey().getName().compareTo(s.getKey().getName());
                    }
                    return s.getValue().size() - f.getValue().size();
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        Comparator<Patient> comparator = Comparator.comparingInt(Patient::getHeight).reversed().thenComparingInt(Patient::getAge);
        TreeMap<Integer, List<Patient>> map = new TreeMap<>();
        this.doctors
                .keySet()
                .stream()
                .sorted(Comparator.comparingInt(Doctor::getPopularity))
                .forEach(doctor -> map.putIfAbsent(doctor.getPopularity(), new ArrayList<>()));

        for (Map.Entry<Doctor, List<Patient>> doctorsList : this.doctors.entrySet()) {
            int popularity = doctorsList.getKey().getPopularity();
            List<Patient> patientList = map.get(popularity);
            patientList.addAll(doctorsList.getValue());
        }

        List<Patient> patientList = new ArrayList<>();
        for (List<Patient> value : map.values()) {
            value.sort(comparator);
            patientList.addAll(value);
        }
        return patientList;
    }

}
