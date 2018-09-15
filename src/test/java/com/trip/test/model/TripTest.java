package com.trip.test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TripTest {

	@Test
	public void testIsOutOfSpeedRange() {
		Trip trip = new Trip("17:13", "17:15", 20.0);
		assertTrue(trip.isOutOfSpeedRange());

		trip = new Trip("17:13", "17:33", 20.0);
		assertFalse(trip.isOutOfSpeedRange());
	}

	@Test
	public void testGetTripDurationInHours() {
		Trip trip = new Trip("17:00", "18:00", 20.4);
		
		assertEquals(1.0, trip.getTripDurationInHours(), 0.001);
	}
}
