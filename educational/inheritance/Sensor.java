package inheritance;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * The interface "Sensor" define the main requirements of a generic sensor, the user with getter methods is able to
 * get the id,name,alert status and alert count of it and with setters method of course he can set it.
 * When the object is created the alert status is set to false.
 *
 */
public interface Sensor {
	
	/** 
	 * This is the id getter method.
	 * @return Id of this instance of the object
	 */
	int getId();
	
	/**
	 * Setter method for set the device id.
	 * @param id to be set on the sensor.
	 */
	void setId(int id);
	
	/**
	 * the name getter method.
	 * @return the sensor name
	 */
	String getName();
	
	/**
	 * Setter method for the sensor name.
	 * @param name to set on the sensor
	 */
	void setName(String name);
	
	/**
	 * this method return the alert status.
	 * @return the alert status
	 */
	boolean isInAlert();
	
	/**
	 * this method set the alert status and, if the alert is "true" and the previous status was "false" 
	 * the alert count will be incremented by one
	 * @param alert a boolean alert status
	 */
	void setAlert(boolean alert);
	
	/**
	 * this method return the count of the alerts.
	 * @return the number of alert.
	 */
	int getAlertCount();
	
	
}
