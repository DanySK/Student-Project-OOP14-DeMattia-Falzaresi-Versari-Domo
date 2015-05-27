package domo.educational.exceptions; 

import java.util.List;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is a generic class for Exceptions tests
 *
 */
public class ExceptionsTestClass {

	public static void main(String[] args) {

		/* Creation of a Flat 
		 * This Procedure require try - catch clause because the constructor 
		 * of this Object can throw exceptions
		 * With try - catch is possible to ignore an Exception (like in our first case)
		 */
		
		try {
			final Flat myFlat = new Flat("My Flat");
			myFlat.addRoom(null);
		} catch (CustomExceptions e) {
			System.out.println("Error catched in the flat " + e.toString());
		}
		
		/* Room Creation
		 * this Procedure doesn't require try - catch clause because has already 
		 * inserted in the constructor of the room element
		 */
		final Room myRoom = new Room("Living");
		
		/* Now there aren't check to verify if the sensor inserted in a room is null or not so the
		 * we can insert "null" as sensor
		 */
		myRoom.addSensor(null);
		final List<Sensor> myList = myRoom.getSensors();
		for (final Sensor sensor : myList) {
			/* But what happen if we try to access the methods of this sensor? */
			System.out.println(sensor.toString());
			/* A standard Exception (in this case Null Pointer Exception) is throw by the JVM */
		}
		
		/* This is what happen in a try catch finally clause
		 * Another type of exception is throw in this case (another custom exception)
		 */
		try {
			final Flat myFlat2 = new Flat(null);
			myFlat2.addRoom(new Room("BedRoom"));
		} catch (CustomExceptions e) {
			String str = " New Exception :\n" + e;
			RuntimeException e2 = new FlatException(str , args);
			throw e2;
		} finally {
			System.out.println("Finally Clause Reached!");
		}
	}

}
