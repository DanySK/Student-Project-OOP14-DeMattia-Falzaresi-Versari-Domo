package domo.GUI;

import java.util.ArrayList;

import domo.devices.Sensor;
import domo.general.Room;

public interface GUIFlat {

	/**
	 * Set a list of sensor in alarm state 
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set in alarm
	 */
	public void setSensorsInAllarm(Room room, ArrayList<Sensor> sensors);

	/**
	 * Reset a list of sensor from in alarm state to 'not in alarm' state
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set 'not in alarm'
	 */
	public void resetSensorsInAllarm(Room room, ArrayList<Sensor> sensors);

	/**
	 * Set the observer for the Graphic Interface
	 * 
	 * @param observer the class observer
	 */
	public void setController(GUIAbstractObserver observer);

}