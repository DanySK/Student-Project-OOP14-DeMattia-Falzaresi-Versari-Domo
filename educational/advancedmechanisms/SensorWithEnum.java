package advancedmechanisms;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This class represent a Standard Sensor Class but with the addition of an Enum with the type of the sensor
 *
 */

public class SensorWithEnum {

	private int id;
	private String name;
	private SensorTypology type;
	private boolean alert;
	private int alertCount;

	/**
	 * Constructor of this element has the difference to accept an enum "sType" ad type of sensor instead of String. 
	 * @param sName name to enter in the sensor
	 * @param sId id of the sensor
	 * @param sType enum type of the sensor
	 */
	public SensorWithEnum(final String sName, final int sId, final SensorTypology sType) {
		this.id = sId;
		this.name = sName;
		this.alert = false;
		this.alertCount = 0;
		this.type = sType;
	}
	
	/**
	 * This method return the id of the sensor.
	 * @return id number
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * With this method is possible to change the sensor id.
	 * @param sId id to set on the sensor.
	 */
	public void setId(final int sId) {
		this.id = sId;
	}
	
	/**
	 * This method return the type of the sensor.
	 * @return type of the sensor
	 */
	public SensorTypology getType() {
		return this.type;
	}
	
	/**
	 * With this method is possible to change the sensor type.
	 * @param sType type to set on the sensor.
	 */
	public void setType(final SensorTypology sType) {
		this.type = sType;
	}
	/**
	 * With this setter method is possible to change the sensor name.
	 * @param sName name to set on the sensor
	 */
	public void setName(final String sName) {
		this.name = sName;
	}
	
	/**
	 * The name getter method.
	 * @return the sensor name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * This method return the alert status.
	 * @return the alert status
	 */
	public boolean isInAlert() {
		return this.alert;
	}

	/**
	 * This method set the alert status and, if the alert is "true" and the previous status was "false".
	 * the alert count will be incremented by one
	 * @param sAlert a boolean alert status
	 */
	public void setAlert(final boolean sAlert) {
		if (!this.alert && sAlert) {
			this.alertCount++;
		}
		this.alert = sAlert;
	}
	
	/**
	 * This method return the count of the alerts.
	 * @return the number of alert.
	 */	
	public int getAlertCount() {
		return this.alertCount;
	}

	@Override
	public String toString() {
		return "Sensor Name: " + this.name + "\nSensor Id: " + this.id + "\nAlert Count: " + this.alertCount;
	}
	
}
