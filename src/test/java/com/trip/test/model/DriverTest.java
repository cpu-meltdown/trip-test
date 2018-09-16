package com.trip.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DriverTest {

	@Test
	public void testAddTrip() {
		// valid trip
		Driver driver = new Driver("Nabil");
		Trip trip = new Trip("17:10", "17:30", 20.0);

		driver.addTrip(trip);

		assertEquals(60, driver.getAverageSpeed());
		
		// add a trip that is out of range should have same result as above
		trip = new Trip("17:10", "17:12", 20.0);
		driver.addTrip(trip);

		assertEquals(60, driver.getAverageSpeed());
		assertEquals(1, driver.getTriprs().size());
		
		// add valid trip
		trip = new Trip("3:10", "5:00", 100.0);
		driver.addTrip(trip);
		
		assertEquals(55, driver.getAverageSpeed());
		assertEquals(2, driver.getTriprs().size());
	}
}
