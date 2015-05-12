package domo.devices;

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
	 * Set the device id.
	 * @param id the device id.
	 */
	void setId(int id);
	
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
	 * Query the x position.
	 * @return the device image point
	 */
	double getXPosition();
	
	/**
	 * Query the y position.
	 * @return the device image point
	 */
	double getYPosition();
	
	/**
	 * get the degree of the sensor.
	 * @return the degree of the sensor.
	 */
	double getDegree();
	
	/**
	 * Set the sensor degree.
	 * @param degree The sensor degree.
	 */
	void setDegree(double degree);
	
	/**
	 * Get the sensor alert status.
	 * @return if the device is in alert.
	 */
	boolean isInAlert();
	
	/**
	 * set the device image point.
	 * @param x The x position.
	 * @param y The y Position.
	 */
	void setLocation(double x, double y);
	
	/**
	 * Set the status the alert status.
	 * @param pAlert status parameter.
	 */
	 void setAlert(boolean pAlert);

	/**
	 * Set the the alert status.
	 * @param pStatus the status to set.
	 */
	void setStatus(SensorStatus pStatus);
	
	/**
	 * Get the image path.
	 * @return the device image path
	 */
	String getImagePath();
}
