package com.trip.test.util;

import java.util.regex.Pattern;

import javax.xml.bind.ValidationException;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;

public class InputValidator {

	private static final String TIME12HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	private static final Pattern TIME_PATTERN = Pattern.compile(TIME12HOURS_PATTERN);

	public static boolean isValidCommand(String command) {
		return command.equals(Driver.FIELD_NAME) || command.equals(Trip.FIELD_NAME);
	}

	public static void validateDriverInput(String[] commandValues) throws ValidationException {
		if (commandValues.length != 2) {
			throw new ValidationException("Please enter proper Driver information: Driver <name>");
		}

	}

	public static void validateTripInput(String[] commandValues) throws ValidationException {
		// validate right length
		if (commandValues.length != 5) {
			throw new ValidationException("Please enter proper Trip information");
		}

		// validate start and end time
		boolean isValidDate = TIME_PATTERN.matcher(commandValues[3]).matches()
				&& TIME_PATTERN.matcher(commandValues[2]).matches();

		if (!isValidDate) {
			throw new ValidationException("Please provide valid date format: HH:mm");
		}
	}
}
