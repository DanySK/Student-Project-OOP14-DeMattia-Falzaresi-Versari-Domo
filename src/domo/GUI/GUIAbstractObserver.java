package domo.GUI;


import domo.devices.Sensor;
import domo.general.Room;

public abstract class GUIAbstractObserver {
	
	protected GUIFlat guiFlat;
	
	public abstract void flatAdded(Room room);
	
	public abstract void roomAdded(Room room);
	
	public abstract void sensorAdded(Sensor sensor);
	
	public abstract void newProject();
	
	public abstract void closeProgram();
	
	public abstract void save();

	public abstract void load(String filePath);

}
