package com.trip.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;
import com.trip.test.util.InputValidator;

public class Controller {

	private static Map<String, Driver> nameToDriver = new HashMap<>();

	public static void main(String args[]) {
		// if args is empty, that means user hasn't provided a file name
		if (args.length == 0) {
			System.out.println("Proper Usage is: java -jar trip-test.jar <file-name>");
			System.exit(0);
		}

		String fileName = System.getProperty("user.dir") + "/" + args[0];

		try {
			Files.lines(Paths.get(fileName)).forEach(line -> {
				try {
					String[] commandValues = line.split(" ");
					String command = commandValues[0];

					if (!InputValidator.isValidCommand(command)) {
						throw new ValidationException("Please enter a valid command: Driver or Trip");
					}

					switch (command) {
					case Driver.FIELD_NAME:
						InputValidator.validateDriverInput(commandValues);
						Driver driver = new Driver(commandValues[1]);
						nameToDriver.put(commandValues[1], driver);
						break;
					case Trip.FIELD_NAME:
						InputValidator.validateTripInput(commandValues);
						Trip trip = new Trip(commandValues[2], commandValues[3], Double.parseDouble(commandValues[4]));
						linkTripToDriver(trip, commandValues[1]);
						break;
					default:
						break;
					}
				} catch (ValidationException e) {
					System.out.println(e.getMessage());
				}
			});
		} catch (IOException e) {
			System.out.println(e.getMessage());
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
