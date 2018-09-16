package com.trip.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import javax.xml.bind.ValidationException;

import org.junit.Before;
import org.junit.Test;

public class ControllerTest {

	private static Controller controller;

	@Before
	public void init() {
		controller = new Controller();
	}

	@Test
	public void testMainMethodNegative() {
		String[] args = new String[0];
		try {
			Controller.main(args);
		} catch (ValidationException e) {
			assertEquals("Proper Usage is: java -jar trip-test.jar <file-name>", e.getMessage());
		}
	}

	@Test
	public void testMainMethodPositive() throws ValidationException {
		String[] args = new String[] { "input-test.txt" };

		Controller.main(args);
	}

	@Test
	public void testReadFileNegative() throws IOException, ValidationException {
		try {
			BufferedReader fileBr = mock(BufferedReader.class);
			when(fileBr.readLine()).thenReturn("This is a test");

			controller.readFile(fileBr);
		} catch (ValidationException e) {
			assertEquals("Please enter a valid command: Driver or Trip", e.getMessage());
		}

		try {
			BufferedReader fileBr = mock(BufferedReader.class);
			when(fileBr.readLine()).thenReturn("Driver");
			controller.readFile(fileBr);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Driver information: Driver <name>", e.getMessage());
		}

		try {
			BufferedReader fileBr = mock(BufferedReader.class);
			when(fileBr.readLine()).thenReturn("Trip hello world");
			controller.readFile(fileBr);
		} catch (ValidationException e) {
			assertEquals("Please enter proper Trip information", e.getMessage());
		}
	}

	@Test
	public void testReadFilePositive() throws IOException, ValidationException {
		Controller controller = new Controller();
		BufferedReader fileBr = mock(BufferedReader.class);
		when(fileBr.readLine()).thenReturn("Driver Test1").thenReturn("Trip Test1 10:13 10:30 5.2")
				.thenReturn("Driver Test2").thenReturn(null);

		controller.readFile(fileBr);
		assertEquals(2, controller.getNameToDriverMap().size());
		assertEquals(5, controller.getNameToDriverMap().get("Test1").getTotalMiles());
	}
}
