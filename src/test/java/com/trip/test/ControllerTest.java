package com.trip.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.ValidationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ControllerTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

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
		String fileName = "test.txt";

		try {
			BufferedReader fileBr = new BufferedReader(new FileReader(fileName));
			Controller.readFile(fileBr);
		} catch (IOException e) {
			assertEquals(
					"Error while trying to read from input file. Please make sure you've provided the correct file name.",
					e.getMessage());
		}

		File testFile = testFolder.newFile(fileName);

		BufferedWriter bw = new BufferedWriter(new FileWriter(testFile));
		bw.write("This is a test");
		bw.close();

		try {
			BufferedReader fileBr = new BufferedReader(new FileReader(fileName));
			Controller.readFile(fileBr);
		} catch (IOException e) {
			assertEquals("Please enter a valid command: Driver or Trip", e.getMessage());
		}
	}
}
