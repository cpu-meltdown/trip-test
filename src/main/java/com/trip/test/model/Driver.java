package com.trip.test.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	public static final String FIELD_NAME = "Driver";

	private String name;

	private int totalMiles;

	private double totalTripsTime = 0;

	private List<Trip> trips;

	public Driver(String name) {
		this(name, 0, 0);
	}

	public Driver(String name, int totalMiles, double averageSpeed) {
		this.name = name;
		this.totalMiles = totalMiles;
		this.trips = new ArrayList<Trip>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTotalMiles(int totalMiles) {
		this.totalMiles = totalMiles;
	}

	public String getName() {
		return name;
	}

	public int getAverageSpeed() {
		return (int) Math.round(totalMiles / totalTripsTime);
	}

	public int getTotalMiles() {
		return totalMiles;
	}

	public List<Trip> getTriprs() {
		return trips;
	}

	/**
	 * Adds a trip to the Driver's list of trips and updates related fields
	 * accordingly
	 * 
	 * @param trip
	 *            to add to list of trips
	 */
	public void addTrip(Trip trip) {
		if (trip.isOutOfSpeedRange()) {
			return;
		}
		double tripInHrs = trip.getTripDurationInHours();

		this.totalTripsTime += tripInHrs;

		trips.add(trip);

		this.totalMiles += Math.rint(trip.getMilesDriven());
	}
}
