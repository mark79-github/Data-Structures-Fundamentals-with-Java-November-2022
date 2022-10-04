package vaccopsjava;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class VaccOpsTests {

    private VaccOps vaccOps;
    Doctor d1 = new Doctor("a", 1);
    Doctor d2 = new Doctor("b", 1);
    Doctor d3 = new Doctor("c", 1);
    Doctor d4 = new Doctor("d", 3);
    Doctor d5 = new Doctor("e", 4);
    Doctor d6 = new Doctor("f", 4);
    Doctor d7 = new Doctor("g", 2);
    Doctor d8 = new Doctor("h", 2);
    Patient p1 = new Patient("a", 1, 1, "a");
    Patient p2 = new Patient("b", 1, 2, "b");
    Patient p3 = new Patient("c", 1, 3, "c");
    Patient p4 = new Patient("d", 3, 1, "a");
    Patient p5 = new Patient("e", 3, 1, "a");
    Patient p6 = new Patient("f", 2, 1, "a");
    Patient p7 = new Patient("g", 5, 10, "a");
    Patient p8 = new Patient("h", 5, 5, "a");
    Patient p9 = new Patient("i", 5, 50, "a");
    Patient p10 = new Patient("j", 2, 2, "a");
    Patient p11 = new Patient("k", 1, 2, "a");

    @Before
    public void setup() {
        this.vaccOps = new VaccOps();
    }

    @Test // 1
    public void testAddingDoctor() {
        vaccOps.addDoctor(d1);
        var d = new ArrayList<>(this.vaccOps.getDoctors());

        assertEquals(1, d.size());
        Assert.assertSame(d.get(0).getName(), d1.getName());
        assertEquals(d.get(0).getPopularity(), d1.getPopularity());
    }

    @Test // 2
    public void testAddingMultipleDoctors() {
        for (int i = 0; i < 1000; i++) {
            this.vaccOps.addDoctor(new Doctor(i + "", i));
        }

        assertEquals(1000, this.vaccOps.getDoctors().size());
    }


    @Test(expected = IllegalArgumentException.class) // 3
    public void testAddingDoctorAlreadyExistThrowException() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d1);
    }

    @Test // 4
    public void testAddingPatient() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        var p = new ArrayList<>(this.vaccOps.getPatients());

        assertEquals(1, p.size());
        assertEquals(p.get(0).getName(), p1.getName());
        assertEquals(p.get(0).getHeight(), p1.getHeight());
        assertEquals(p.get(0).getTown(), p1.getTown());
        assertEquals(p.get(0).getAge(), p1.getAge());
    }

    @Test // 5
    public void testAddingMultiplePatients() {
        this.vaccOps.addDoctor(d1);
        for (int i = 0; i < 1000; i++) {
            var p = new Patient(i + "", i, i, i + "");
            this.vaccOps.addPatient(d1, p);
        }

        assertEquals(1000, this.vaccOps.getPatients().size());
    }


    @Test(expected = IllegalArgumentException.class) // 6
    public void testAddingPatientWithNonExistentDoctorThrowException() {
        this.vaccOps.addPatient(d1, p1);
    }


    @Test // 7
    public void testNotAddingAnyDoctors() {
        assertEquals(0, this.vaccOps.getPatients().size());
    }


    @Test // Performance
    public void testAddDoctorPerf() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.vaccOps.addDoctor(new Doctor(i + "", i));
        }

        long stop = System.currentTimeMillis();
        assertTrue(stop - start <= 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingPatientShouldThrowExceptionIfAlreadyExists() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d2, p1);
    }

    @Test
    public void testExistsDoctorReturnTrue() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addDoctor(d4);
        boolean actual = this.vaccOps.exist(d3);
        assertTrue(actual);
    }

    @Test
    public void testExistsDoctorReturnFalse() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addDoctor(d4);
        boolean actual = this.vaccOps.exist(d5);
        assertFalse(actual);
    }

    @Test
    public void testExistsPatientReturnTrue() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d1, p3);
        boolean actual = this.vaccOps.exist(p2);
        assertTrue(actual);
        boolean contains = this.vaccOps.getPatients().contains(p2);
        assertTrue(contains);
    }

    @Test
    public void testExistsPatientReturnFalse() {
        this.vaccOps.addDoctor(d1);
        boolean actual = this.vaccOps.exist(p1);
        assertFalse(actual);
        boolean contains = this.vaccOps.getPatients().contains(p1);
        assertFalse(contains);
    }

    @Test
    public void testRemoveDoctorReturnCorrect() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d2, p1);
        this.vaccOps.addPatient(d2, p2);
        Doctor doctor = this.vaccOps.removeDoctor(d2.getName());
        assertNotNull(doctor);
        assertEquals(doctor, d2);
        assertEquals(2, this.vaccOps.getDoctors().size());
        assertEquals(0, this.vaccOps.getPatients().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDoctorShouldThrowException() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d2, p1);
        this.vaccOps.addPatient(d2, p2);
        this.vaccOps.removeDoctor(d4.getName());
    }

    @Test
    public void testChangeDoctorReturnCorrect() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d3, p4);
        this.vaccOps.changeDoctor(d1, d2, p2);
        Assert.assertEquals(4, this.vaccOps.getPatients().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeDoctorShouldThrowExceptionIfFromDoctorDoesNotExist() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d3, p4);
        this.vaccOps.changeDoctor(d4, d2, p2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeDoctorShouldThrowExceptionIfToDoctorDoesNotExist() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d3, p4);
        this.vaccOps.changeDoctor(d1, d4, p2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeDoctorShouldThrowExceptionIfPatientTotallyDoesNotExist() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d3, p4);
        this.vaccOps.changeDoctor(d1, d4, p5);
    }

    @Test
    public void testGetDoctorByPopularityOnEmptyStorage() {
        Collection<Doctor> doctors = this.vaccOps.getDoctorsByPopularity(5);
        assertEquals(0, doctors.size());
    }

    @Test
    public void testGetDoctorByPopularityReturnCorrect() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d5);
        this.vaccOps.addDoctor(d6);
        this.vaccOps.addDoctor(d7);
        this.vaccOps.addDoctor(d8);

        Collection<Doctor> doctors = this.vaccOps.getDoctorsByPopularity(4);
        assertEquals(2, doctors.size());

        String[] expected = {"e", "f"};
        int i = 0;
        for (Doctor doctor : doctors) {
            assertEquals(doctor.getName(), expected[i++]);
        }
    }

    @Test
    public void testGetPatientByTownOnEmptyStorage() {
        Collection<Patient> patients = this.vaccOps.getPatientsByTown("a");
        assertEquals(0, patients.size());
    }

    @Test
    public void testGetPatientsByTownReturnCorrect() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d2, p1);
        this.vaccOps.addPatient(d2, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d1, p4);
        this.vaccOps.addPatient(d2, p5);
        this.vaccOps.addPatient(d3, p6);
        this.vaccOps.addPatient(d2, p7);
        this.vaccOps.addPatient(d1, p8);
        this.vaccOps.addPatient(d3, p9);
        this.vaccOps.addPatient(d2, p10);

        Collection<Patient> patients = this.vaccOps.getPatientsByTown("b");
        assertEquals(1, patients.size());
        String[] expected = {"b"};
        int i = 0;
        for (Patient patient : patients) {
            assertEquals(patient.getTown(), expected[i++]);
        }

        this.vaccOps.removeDoctor(d2.getName());
        patients = this.vaccOps.getPatientsByTown("b");
        assertEquals(0, patients.size());
    }

    @Test
    public void testGetPatientInAgeRangeOnEmptyStorage() {
        Collection<Patient> patients = this.vaccOps.getPatientsInAgeRange(1, 50);
        assertEquals(0, patients.size());
    }

    @Test
    public void testGetPatientsInAgeRangeReturnCorrect() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d2, p1);
        this.vaccOps.addPatient(d2, p2);
        this.vaccOps.addPatient(d3, p3);
        this.vaccOps.addPatient(d1, p4);
        this.vaccOps.addPatient(d2, p5);
        this.vaccOps.addPatient(d3, p6);
        this.vaccOps.addPatient(d2, p7);
        this.vaccOps.addPatient(d1, p8);
        this.vaccOps.addPatient(d3, p9);
        this.vaccOps.addPatient(d2, p10);

        Collection<Patient> patients = this.vaccOps.getPatientsInAgeRange(1, 50);
        assertEquals(10, patients.size());
        patients = this.vaccOps.getPatientsInAgeRange(11, 50);
        assertEquals(1, patients.size());
        String[] expected = {"i"};
        int i = 0;
        for (Patient patient : patients) {
            assertEquals(patient.getName(), expected[i++]);
        }
    }

    @Test
    public void testGetDoctorsSortedByPatientsCountDescAndNameAscReturnCorrect() {
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d1, p3);
        this.vaccOps.addPatient(d1, p4);
        this.vaccOps.addPatient(d1, p5);
        this.vaccOps.addPatient(d2, p6);
        this.vaccOps.addPatient(d2, p7);
        this.vaccOps.addPatient(d2, p8);
        this.vaccOps.addPatient(d2, p9);
        this.vaccOps.addPatient(d2, p10);
        this.vaccOps.addPatient(d4, p11);

        String[] expected = {"a", "b", "d", "c"};
        int i = 0;

        Collection<Doctor> sortedDoctors = this.vaccOps.getDoctorsSortedByPatientsCountDescAndNameAsc();
        assertEquals(expected.length, sortedDoctors.size());
        for (Doctor doctor : sortedDoctors) {
            assertEquals(expected[i++], doctor.getName());
        }
    }

    @Test
    public void testGetDoctorsSortedByPatientsCountDescAndNameAscReturnCorrect_v2() {
        this.vaccOps.addDoctor(d8);
        this.vaccOps.addDoctor(d7);
        this.vaccOps.addDoctor(d6);
        this.vaccOps.addDoctor(d5);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d1);

        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h"};
        int i = 0;

        Collection<Doctor> sortedDoctors = this.vaccOps.getDoctorsSortedByPatientsCountDescAndNameAsc();
        assertEquals(expected.length, sortedDoctors.size());
        for (Doctor doctor : sortedDoctors) {
            assertEquals(expected[i++], doctor.getName());
        }
    }

    @Test
    public void testGetDoctorsSortedByPatientsCountDescAndNameAscReturnCorrect_v3() {
        this.vaccOps.addDoctor(d8);
        this.vaccOps.addDoctor(d7);
        this.vaccOps.addDoctor(d6);
        this.vaccOps.addDoctor(d5);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d1);

        this.vaccOps.addPatient(d2, p1);
        this.vaccOps.addPatient(d2, p2);
        this.vaccOps.addPatient(d2, p3);
        this.vaccOps.addPatient(d3, p4);
        this.vaccOps.addPatient(d3, p5);
        this.vaccOps.addPatient(d3, p6);
        this.vaccOps.addPatient(d6, p7);

        String[] expected = {"b", "c", "f", "a", "d", "e", "g", "h"};
        int i = 0;

        Collection<Doctor> sortedDoctors = this.vaccOps.getDoctorsSortedByPatientsCountDescAndNameAsc();
        assertEquals(expected.length, sortedDoctors.size());
        for (Doctor doctor : sortedDoctors) {
            assertEquals(expected[i++], doctor.getName());
        }
    }

    @Test
    public void testGetPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAgeReturnCorrectOnEmptyPatientStorage() {
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d3);

        Collection<Patient> sortedPatients = this.vaccOps.getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge();
        assertEquals(0, sortedPatients.size());
        assertEquals(0, this.vaccOps.getPatients().size());
    }

    @Test
    public void testGetPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAgeReturnCorrect() {
        this.vaccOps.addDoctor(d2);
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addDoctor(d4);
        this.vaccOps.addDoctor(d3);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d1, p3);
        this.vaccOps.addPatient(d1, p4);
        this.vaccOps.addPatient(d1, p5);
        this.vaccOps.addPatient(d2, p6);
        this.vaccOps.addPatient(d2, p7);
        this.vaccOps.addPatient(d2, p8);
        this.vaccOps.addPatient(d2, p9);
        this.vaccOps.addPatient(d2, p10);
        this.vaccOps.addPatient(d4, p11);

        String[] expected = {"h", "g", "i", "d", "e", "f", "j", "a", "b", "c", "k"};

        int i = 0;

        Collection<Patient> sortedPatients = this.vaccOps.getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge();
        assertEquals(expected.length, sortedPatients.size());
        for (Patient patient : sortedPatients) {
            assertEquals(expected[i++], patient.getName());
        }
    }

    @Test
    public void testGetPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAgeReturnCorrect_v2() {
        this.vaccOps.addDoctor(d1);
        this.vaccOps.addPatient(d1, p1);
        this.vaccOps.addPatient(d1, p2);
        this.vaccOps.addPatient(d1, p3);
        this.vaccOps.addPatient(d1, p4);
        this.vaccOps.addPatient(d1, p5);
        this.vaccOps.addPatient(d1, p6);
        this.vaccOps.addPatient(d1, p7);
        this.vaccOps.addPatient(d1, p8);
        this.vaccOps.addPatient(d1, p9);
        this.vaccOps.addPatient(d1, p10);
        this.vaccOps.addPatient(d1, p11);

        String[] expected = {"h", "g", "i", "d", "e", "f", "j", "a", "b", "k", "c"};

        int i = 0;

        Collection<Patient> sortedPatients = this.vaccOps.getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge();
        assertEquals(expected.length, sortedPatients.size());
        for (Patient patient : sortedPatients) {
            assertEquals(expected[i++], patient.getName());
        }
    }

}
