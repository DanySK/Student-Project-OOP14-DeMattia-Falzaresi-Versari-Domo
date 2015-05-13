package domo.GUI;

import java.util.ArrayList;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.Room;
/**
 * Interface of abstract class for GUI observer. 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public interface GUIAbstractInterface {

	/**
	 * Add a room and a sensors list in that specific room.
	 * @param name	the room name to create
	 * @param arrayList the sensors list to add in room
	 */
	void addRoomWithNameAndSensors(String name,
			ArrayList<Sensor> arrayList);

	/**
	 * Add a sensor in not specific room.
	 * @param name The name that represent the specific sensor (motion, IR, magnetic, etc..)
	 * @return	the sensor instance
	 */
	Sensor addSensorWithName(String name);

	/**
	 * get the room list in flat.
	 * @return a room ArrayList
	 */
	ArrayList<Room> getRoomList();

	/**
	 * Add a ArrayList of sensor to a specific room.
	 * @param arrayList sensor list
	 * @param room room to add sensors
	 */
	void addSensorToRoom(ArrayList<Sensor> arrayList, Room room);

	/**
	 * called when press the new project button .
	 */
	void newProject();

	/**
	 * called when the frame did begin to dispose.
	 */
	void closeProgram();

	/**
	 * Called when the save button is clicked.
	 * @param filePathWithName the path to save the project like home/[\]folder/[\...]/[\]filename.extetion
	 * @param imageFilePath the background image path like home/[\]folder/[\...]/[\]filename.extetion
	 */
	void save(String filePathWithName, String imageFilePath);

	/**
	 * Called when the open file button is clicked.
	 * @param filePath the path to save the project like home/[\]folder/[\...]/[\]filename.extetion
	 * @return the flat object that represent the project
	 */
	Flat load(String filePath);

	/**
	 * Refresh the sensor type list available .
	 */
	void refreshSensorList();

	/**
	 * Call when some sensor are delete from project.
	 * @param sensors the sensor list to delete
	 */
	void deleteSensors(ArrayList<Sensor> sensors);

}