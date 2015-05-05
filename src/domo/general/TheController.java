package domo.general;

import java.util.Set;

import domo.GUI.GUIAbstractObserver;
import domo.GUI.GUIFlatImpl;
import domo.devices.Sensor;

public class TheController extends GUIAbstractObserver{
	private GUIFlatImpl GraphicInterface;
	
	public TheController(GUIFlatImpl GI) {
		this.GraphicInterface = GI;
		this.GraphicInterface.setController(this);
	}
	

	@Override
	public void addRoomWithNameAndSensors(String name, Set<Sensor> sensors) {
		System.out.println("controller: addRoomWithNameAndSensors  number of select sensor: " + sensors.size());
	}

	@Override
	public Sensor addSensorWithName(String name) {
		System.out.println("controller: addSensorWithName    sensorNaem: " + name);
		return null;
	}

	@Override
	public Set<Room> getRoomList() {
		System.out.println("controller: getRoomList");
		return null;
	}

	@Override
	public void addSensorToRoom(Set<Sensor> sensors, Room room) {
		// TODO Auto-generated method stub
		System.out.println("controller: addSensorToRoom   number of select sensor: " + sensors.size());
	}

	@Override
	public void newProject() {
		System.out.println("controller: newProject");
		
	}

	@Override
	public void closeProgram() {
		System.out.println("controller: closeProgram");
		
	}

	@Override
	public void save(String filePathWithName) {
		System.out.println("controller: save  file name: " + filePathWithName);
		
	}

	@Override
	public void load(String filePath) {
		System.out.println("controller: load filename: " + filePath);
		
	}

	@Override
	public void refreshSensorList() {
		System.out.println("controller: refreshSensorList");
		
	}
}
