package com.trip.test.model;

import java.util.ArrayList;
import java.util.List;

public class Driver{

	private String name;

	private int totalMiles;

	private double averageSpeed;

	private double totalTripsTime = 0;
	
	private List<Trip> trips;

	public Driver(String name){
		this(name, 0, 0);
	}

	public Driver(String name, int totalMiles, double averageSpeed){
		this.name = name;
		this.totalMiles = totalMiles;
		this.averageSpeed = averageSpeed;
		this.trips = new ArrayList<Trip>();
	}

	public void setName(String name){
		this.name = name;
	}

	public void setAverageSpeed(double averageSpeed){
		this.averageSpeed = averageSpeed;
	}

	public void setTotalMiles(int totalMiles){
		this.totalMiles = totalMiles;
	}

	public String getName(){
		return name;
	}

	public int getAverageSpeed(){
		return (int) Math.round(totalMiles / totalTripsTime);
	}

	public int getTotalMiles(){
		return totalMiles;
	}

	public void addTrip(Trip trip){
		
		if (trip.isOutOfSpeedRange()){
			return;
		}
		double tripInHrs = trip.getTripDurationInHours();
		
		this.totalTripsTime += tripInHrs;
		
		trips.add(trip);

		this.totalMiles += Math.rint(trip.getMilesDriven());
	}
}
