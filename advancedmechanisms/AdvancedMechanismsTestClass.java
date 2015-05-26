package domo.educational.advancedmechanisms;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is a generic class for AdvancedMechanisms tests
 *
 */
public class AdvancedMechanismsTestClass {

	public static void main(String[] args) {
		
		
		System.out.println("Nested Class Example");
		
		/* New SensorWithNested Creation */
		SensorWithNested sensI = new SensorWithNested("Standard Sensor", 1);
		
		/* The output must be "Sensor Name: Standard Sensor Sensor Id: 1 Alert Count: 0" */
		System.out.println(sensI);
		
		/* Set an Alert */
		sensI.setAlert(true);
		
		/* The output must be "Alert Count: 1" */
		System.out.println("Alert Count: " + sensI.getAlertCount());
		
		/* New Resettable sensor Creation */
		SensorWithNested.ResettableSensor sensR = new SensorWithNested.ResettableSensor("Resettable Sensor", 2);
		
		/* The output must be "Sensor Name: Resettable Sensor Sensor Id: 2 Alert Count: 0" */
		System.out.println(sensR);
		
		/* Set an Alert */
		sensR.setAlert(true);
		
		/* The output must be "Sensor Name: Resettable Sensor Sensor Id: 2 Alert Count: 1" */
		System.out.println(sensR);
		
		/* Method "decreaseCounter" is unavailable for the object SensI */
		//sensI.decreaseCounter();
		
		/* But is available for the nested object  */
		sensR.decreaseCounter();
		
		/* The output must be "Sensor Name: Resettable Sensor Sensor Id: 2 Alert Count: 0" */
		System.out.println(sensR);
		
		System.out.println("Inner Class Example");
		
		/* New Standard Sensor Created */
		SensorWithInner sensInn = new SensorWithInner("Standard Sensor with Inner Class", 1);
		
		/* The output must be "Sensor Name: Standard Sensor with Inner Class Sensor Id: 1 Alert Count: 0" */
		System.out.println(sensInn);
		
		/* Now I Set an Alert */
		sensInn.setAlert(true);
		
		/* Creation of an Inner Resettable Sensor */
		SensorWithInner.ResettableSensor sensInnRes = sensInn.createResettable();
		
		/* The output must be "Sensor Name: Standard Sensor with Inner Class Sensor Id: 1 Alert Count: 1" */
		System.out.println(sensInnRes);
		
		/* Decrease the alert count */
		sensInnRes.decreaseCounter();
		
		/* The output must be "Sensor Name: Standard Sensor with Inner Class Sensor Id: 1 Alert Count: 0" */
		System.out.println(sensInnRes);
		
		/* of course the result is the same if i print the outer element "SensorWithInner" */
		System.out.println(sensInn);
	}
}
