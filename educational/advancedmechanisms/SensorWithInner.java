package advancedmechanisms;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * SensorWithInner contains an example of an "inner class" if compared with the example of the nested class we can see some 
 * differences, scope of variable is the same but, because "ResettableSensor" is a not static element inside another class
 * it doesn't need a constructor, and it's instantiated with a public method "createResettable" in the outer class.
 * In the methods of Resettable Sensor "super" is not used because ResettableSensor is enclosed in SensorWithInner so the inner class can access
 * all the variables of the outer with "Outer.this.xxx" (in our case SensorWithInner.this.xxx)
 *
 */

public class SensorWithInner {

	private int id;
	private String name;
	private boolean alert;
	private int alertCount;
	
	/**
	 * Constructor of the outer class.
	 * @param name of the sensor
	 * @param id of the sensor
	 */
	public SensorWithInner(String name,int id) {
		this.id = id;
		this.name = name;
		this.alert = false;
		this.alertCount = 0;
	}
	
	/**
	 * Create a new element of the inner class "ResettableSensor" and return it 
	 * @return the instance of the new item created
	 */
	public ResettableSensor createResettable() {
		return new ResettableSensor();
	}
	
	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is the inner class "ResettableSensor" add the decrease alert count functionality to the
	 * outer class
	 *
	 */
	public class ResettableSensor {
		
		/**
		 * Decrease counter method, add the possibility to decrease the alert count (if more than 0).
		 * Here we can see how is possible from an inner class to access the outer class variables (if the scope is private too and without "super")
		 * in our case we used "SensorWithInner.this.alertCount--" to decrease the variable alertCount of the outer Class "SensorWithInner" 
		 */
		public void decreaseCounter() {
			if (SensorWithInner.this.getAlertCount() > 0) {
				SensorWithInner.this.alertCount--;
			}
		}
		/**
		 * I've inserted the override of the "toString()" method so the user can see that there are no difference with the variables value from the
		 * inner to the outer class.
		 */
		@Override 
		public String toString() {
			return SensorWithInner.this.toString() + " This print is made from the inner class!";
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
