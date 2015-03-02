package domo.general;

import java.awt.Dimension;
import java.awt.Point;

import devices.Sensor;

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
	 * Get room name.
	 * @return room name.
	 */
	String getName();
	
	/**
	 * Get room size.
	 * @return room size.
	 */
	Dimension getSize();
	
	/**
	 * Get room location.
	 * @return room location.
	 */
	Point getLocation();
	
	/**
	 * Set room location.
	 * @param destination room destination point.
	 */
	void setLocation(Point destination);
	
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
