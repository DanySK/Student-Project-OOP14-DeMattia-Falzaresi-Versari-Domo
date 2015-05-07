package domo.devices;

import java.awt.Dimension;
import java.awt.Point;

/**
 * 
 * @author Marco Versari
 *
 */

public interface Sensor  {
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
	 * @return the device image size --??--
	 */
	Dimension getSize();
	
	/**
	 * 
	 * @return the device image point
	 */
	Point getLocation();
	double getXPosition();
	double getYPosition();
	/**
	 * 
	 * @return if the device is in alert
	 */
	boolean isInAlert();
	
	/**
	 * 
	 * @param destination set the device image point
	 */
	void setLocation(double x, double y);
	void setLocation(Point point);
	
	/**
	 * 
	 * @param size set the device dimension
	 */
	void setSize(Dimension size);
	
	/**
	 * 
	 * @return the device image path
	 */
	String getImagePath();
	
	void setAlert(final boolean pAlert);	
	void setStatus(final SensorStatus pStatus);
}
