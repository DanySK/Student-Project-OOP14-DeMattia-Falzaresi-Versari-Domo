package domo.general;

import java.awt.Point;
import java.util.Set;

import domo.devices.Sensor;

/**
 * 
 * @author Marco Versari
 * 
 *  The is an interface that represents our room.
 *
 */
public interface Room {
	/**
	 * Get room id.
	 * @return room id.
	 */
	int getId();
	
	/**
	 * Set room id.
	 * @param pId Set room id.
	 */
	void setId(final int pId);
	
	/**
	 * Get room name.
	 * @return room name.
	 */
	String getName();
	
	/**
	 * Get the sensor list.
	 * @return all sensor in the room.
	 */
	Set<Sensor> getSensor();	
	
	/**
	 * Add a sensor to room.
	 * @param sensor sensor to add.
	 * @return room id.
	 */
	int addSensor(Sensor sensor);
	
	/**
	 * Remove a sensor from room.
	 * @param id the sensor is to remove.
	 */
	void removeSensor(int id);
	
	/**
	 * Move a sensor.
	 * @param id the sensor id to move
	 * @param to the point to move the sensor.
	 */
	void moveSensor(int id, Point to);
}
