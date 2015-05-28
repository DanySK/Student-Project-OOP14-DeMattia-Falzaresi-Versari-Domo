package advancedmechanisms;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * SensorWithNested contains an example of a "nested class" if compared with the sensor of the lesson about "Inheritance" the scope of variable
 * "alertCount" is changed and now is private (before was protected) because the nested class "Resettable Sensor" can access to the super
 * attributes if they are private too.
 * this implementation is useful for cases like this when only few functionality are added to the extended class
 * to prevent the uncontrolled growing of classes
 *
 */

public class SensorWithNested {

	private int id;
	private String name;
	private boolean alert;
	private int alertCount;
	
	/**
	 * Constructor of the outer class.
	 * @param name of the sensor
	 * @param id of the sensor
	 */
	
	public SensorWithNested(String name,int id) {
		this.id = id;
		this.name = name;
		this.alert = false;
		this.alertCount = 0;
	}
	
	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is the nested class "Resettable Sensor" add the decrease alert count functionality to the
	 * outer class
	 *
	 */
	static class ResettableSensor extends SensorWithNested {
		/**
		  * The constructor accept same parameters of SensorWithNested and when is created a new instance of sensorImpl is created too
		  * @param name of the new resettable sensor
		  * @param id  of the resettable sensor
		  */
		public ResettableSensor(String name, int id) {
			super(name, id);
		}
		/**
		 * Decrease counter method, add the possibility to decrease the alert count (if more than 0)
		 * as told before super of a nested class can access to private method of the extended class too
		 */
		public void decreaseCounter() {
			if (super.getAlertCount() > 0) {
				super.alertCount--;
			}
		}	
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
	 * @param id to be set on the sensor.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * With this setter method is possible to change the sensor name.
	 * @param name to set on the sensor
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param alert a boolean alert status
	 */
	public void setAlert(boolean alert) {
		if (!this.alert && alert) {
			this.alertCount++;
		}
		this.alert = alert;
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
