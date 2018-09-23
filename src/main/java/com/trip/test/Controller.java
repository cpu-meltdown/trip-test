package com.trip.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.apache.log4j.Logger;

import com.trip.test.model.Driver;
import com.trip.test.model.Trip;
import com.trip.test.util.InputValidator;

/**
 * This class handles input/output from terminal and keeps track of drivers and their trips
 * 
 * @author nabil baalbaki
 *
 */
public class Controller {

    private final static Logger logger = Logger.getLogger( Controller.class );

    private Map<String, Driver> nameToDriver = new HashMap<>();

    public static void main( String args[] ) throws ValidationException {

        Controller controller = new Controller();

        // if args is empty, that means user hasn't provided a file name
        if ( args.length == 0 ) {
            logger.error( "No file name was provided from the user." );
            throw new ValidationException( "Proper Usage is: java -jar trip-test.jar <file-name>" );
        }

        // the filename to read from
        File file = new File( System.getProperty( "user.dir" ) + "/" + args[0] );

        try {
            BufferedReader fileBr = new BufferedReader( new FileReader( file ) );
            controller.readFile( fileBr );
        } catch ( FileNotFoundException e ) {
            logger.error( "Provided file: " + file + " was not found." );
            System.out.println( e.getMessage() );
        } catch ( IOException e ) {
            logger.error( "Error while trying to read from input file." );
            System.out.println( e.getMessage() );
        }

        // out put to a file named "report.txt"
        controller.printReport();

        System.out.println( "Success" );
    }

    /**
     * Reads the commands from the provided file
     * 
     * @param fileBr the file to read from
     * @throws IOException exception reading the file
     * @throws ValidationException validation exception
     */
    protected void readFile( BufferedReader fileBr ) throws IOException, ValidationException {
        try {
            String line = null;
            while ( ( line = fileBr.readLine() ) != null ) {
                String[] commandValues = line.split( " " );
                String command = commandValues[0];

                if ( !InputValidator.isValidCommand( command ) ) {
                    logger.error( "Wrong command value was provided: " + command );
                    throw new ValidationException( "Please enter a valid command: Driver or Trip" );
                }

                switch ( command ) {
                    case Driver.FIELD_NAME:
                        InputValidator.validateDriverInput( commandValues );
                        Driver driver = new Driver( commandValues[1] );
                        nameToDriver.put( commandValues[1], driver );
                        break;
                    case Trip.FIELD_NAME:
                        InputValidator.validateTripInput( commandValues );
                        Trip trip = new Trip( commandValues[2], commandValues[3],
                            Double.parseDouble( commandValues[4] ) );
                        linkTripToDriver( trip, commandValues[1] );
                        break;
                    default:
                        break;
                }
            }
        } catch ( IOException e ) {
            throw new IOException(
                "Error while trying to read from input file. Please make sure you've provided the correct file name." );
        } catch ( ValidationException e ) {
            throw e;
        }
    }

    /**
     * Writes the results to a "report.txt" file in the project directory. The report should be sorted by miles traveled
     * in descending order.
     */
    private void printReport() {
        List<Driver> drivers = new ArrayList<>( nameToDriver.values() );

        drivers.sort( ( Driver d1, Driver d2 ) -> d2.getTotalMiles() - d1.getTotalMiles() );

        List<String> results = new ArrayList<>();

        drivers.forEach( driver -> {
            results.add( driver.getTotalMiles() > 0
                ? String.format( "%s: %d miles @ %d mph", driver.getName(), driver.getTotalMiles(),
                    driver.getAverageSpeed() )
                : String.format( "%s: %d miles", driver.getName(), driver.getTotalMiles() ) );
        } );

        try {
            Path file = Paths.get( "report.txt" );
            Files.write( file, results, Charset.forName( "UTF-8" ) );
        } catch ( IOException e ) {
            logger.error( "Error writing to output report file" );
            System.out.println( "Error writing to output report file" );
        }
    }

    /**
     * Adds the provided trip to nameToDriver map
     * 
     * @param trip trip to link to driver
     * @param driverName driver name that trip belongs to
     */
    protected void linkTripToDriver( Trip trip, String driverName ) {
        if ( trip == null || driverName == null ) {
            return;
        }

        Driver driver = null;
        if ( nameToDriver.containsKey( driverName ) ) {
            driver = nameToDriver.get( driverName );
            driver.addTrip( trip );
            nameToDriver.put( driverName, driver );
        } else {
            driver = new Driver( driverName );
            driver.addTrip( trip );
            nameToDriver.put( driverName, driver );
        }
    }

    /**
     * @return nameToDriver map
     */
    public Map<String, Driver> getNameToDriverMap() {
        return nameToDriver;
    }
}
