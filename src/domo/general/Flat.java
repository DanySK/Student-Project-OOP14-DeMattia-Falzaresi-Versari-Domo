package domo.general;

import java.util.Set;

import domo.devices.Sensor;
import domo.devices.util.pair.Pair;

/**
 * 
 * @author Marco Versari
 * 
 *  The is an interface that represents our flat.
 *
 */
public interface Flat {
	/**
	 * Get the name of the flat.
	 * @return the name of the flat.
	 */
	String getName();
	
	/**
	 * Add a room in the list by id.
	 * @param name the room name.
	 * @return The room id
	 */
	int addRoom(String name);
	
	/**
	 * Add a sensor to the flat.
	 * @param room the room sensor.
	 * @param sensor the sensor to add.
	 * @return A pair of integer, the first is the unique id on the flat, the second is the unique id on the room.
	 */
	Pair<Integer, Integer> addSensorToRoom(Room room, Sensor sensor);
	
	/**
	 * Get the room list.
	 * @return all rooms in the flat.
	 */
	Set<Room> getRooms();
	
	/**
	 * get room by id.
	 * @param id the room id.
	 * @return the room object.
	 */
	Room getRoom(int id);
	
	/**
	 * Remove room in the list by id.
	 * @param id room to remove.
	 */
	void removeRoom(int id);
	
	/**
	 * Get the image path.
	 * @return the image path,
	 */
	String getImagePath();
	
	/**
	 * Set the image path.
	 * @param path The image path to set.
	 */
	void setImagePath(String path);
}
