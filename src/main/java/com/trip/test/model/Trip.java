package com.trip.test.model;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class Trip {

	private String startTime;

	private String endTime;

	private double milesDriven;

	public Trip(String startTime, String endTime, double milesDriven) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.milesDriven = milesDriven;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public double getMilesDriven() {
		return milesDriven;
	}

	public void setMilesDriven(double milesDriven) {
		this.milesDriven = milesDriven;
	}

	public double getTripDurationInHours() {
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.split(":")[0]));
		startCal.set(Calendar.MINUTE, Integer.parseInt(startTime.split(":")[1]));
		startCal.set(Calendar.SECOND, 0);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTime.split(":")[0]));
		endCal.set(Calendar.MINUTE, Integer.parseInt(endTime.split(":")[1]));
		endCal.set(Calendar.SECOND, 0);

		long minutes = ChronoUnit.MINUTES.between(startCal.toInstant(), endCal.toInstant());
		
		return minutes / 60.0;
	}

	public boolean isOutOfSpeedRange() {
		double speedAvg = this.milesDriven / getTripDurationInHours();
		return speedAvg < 5 || speedAvg > 100;
	}

}