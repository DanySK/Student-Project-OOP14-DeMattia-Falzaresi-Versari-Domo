package domo.general;

import domo.devices.loader.DynamicLoaderImpl;
import domo.bckrst.RestoreDomoConfException;
import domo.bckrst.BackupDomoConfException;
import domo.util.test.AbstracTestInterface;
import domo.devices.loader.DynamicLoader;
import static org.junit.Assert.fail;
import domo.util.test.DomoTest;
import domo.bckrst.RestoreImpl;
import domo.bckrst.BackupImpl;

import java.util.ArrayList;

import domo.bckrst.Restore;
import domo.devices.Sensor;
import domo.graphic.GUIAbstractObserver;
import domo.graphic.GUIFlat;
import domo.bckrst.Backup;

import java.util.Set;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 * 
 */
public class TheController extends GUIAbstractObserver implements AbstracTestInterface {

	private GUIFlat graphicInterface;
	private Flat flat;
	private DomoTest testFrame;
	@SuppressWarnings("unused")
	private boolean inallarm = false;
	
	/**
	 * Constructor.
	 * @param gI a GUIFlatImpl object to start with the controller 
	 */
	public TheController(final GUIFlat gI) {
		this.graphicInterface = gI;
		this.graphicInterface.setController(this);
	}
	
	/**
	 * This method is for start the testing frame.
	 * @param test 
	 */
	public void startTesting(final DomoTest test) {
		this.testFrame = test;
		this.testFrame.setObserver(this);
	}
	
	private Room getRoomfromName(final String roomName) {
		return this.flat.getRooms().stream().filter(s->s != null).filter(s->s.getName().equals(roomName)).findFirst().get();
	}

	@Override
	public void addRoomWithNameAndSensors(final String name, final ArrayList<Sensor> sensors) {
		System.out.println("controller: addRoomWithNameAndSensors \n number of select sensor: " + sensors.size() + " room name: " + name);
		int idR = flat.addRoom(name);
		Room roomToAdd = flat.getRoom(idR);
		for (Sensor sensor : sensors) {
			for (Room rooms : flat.getRooms()) {
				getRoomfromName(rooms.getName()).removeSensor(sensor.getId());
			}
			flat.addSensorToRoom(roomToAdd, sensor);
		}
		testFrame.refresh(flat);

	}

	@Override
	public Sensor addSensorWithName(final String name) {
		System.out.println("controller: addSensorWithName: " + name);
		final String classPath = "classi";
		final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
		listaClassiSensori.setModulePath(classPath);
		final Set<String> resLoader = listaClassiSensori.updateModuleList();
		for (String x : resLoader) {
			try {
				if (listaClassiSensori.createClassInstance(x).getName().equals(name)) {
					Sensor tmp = listaClassiSensori.createClassInstance(x);
					flat.addSensorToRoom(getRoomfromName("Default Room"), tmp);
					testFrame.refresh(flat);
					return tmp;
				}
			} catch (Exception e) {
				fail(e.toString());
			}
		}
		testFrame.refresh(flat);
		return null;
	}

	@Override
	public ArrayList<Room> getRoomList() {
		System.out.println("controller: getRoomList");
		//return this.roomList;
		return  this.flat != null && this.flat.getRooms().size() > 0 ? new ArrayList<>(flat.getRooms()) : null;
	}

	@Override
	public void addSensorToRoom(final ArrayList<Sensor> sensors, final Room room) {
		System.out.println("controller: addSensorToRoom   number of select sensor: " + sensors.size() + "room name: " + room);
		for (Sensor sensor : sensors) {
			for (Room rooms : flat.getRooms()) {
				getRoomfromName(rooms.getName()).removeSensor(sensor.getId());
			}
			flat.addSensorToRoom(room, sensor);
		}
		testFrame.refresh(flat);
	}

	@Override
	public void newProject() {
		System.out.println("controller: newProject");
		this.flat = new FlatImpl("New Flat");
		this.flat.addRoom("Default Room");
	}

	@Override
	public void closeProgram() {
		System.out.println("controller: closeProgram");

		//qui si puo mettere il modo per stoppare l'agent
	}

	@Override
	public void save(final String filePathWithName, final String imageFilePath) {
		System.out.println("controller: save  file name: " + filePathWithName + " Image file: " + imageFilePath);
		this.flat.setImagePath(imageFilePath);
		try {
			Backup bac = new BackupImpl(filePathWithName);
			bac.backupNow(this.flat);
		} catch (BackupDomoConfException e) {
			System.out.println(e);
		}
	}

	@Override
	public Flat load(final String filePath) {
		System.out.println("controller: load filename: " + filePath);
		try {
			Restore res = new RestoreImpl();
			this.flat = res.restoreNow(filePath);

		} catch (RestoreDomoConfException e) {
			System.out.println(e);
		}
		testFrame.refresh(flat);
		return this.flat == null ? null : this.flat;
	}

	@Override
	public void refreshSensorList() {
//		System.out.println("controller: refreshSensorList");
//		for (Room rooms : flat.getRooms()) {
//			for  (Sensor sensor : rooms.getSensor()) {
//				if (inallarm) {
//					sensor.setAlert(true);
//				} else  {
//					sensor.setAlert(false);
//				}
//			}
//			if (inallarm) {
//				graphicInterface.setSensorsInAllarm(rooms, new ArrayList<Sensor>(rooms.getSensor()));
//			} else {
//				graphicInterface.resetSensorsInAllarm(rooms, new ArrayList<Sensor>(rooms.getSensor()));
//
//			}
//			inallarm = !inallarm;
//		}
	}

	@Override
	public void deleteSensors(final ArrayList<Sensor> sensors) {

		for (Room room : flat.getRooms()) {
			for (Sensor sensor : sensors) {
				if (room.getSensor().contains(sensor)) {
					room.removeSensor(sensor.getId());
				}
			}
		}
	}

	@Override
	public void sensorStateChange() {
		for (Room rooms : flat.getRooms()) {
			ArrayList<Sensor> tempAllarm = new ArrayList<>();
			ArrayList<Sensor> tempNotAllarm = new ArrayList<>();
			for  (Sensor sensor : rooms.getSensor()) {
				if (sensor.isInAlert()) {
					tempAllarm.add(sensor);
				} else {
					tempNotAllarm.add(sensor);
				}
			}
			graphicInterface.setSensorsInAllarm(rooms, tempAllarm);
			graphicInterface.resetSensorsInAllarm(rooms, tempNotAllarm);
		}
	}
	
	/**
	 * A method to get the flat from the controller.
	 * @return the flat
	 */
	public Flat getFlat() {
		return flat;
	}
}
