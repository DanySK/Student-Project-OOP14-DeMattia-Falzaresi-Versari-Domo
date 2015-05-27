package domo.educational.inheritance;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is a generic clas for inheritance tests
 *
 */

public class InheritanceTestClass {

	public static void main(String[] args) {
		
		/* New SensorImpl Creation */
		Sensor sensI = new SensorImpl("Standard Sensor", 1);
		
		/* The output must be "Sensor Name: Standard Sensor Sensor Id: 1 Alert Count: 0" */
		System.out.println(sensI);
		
		/* Set an Alert */
		sensI.setAlert(true);
		
		/* The output must be "Alert Count: 1" */
		System.out.println("Alert Count: " + sensI.getAlertCount());
		
		/* New ResettableSensor Creation */
		ResettableSensor sensR = new ResettableSensor("Resettable Sensor", 2);
		
		/* The output must be "Sensor Name: Standard Sensor Sensor Id: 2 Alert Count: 0" */
		System.out.println(sensR);
		
		/* Set an Alert */
		sensR.setAlert(true);
		
		/* The output must be "Alert Count: 1" */
		System.out.println("Alert Count: " + sensR.getAlertCount());
		
		/* Decrease the alert count */
		sensR.decreaseCounter();
		
		/* The output must be "Alert Count: 0" */
		System.out.println("Alert Count: " + sensR.getAlertCount());
		
		/* New CamSensor */
		CamSensor sensC = new CamSensor("Camera", 3, 180);
		
		/* The output must be "This is a Camera! Sensor Name: Camera Sensor Id: 3 Alert Count: 0 Sensor Range: 180 Sensor Position 0" */
		System.out.println(sensC);
		
		/* Change camera position */
		sensC.setPosition(88);

		/* The output must be "This is a Camera! Sensor Name: Camera Sensor Id: 3 Alert Count: 0 Sensor Range: 180 Sensor Position 88" */
		System.out.println(sensC);
	}

}
