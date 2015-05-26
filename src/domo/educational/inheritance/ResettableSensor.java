package domo.educational.inheritance;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 * The Class Resettable Sensor give the use the opportunity to decrease the alert counter of the standard sensor class.
 * constructor this time is the same of SensorImpl and there are no additional variable in this object.
 * with inheritance each method of the super class "SensorImpl" are available to Resettable Sensor
 */
public class ResettableSensor extends SensorImpl {

	/**
	 * constructor accept same parameters of sensor and with "Super" create an instance of "SensorImpl" object
	 * @param name of the new resettable sensor
	 * @param id  of the resettable sensor
	 */
	public ResettableSensor(String name, int id) {
		super(name, id);
	}
	
	/**
	 * Decrease the number of alert count (only if the number of the alert is more than 0)
	 */
	public void decreaseCounter() {
		if (this.getAlertCount() > 0) {
			this.alertCount--;
		}
	}

}
