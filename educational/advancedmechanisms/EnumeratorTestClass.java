package advancedmechanisms;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is a generic class for Enumerator Tests
 *
 */
public class EnumeratorTestClass {

	public static void main(String[] args) {
		
		/* Creation of the room */
		Room myRoom = new Room("Kitchen");
		
		/* Creation of a Standard Sensor */
		Sensor stSens = new Sensor("Kitchen Door", 1, "Door");
		
		/* Creation of a Sensor With Enum */
		SensorWithEnum senSens = new SensorWithEnum("Kitchen Door", 1, SensorTypology.DOOR);
		
		/* Add Sensor to the room with different methods */
		myRoom.addSensor(stSens);
		myRoom.addSensorEnum(senSens);
		
		/* Now I add other Sensors to the room */
		
		myRoom.addSensor(new Sensor("Kitchen Window", 2, "Glasses"));
		myRoom.addSensorEnum(new SensorWithEnum("Kitchen Window", 2, SensorTypology.GLASSBREAK));
		
		myRoom.addSensor(new Sensor("Kitchen Camera", 3, "Cam"));
		myRoom.addSensor(new Sensor("Kitchen Camera No 2", 4, "Camera"));
		/* In this row is possible to see one of the differences between Enum and normal string */
		myRoom.addSensorEnum(new SensorWithEnum("Kitchen Camera", 3, SensorTypology.CAMERA));
		myRoom.addSensorEnum(new SensorWithEnum("Kitchen Camera No 2", 4, SensorTypology.CAMERA));
		
		
		/* Now let's take a look at what happen when we want to iterate our sensors */
		
		List<Sensor> stList = myRoom.getSensors();
		List<SensorWithEnum> senList = myRoom.getSensorsWithEnum();
		
		/* Let's try to get a list of sensors different from camera */
		
		List<Sensor> noCamList = new LinkedList<>();
		List<SensorWithEnum> noCamListEnu = new LinkedList<>();
		
		/* this is a really slow method and we have to be sure in what the user wrote on the String*/
		for (Sensor sensor : stList) {
			if (!sensor.getType().equals("Cam") && !sensor.getType().equals("Camera")) {
				noCamList.add(sensor);
			}
		}
		
		/* this is what happen with enum, is faster and more safer */
		for (SensorWithEnum sensorWithEnum : senList) {
			if (!sensorWithEnum.getType().isTiltable()) {
				noCamListEnu.add(sensorWithEnum);
			}
		}
		
		/* now let's see the two lists */
		System.out.println("Results Print");
		for (Sensor sensor : noCamList) {
			System.out.println(sensor.toString());
			
		}
		System.out.println("Results Print With Enum");
		for (Sensor sensor : noCamList) {
			System.out.println(sensor.toString());
		}
	}

}
