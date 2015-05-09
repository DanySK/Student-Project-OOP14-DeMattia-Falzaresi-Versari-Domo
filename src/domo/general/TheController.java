package domo.general;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import domo.GUI.GUIAbstractObserver;
import domo.GUI.GUIFlatImpl;
import domo.bckRst.Backup;
import domo.bckRst.BackupDomoConfException;
import domo.bckRst.BackupImpl;
import domo.bckRst.Restore;
import domo.bckRst.RestoreDomoConfException;
import domo.bckRst.RestoreImpl;
import domo.devices.Sensor;
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;
/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 */
public class TheController extends GUIAbstractObserver{
	
	private GUIFlatImpl graphicInterface;
	private ArrayList<Room> roomList;
	private Set<Sensor> sensorList;
	private Flat flat;
	
	/**
	 * Constructor.
	 * @param GI a GUIFlatImpl object to start with the controller 
	 */
	public TheController(GUIFlatImpl GI) {
		
		
		this.graphicInterface = GI;
		this.roomList = new ArrayList<>();
		this.sensorList = new HashSet<>();
		
		for (int i = 0; i < 4; i++) {
			Room t = new RoomImpl("Romm n. " + (i + 1));
			roomList.add(t);
		}
		this.graphicInterface.setController(this);

	}

	@Override
	public void addRoomWithNameAndSensors(final String name, final Set<Sensor> sensors) {
		System.out.println("controller: addRoomWithNameAndSensors \n number of select sensor: " + sensors.size() + "room name: " + name);
		Room tmpRoom = new RoomImpl(name);
		for (Sensor sensor : sensors) {
			tmpRoom.addSensor(sensor);
		}
		this.roomList.add(tmpRoom);
	}

	@Override
	public Sensor addSensorWithName(final String name) {
		System.out.println("controller: addSensorWithName    sensorName: " + name);
		final String classPath = "classi";
		final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor", "AbstractSensor");			
		listaClassiSensori.setModulePath(classPath);
		final Set<String> resLoader = listaClassiSensori.updateModuleList();
		for (String x : resLoader) {
			try {
				if(listaClassiSensori.createClassInstance(x).getName().equals(name)) {
					return listaClassiSensori.createClassInstance(x);
				}
				
			} catch (Exception e) {
				fail(e.toString());
			}
		}
	
		//qui simone mi da il nome del sensore e io lo istanzio e poi glielo restituisco
		return null;
	}

	@Override
	public ArrayList<Room> getRoomList() {
		System.out.println("controller: getRoomList");
		//return this.roomList;
		return this.roomList.size()>0 ? this.roomList : null;
	}

	@Override
	public void addSensorToRoom(final Set<Sensor> sensors, final Room room) {
		System.out.println("controller: addSensorToRoom   number of select sensor: " + sensors.size() + "room name: " + room);
		for (Sensor sensor : this.sensorList) {
			this.roomList.stream().filter(s->s!=null).filter(s->s.equals(room)).findFirst().get().addSensor(sensor);
			if(sensors.contains(sensor)){
				this.sensorList.remove(sensor);
			}
		}
		
		
	}

	@Override
	public Flat newProject() {
		System.out.println("controller: newProject");
		this.flat = new FlatImpl("New Flat");
		return this.flat;
	}

	@Override
	public void closeProgram() {
		System.out.println("controller: closeProgram");

		//qui si puo mettere il modo per stoppare l'agent
	}

	@Override
	public void save(final String filePathWithName) {
		System.out.println("controller: save  file name: " + filePathWithName);
		try{
			Backup bac = new BackupImpl(filePathWithName);
			bac.backupNow(null);
		}
		catch (BackupDomoConfException e){
			System.out.println(e);
		}

	}

	@Override
	public Flat load(final String filePath) {
		System.out.println("controller: load filename: " + filePath);
		try{
			Restore res = new RestoreImpl();
			return res.restoreNow("tmp.dom");
		}
		catch(RestoreDomoConfException e){
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void refreshSensorList() {
		System.out.println("controller: refreshSensorList");
	}
}
