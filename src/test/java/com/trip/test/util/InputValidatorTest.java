package com.trip.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.bind.ValidationException;

import org.junit.Test;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;
import com.trip.test.util.InputValidator;

public class InputValidatorTest {

	@Test
	public void testIsValidCommand() {
		assertTrue(InputValidator.isValidCommand(Driver.FIELD_NAME));
		assertTrue(InputValidator.isValidCommand(Trip.FIELD_NAME));
		assertFalse(InputValidator.isValidCommand("test"));
	}

	@Test
	public void testValidateTripInputPositive() throws ValidationException {
		String[] validTrip = { "Trip", "Nabil", "13:20", "00:20", "13.2" };
		InputValidator.validateTripInput(validTrip);
	}

	@Test
	public void testValidateTripInputInvalidTripInput() {
		String[] invalidTimeTrip = { "Trip", "Nabil", "13:2", "25:2", "13.2" };
		try {
			InputValidator.validateTripInput(invalidTimeTrip);
			fail();
		} catch (ValidationException e) {
			assertEquals("Please provide valid date format: HH:mm", e.getMessage());
		}

		String[] invalidLengthTrip = { "Trip", "13.2", "25:2", "13.2" };
		try {
			InputValidator.validateTripInput(invalidLengthTrip);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Trip information", e.getMessage());
		}
	}

	@Test
	public void testValidateDriverInputNegative() {
		String[] invalidDriverInfo = { "Test" };
		try {
			InputValidator.validateDriverInput(invalidDriverInfo);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Driver information: Driver <name>", e.getMessage());
		}
	}

	@Test
	public void testValidateDriverInputPostivie() throws ValidationException {
		String[] invalidDriverInfo = { "Driver", "Nabil" };
		InputValidator.validateDriverInput(invalidDriverInfo);
	}
}
