package devices;

import java.awt.Dimension;
import java.awt.Point;

/**
 * 
 * @author Marco Versari
 *
 */

public interface Sensor {
	/**
	 * 
	 * @return the device id
	 */
	int getId();
	
	/**
	 * 
	 * @return the device name
	 */
	String getName();
	
	/**
	 * 
	 * @return return the device type
	 */
	SensorTypology getType();
	
	/**
	 * 
	 * @return the device status
	 */
	SensorStatus getStatus();
	
	/**
	 * 
	 * @return the device image size
	 */
	Dimension getSize();
	
	/**
	 * 
	 * @return the device image point
	 */
	Point getLocation();
	
	/**
	 * 
	 * @return if the device is in alert
	 */
	boolean isInAlert();
	
	/**
	 * 
	 * @param destination set the device image point
	 */
	void setLocation(Point destination);
	
	/**
	 * 
	 * @param size set the device dimension
	 */
	void setSize(Dimension size);
	
	/**
	 * 
	 * @param alert set the device alert
	 */
	void setAlert(boolean alert);
	
	/**
	 * 
	 * @param status set the device status
	 */
	void setStatus(SensorStatus status);
	
	/**
	 * 
	 * @return the device image path
	 */
	String getImagePath();
}
