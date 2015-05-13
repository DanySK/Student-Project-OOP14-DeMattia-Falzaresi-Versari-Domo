package domo.graphic;

import java.util.Collection;

import domo.devices.Sensor;
import domo.general.Room;
/**
 * GUI interface.
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public interface GUIFlat {

	/**
	 * Set a list of sensor in alarm state.
	 * (change left panel led color and the color filter in 
	 *  main window)
	 * @param room the sensor's room
	 * @param sensors sensors list to set in alarm
	 */
	 void setSensorsInAllarm(Room room, Collection<Sensor> sensors);

	/**
	 * Reset a list of sensor from in alarm state to 'not in alarm' state
	 * (change left panel led color and the color filter in 
	 *  main window).
	 * @param room the sensor's room
	 * @param sensors sensors list to set 'not in alarm'
	 */
	 void resetSensorsInAllarm(Room room, Collection<Sensor> sensors);

	/**
	 * Set the observer for the Graphic Interface.
	 * 
	 * @param observer the class observer
	 */
	void setController(GUIAbstractInterface observer);

}