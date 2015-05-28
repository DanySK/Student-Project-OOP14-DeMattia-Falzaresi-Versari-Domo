package mvc;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 *  This is a room, is made by a name and a list of sensor, 
 *  all the methods where the room name can be changed are surrounded by "try - catch" clause because the method "checkName" can throw a
 *  Custom Exception and interrupt the execution of the program, we will the difference between Flat and Room in the Test Class
 *
 */
public class Room {
	
	private String roomName;
	private final List<Sensor> sensors;
	
	/**
	 * Room constructor name of the room is needed.
	 * @param rName name of the room
	 */
	public Room(final String rName) {
		try {
			checkName(rName);
		} catch (CustomExceptions e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		this.roomName = rName;
		sensors = new LinkedList<>();
	}
	
	/**
	 * This private method check the name inserted and if is not correct throws an exception
	 * @param stCheck the string to check
	 * @throws CustomExceptions	a Custom Exception for name not correct
	 */
	private void checkName(final String stCheck) throws CustomExceptions {
		if (stCheck == null || stCheck.equals("")) {
			throw new CustomExceptions("The name inserted is not correct");
		}
	}
		
	/**
	 * Add a Standard sensor to the Sensors list in the room.
	 * @param sens the sensor to add
	 */
	public void addSensor(final Sensor sens) {
		this.sensors.add(sens);
	}
	
	/**
	 * getter method for the name of the room. 
	 * @return the name of this room
	 */
	public String getName() {
		return this.roomName;
	}

	/**
	 * Change the room name .
	 * @param rName name to set 
	 */
	public void setName(final String rName) {
		try {
			checkName(rName);
		} catch (CustomExceptions e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		this.roomName = rName;
	}
	
	/**
	 * Return the entire list of sensors.
	 * @return a List of sensors
	 */
	public List<Sensor> getSensors() {
		return this.sensors;
	}

}
