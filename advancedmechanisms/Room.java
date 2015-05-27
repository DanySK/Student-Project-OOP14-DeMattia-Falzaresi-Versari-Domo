package domo.educational.advancedmechanisms;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 *  This is a room, is made by a name and a list of sensor, in this case I've inserted two lists of sensors to explain the differences 
 *  between objects with enum and without enum
 *  for object with standard types (for example sensor) is better to use Enum because if you have to search in a collection is faster than
 *  a normal "String" and because with String the possibility to insert a wrong character is really high
 *
 */
public class Room {
	
	private String roomName;
	private List<Sensor> sensors;
	private List<SensorWithEnum> sensorsWithEnum;
	
	/**
	 * Room constructor name of the room is needed.
	 * @param rName name of the room
	 */
	public Room(final String rName) {
		this.roomName = rName;
		sensors = new LinkedList<>();
		sensorsWithEnum = new LinkedList<>();
	}
	
	/**
	 * Add a Standard sensor to the Sensors list in the room.
	 * @param sens the sensor to add
	 */
	public void addSensor(final Sensor sens) {
		this.sensors.add(sens);
	}
	
	/**
	 * Add a Sensor with enum to the list in the room.
	 * @param sensEn the sensor to add
	 */
	public void addSensorEnum(final SensorWithEnum sensEn) {
		this.sensorsWithEnum.add(sensEn);
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
		this.roomName = rName;
	}
	
	/**
	 * Return the entire list of sensors.
	 * @return a List of sensors
	 */
	public List<Sensor> getSensors() {
		return this.sensors;
	}

	/**
	 * Return the entire list of sensors with enum.
	 * @return a List of sensorsWithEnum
	 */
	public List<SensorWithEnum> getSensorsWithEnum() {
		return this.sensorsWithEnum;
	}
	
}
