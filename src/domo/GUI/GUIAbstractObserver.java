package domo.GUI;


import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import domo.devices.Sensor;
import domo.general.Flat;
import domo.general.Room;

public abstract class GUIAbstractObserver {
	
	protected GUIFlat guiFlat;
	
	public abstract void addRoomWithNameAndSensors(String name, ArrayList<Sensor> arrayList);
	
	public abstract Sensor addSensorWithName(String name);
	
	public abstract ArrayList<Room> getRoomList();
	
	public abstract void addSensorToRoom(ArrayList<Sensor> arrayList, Room room);
	
	public abstract void newProject();
	
	public abstract void closeProgram();
	
	public abstract void save(String filePathWithName, final String imageFilePath);

	public abstract Flat load(String filePath);
	
	public abstract void refreshSensorList();	
	
	public abstract void deleteSensors(ArrayList<Sensor> sensors);

}
