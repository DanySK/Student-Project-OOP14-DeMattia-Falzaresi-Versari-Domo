package domo.general;

import java.util.HashSet;
import java.util.Set;

import domo.GUI.GUIAbstractObserver;
import domo.GUI.GUIFlatImpl;
import domo.devices.Sensor;

public class TheController extends GUIAbstractObserver{
	private GUIFlatImpl graphicInterface;
	
	public TheController(GUIFlatImpl GI) {
		this.graphicInterface = GI;
		this.graphicInterface.setController(this);
	}
	

	@Override
	public void addRoomWithNameAndSensors(final String name, final Set<Sensor> sensors) {
		System.out.println("controller: addRoomWithNameAndSensors \n number of select sensor: " + sensors.size() + "room name: " + name);
	}

	@Override
	public Sensor addSensorWithName(final String name) {
		System.out.println("controller: addSensorWithName    sensorNaem: " + name);
		return null;
	}

	@Override
	public Set<Room> getRoomList() {
		System.out.println("controller: getRoomList");
		return null;
	}

	@Override
	public void addSensorToRoom(final Set<Sensor> sensors, final Room room) {
		// TODO Auto-generated method stub
		System.out.println("controller: addSensorToRoom   number of select sensor: " + sensors.size() + "room name: " + room);
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
	public void save(final String filePathWithName) {
		System.out.println("controller: save  file name: " + filePathWithName);
		
	}

	@Override
	public void load(final String filePath) {
		System.out.println("controller: load filename: " + filePath);
		
	}

	@Override
	public void refreshSensorList() {
		System.out.println("controller: refreshSensorList");
		
	}
}
