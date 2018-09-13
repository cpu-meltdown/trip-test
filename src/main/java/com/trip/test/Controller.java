package com.trip.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;

public class Controller {

	private static Map<String, Driver> nameToDriver = new HashMap<>();

	public static void main(String args[]) {
		// if arg sis empty, that means user hasn't provided a file name
		if (args.length == 0) {
			System.out.println("Proper Usage is: java -jar trip-test.jar <file-name>");
			System.exit(0);
		}

		String fileName = System.getProperty("user.dir") + "/" + args[0];

		try {
			Files.lines(Paths.get(fileName)).forEach(line -> {
				String[] commandValues = line.split(" ");
				switch (commandValues[0]) {
				case "Driver":
					Driver driver = new Driver(commandValues[1]);
					nameToDriver.put(commandValues[1], driver);
					break;
				case "Trip":
					Trip trip = new Trip(commandValues[2], commandValues[3], Double.parseDouble(commandValues[4]));
					linkTripToDriver(trip, commandValues[1]);
					break;
				default:
					break;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		printReport();
	}

	private static void printReport() {
		List<Driver> drivers = new ArrayList<>(nameToDriver.values());

		drivers.sort((Driver d1, Driver d2) -> d2.getTotalMiles() - d1.getTotalMiles());

		drivers.forEach(driver -> {
			System.out.println(driver.getTotalMiles() > 0
					? String.format("%s: %d miles @ %d mph", driver.getName(), driver.getTotalMiles(),
							driver.getAverageSpeed())
					: String.format("%s: %d miles", driver.getName(), driver.getTotalMiles()));
		});
	}

	private static void linkTripToDriver(Trip trip, String driverName) {
		Driver driver = null;
		if (nameToDriver.containsKey(driverName)) {
			driver = nameToDriver.get(driverName);
			driver.addTrip(trip);
			nameToDriver.put(driverName, driver);
		} else {
			driver = new Driver(driverName);
			driver.addTrip(trip);
			nameToDriver.put(driverName, driver);
		}
	}
}
