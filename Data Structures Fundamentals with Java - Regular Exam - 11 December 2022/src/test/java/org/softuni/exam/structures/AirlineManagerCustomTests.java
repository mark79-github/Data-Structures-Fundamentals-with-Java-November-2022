package org.softuni.exam.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class AirlineManagerCustomTests {

    private AirlinesManager airlinesManager;

    private Airline getRandomAirline() {
        return new Airline(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Math.random() * 1_000_000_000);
    }

    private Flight getRandomFlight() {
        return new Flight(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                ((Math.random() * 1_000) < 500));
    }

    @Before
    public void setup() {
        this.airlinesManager = new AirlinesManagerImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFlightShouldThrowExceptionWhenAirDownNotExists() {
        this.airlinesManager.addAirline(getRandomAirline());
        this.airlinesManager.addFlight(getRandomAirline(), getRandomFlight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteAirlineShouldThrowExceptionWhenAirlineDoesNotExists() {
        this.airlinesManager.addAirline(getRandomAirline());
        this.airlinesManager.deleteAirline(getRandomAirline());
    }

    @Test
    public void testDeleteAirlineShouldWorkCorrectly() {
        Airline firstAirline = getRandomAirline();
        Airline secondAirline = getRandomAirline();
        Airline thirdAirline = getRandomAirline();
        this.airlinesManager.addAirline(firstAirline);
        this.airlinesManager.addAirline(secondAirline);
        this.airlinesManager.addAirline(thirdAirline);

        Flight firstFlight = getRandomFlight();
        Flight secondFlight = getRandomFlight();
        Flight thirdFlight = getRandomFlight();
        this.airlinesManager.addFlight(secondAirline, firstFlight);
        this.airlinesManager.addFlight(secondAirline, secondFlight);
        this.airlinesManager.addFlight(secondAirline, thirdFlight);

        this.airlinesManager.deleteAirline(thirdAirline);
        Assert.assertFalse(this.airlinesManager.contains(thirdAirline));

        Iterable<Flight> flightIterable = this.airlinesManager.getAllFlights();

        Set<Flight> set = StreamSupport
                .stream(flightIterable.spliterator(), false)
                .collect(Collectors.toSet());
        Assert.assertEquals(3, set.size());

        this.airlinesManager.deleteAirline(secondAirline);
        Assert.assertFalse(this.airlinesManager.contains(secondAirline));
        Assert.assertFalse(this.airlinesManager.contains(secondFlight));

        set = StreamSupport
                .stream(flightIterable.spliterator(), false)
                .collect(Collectors.toSet());
        Assert.assertEquals(0, set.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerformFlightShouldThrowExceptionWhenAirlineDoesNotExists() {
        Airline airline = getRandomAirline();
        Flight flight = getRandomFlight();
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight);
        this.airlinesManager.performFlight(getRandomAirline(), flight);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPerformFlightShouldThrowExceptionWhenFlightDoesNotExists() {
        Airline airline = getRandomAirline();
        Flight flight = getRandomFlight();
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight);
        this.airlinesManager.performFlight(airline, getRandomFlight());
    }

    @Test
    public void testPerformFlightShouldReturnFlightCorrectly() {
        Airline airline = getRandomAirline();
        Flight flight = getRandomFlight();
        flight.setCompleted(false);
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight);

        Flight performFlight = this.airlinesManager.performFlight(airline, flight);

        Assert.assertTrue(performFlight.isCompleted());
        Assert.assertTrue(this.airlinesManager.contains(performFlight));
    }

    @Test
    public void testGetCompletedFlightsShouldReturnCorrectly() {
        Airline firstAirline = getRandomAirline();
        Airline secondAirline = getRandomAirline();
        this.airlinesManager.addAirline(firstAirline);
        this.airlinesManager.addAirline(secondAirline);

        int flightsCount = 20;

        for (int i = 0; i < flightsCount; i++) {
            Flight randomFlight = getRandomFlight();
            if (i % 2 == 0) {
                randomFlight.setCompleted(false);
                this.airlinesManager.addFlight(firstAirline, randomFlight);
            } else {
                randomFlight.setCompleted(true);
                this.airlinesManager.addFlight(secondAirline, randomFlight);
            }
        }

        Iterable<Flight> flightIterable = this.airlinesManager.getCompletedFlights();
        Set<Flight> set = StreamSupport
                .stream(flightIterable.spliterator(), false)
                .collect(Collectors.toSet());

        Assert.assertEquals(flightsCount / 2, set.size());
    }

    @Test
    public void testGetAllFlightsShouldReturnCorrectly() {
        Airline firstAirline = getRandomAirline();
        Airline secondAirline = getRandomAirline();
        this.airlinesManager.addAirline(firstAirline);
        this.airlinesManager.addAirline(secondAirline);

        int flightsCount = 20;

        for (int i = 0; i < flightsCount; i++) {
            Flight randomFlight = getRandomFlight();
            randomFlight.setId(String.valueOf(i));
            if (i % 2 == 0) {
                this.airlinesManager.addFlight(firstAirline, randomFlight);
            } else {
                this.airlinesManager.addFlight(secondAirline, randomFlight);
            }
        }

        Iterable<Flight> flightIterable = this.airlinesManager.getAllFlights();
        Set<Flight> set = StreamSupport
                .stream(flightIterable.spliterator(), false)
                .collect(Collectors.toSet());

        Assert.assertEquals(flightsCount, set.size());
    }

    @Test
    public void testGetFlightsOrderedByNumberThenByCompletionShouldReturnEmptyCollection() {
        Iterable<Flight> flightIterable = this.airlinesManager.getFlightsOrderedByNumberThenByCompletion();
        Set<Flight> set = StreamSupport
                .stream(flightIterable.spliterator(), false)
                .collect(Collectors.toSet());

        assertEquals(0, set.size());
    }

    @Test
    public void testGetFlightsOrderedByNumberThenByCompletionShouldReturnSortedCorrectlyCollection() {
        List<Flight> flights = new ArrayList<>();
        int collectionSize = 20;
        for (int i = 0; i < collectionSize; i++) {
            Flight flight = new Flight(String.valueOf(i), Integer.toString(i + 100), null, null, false);
            if (i % 2 == 0) {
                flight.setCompleted(true);
            }
            flights.add(flight);
        }

        Airline airline = getRandomAirline();
        this.airlinesManager.addAirline(airline);

        for (Flight flight : flights) {
            this.airlinesManager.addFlight(airline, flight);
        }

        flights.sort((o1, o2) -> {
            if (o1.isCompleted() == o2.isCompleted()) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
            return Boolean.compare(o1.isCompleted(), o2.isCompleted());
        });

        Iterable<Flight> flightIterable = this.airlinesManager.getFlightsOrderedByNumberThenByCompletion();
        collectionSize = 0;
        for (Flight flight : flightIterable) {
            Flight f = flights.get(collectionSize++);
            Assert.assertEquals(f.isCompleted(), flight.isCompleted());
            Assert.assertEquals(f.getNumber(), flight.getNumber());
        }
    }


    @Test
    public void testGetAirlinesOrderedByRatingThenByCountOfFlightsThenByNameShouldReturnEmptyCollection() {
        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesOrderedByRatingThenByCountOfFlightsThenByName();
        Set<Airline> set = StreamSupport
                .stream(airlineIterable.spliterator(), false)
                .collect(Collectors.toSet());

        assertEquals(0, set.size());
    }

    @Test
    public void testGetAirlinesOrderedByRatingThenByCountOfFlightsThenByNameShouldReturnSortedCorrectlyCollection() {
        Airline airline_1 = new Airline(UUID.randomUUID().toString(), "101", 1.5);
        Airline airline_2 = new Airline(UUID.randomUUID().toString(), "102", 1.5);
        Airline airline_3 = new Airline(UUID.randomUUID().toString(), "103", 1.8);
        Airline airline_4 = new Airline(UUID.randomUUID().toString(), "104", 1.1);
        Airline airline_5 = new Airline(UUID.randomUUID().toString(), "105", 1.2);
        Airline airline_6 = new Airline(UUID.randomUUID().toString(), "106", 1.2);

        Flight flight_1 = getRandomFlight();

        this.airlinesManager.addAirline(airline_6);
        this.airlinesManager.addAirline(airline_5);
        this.airlinesManager.addAirline(airline_4);
        this.airlinesManager.addAirline(airline_3);
        this.airlinesManager.addAirline(airline_2);
        this.airlinesManager.addAirline(airline_1);

        this.airlinesManager.addFlight(airline_2, flight_1);

        String[] expected = new String[]{"103", "102", "101", "105", "106", "104"};

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesOrderedByRatingThenByCountOfFlightsThenByName();
        int index = 0;
        for (Airline airline : airlineIterable) {
            Assert.assertEquals(expected[index++], airline.getName());
        }
    }

    @Test
    public void testGetAirlinesWithFlightsFromOriginToDestinationShouldReturnEmptyCollection() {
        Airline randomAirline = getRandomAirline();
        Flight flight_1 = getRandomFlight();
        Flight flight_2 = getRandomFlight();
        Flight flight_3 = getRandomFlight();

        flight_1.setOrigin("origin");
        flight_1.setCompleted(false);
        flight_2.setDestination("destination");
        flight_2.setCompleted(false);
        flight_3.setOrigin("origin");
        flight_3.setDestination("destination");
        flight_3.setCompleted(true);

        this.airlinesManager.addAirline(randomAirline);
        this.airlinesManager.addFlight(randomAirline, flight_1);
        this.airlinesManager.addFlight(randomAirline, flight_2);
        this.airlinesManager.addFlight(randomAirline, flight_3);

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesWithFlightsFromOriginToDestination("origin", "destination");
        Set<Airline> set = StreamSupport
                .stream(airlineIterable.spliterator(), false)
                .collect(Collectors.toSet());

        assertEquals(0, set.size());
    }

    @Test
    public void testGetAirlinesWithFlightsFromOriginToDestinationShouldReturnSortedCorrectlyCollection() {
        Airline airline_1 = getRandomAirline();
        Airline airline_2 = getRandomAirline();
        Airline airline_3 = getRandomAirline();
        Flight flight_1 = getRandomFlight();
        Flight flight_2 = getRandomFlight();
        Flight flight_3 = getRandomFlight();
        Flight flight_4 = getRandomFlight();
        Flight flight_5 = getRandomFlight();

        flight_1.setOrigin("origin");
        flight_1.setCompleted(false);
        flight_2.setDestination("destination");
        flight_2.setCompleted(false);
        flight_3.setOrigin("origin");
        flight_3.setDestination("destination");
        flight_3.setCompleted(false);
        flight_4.setOrigin("origin");
        flight_4.setDestination("destination");
        flight_4.setCompleted(false);
        flight_5.setOrigin("origin");
        flight_5.setDestination("destination");
        flight_5.setCompleted(true);

        this.airlinesManager.addAirline(airline_1);
        this.airlinesManager.addAirline(airline_2);
        this.airlinesManager.addAirline(airline_3);
        this.airlinesManager.addFlight(airline_2, flight_1);
        this.airlinesManager.addFlight(airline_2, flight_2);
        this.airlinesManager.addFlight(airline_2, flight_3);
        this.airlinesManager.addFlight(airline_2, flight_4);

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesWithFlightsFromOriginToDestination("origin", "destination");
        int collectionSize = 0;
        for (Airline airline : airlineIterable) {
            collectionSize++;
            Assert.assertEquals(airline_2, airline);
        }

        Assert.assertEquals(1, collectionSize);
    }

}
