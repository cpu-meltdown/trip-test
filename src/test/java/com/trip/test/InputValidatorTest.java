package com.trip.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.bind.ValidationException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;
import com.trip.test.util.InputValidator;

public class InputValidatorTest {

	static InputValidator inputValidator;

	@BeforeClass
	public static void init() {
		inputValidator = new InputValidator();
	}

	@Test
	public void testIsValidCommand() {
		assertTrue(inputValidator.isValidCommand(Driver.FIELD_NAME));
		assertTrue(inputValidator.isValidCommand(Trip.FIELD_NAME));
		assertFalse(inputValidator.isValidCommand("test"));
	}

	@Test
	public void testValidateTripInputPositive() throws ValidationException {
		String[] validTrip = { "Trip", "Nabil", "13:20", "00:20", "13.2" };
		inputValidator.validateTripInput(validTrip);
	}

	@Test
	public void testValidateTripInputInvalidTripInput() {
		String[] invalidTimeTrip = { "Trip", "Nabil", "13:2", "25:2", "13.2" };
		try {
			inputValidator.validateTripInput(invalidTimeTrip);
			fail();
		} catch (ValidationException e) {
			assertEquals("Please provide valid date format: HH:mm", e.getMessage());
		}

		String[] invalidLengthTrip = { "Trip", "13.2", "25:2", "13.2" };
		try {
			inputValidator.validateTripInput(invalidLengthTrip);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Trip information", e.getMessage());
		}
	}

	@Test
	public void testValidateDriverInputNegative() {
		String[] invalidDriverInfo = { "Test" };
		try {
			inputValidator.validateDriverInput(invalidDriverInfo);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Driver information: Driver <name>", e.getMessage());
		}
	}

	@Test
	public void testValidateDriverInputPostivie() throws ValidationException {
		String[] invalidDriverInfo = { "Driver", "Nabil" };
		inputValidator.validateDriverInput(invalidDriverInfo);
	}
}
