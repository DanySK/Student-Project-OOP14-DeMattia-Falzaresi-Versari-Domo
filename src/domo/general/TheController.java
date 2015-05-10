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
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 * 
 */
public class TheController extends GUIAbstractObserver{
	
	private GUIFlatImpl graphicInterface;
	private Flat flat;
	
	/**
	 * Constructor.
	 * @param GI a GUIFlatImpl object to start with the controller 
	 */
	public TheController(GUIFlatImpl GI) {
		
		
		this.graphicInterface = GI;
		/*
		for (int i = 0; i < 4; i++) {
			Room t = new RoomImpl("Romm n. " + (i + 1));
			roomList.add(t);
		}
		*/
		this.graphicInterface.setController(this);

	}

	@Override
	public void addRoomWithNameAndSensors(final String name, final Set<Sensor> sensors) {
		System.out.println("controller: addRoomWithNameAndSensors \n number of select sensor: " + sensors.size() + " room name: " + name);
		Room tmpRoom = new RoomImpl(name);
		for (Sensor sensor : sensors) {
			tmpRoom.addSensor(sensor);
			getSensorFromRoom("Default Room").remove(sensor);
		}
		flat.addRoom(tmpRoom);
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
				if(listaClassiSensori.createClassInstance(x).getName().equals(name)) {
					Sensor tmp = listaClassiSensori.createClassInstance(x);
					getRoomfromName("Default Room").addSensor(tmp);
					return tmp;
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
		return  this.flat != null && this.flat.getRooms().size()>0 ? new ArrayList<>(flat.getRooms()) : null;
	}

	@Override
	public void addSensorToRoom(final Set<Sensor> sensors, final Room room) {
		System.out.println("controller: addSensorToRoom   number of select sensor: " + sensors.size() + "room name: " + room);
		Set<Sensor> ss = getSensorFromRoom("Default Room");
		for (Sensor sensor : ss) {
			getRoomfromName(room.getName()).addSensor(sensor);
			if(sensors.contains(sensor)){
				getSensorFromRoom("Default Room").remove(sensor);
			}
		}
		
		
	}

	@Override
	public void newProject() {
		System.out.println("controller: newProject");
		this.flat = new FlatImpl("New Flat");
		this.flat.addRoom(new RoomImpl("Default Room"));
	}

	@Override
	public void closeProgram() {
		System.out.println("controller: closeProgram");

		//qui si puo mettere il modo per stoppare l'agent
	}

	@Override
	public void save(final String filePathWithName, final String imageFilePath) {
		System.out.println("controller: save  file name: " + filePathWithName + " Image file: "+imageFilePath);
		this.flat.setImagePath(imageFilePath);
		try{
			Backup bac = new BackupImpl(filePathWithName);
			bac.backupNow(this.flat);
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
			this.flat = res.restoreNow(filePath);
		}
		catch(RestoreDomoConfException e){
			System.out.println(e);
		}
		return this.flat==null ? null : this.flat;
	}

	@Override
	public void refreshSensorList() {
		System.out.println("controller: refreshSensorList");
	}
	
	private Set getSensorFromRoom(String roomName){
		return this.flat.getRooms().stream().filter(s->s!=null).filter(s->s.getName().equals(roomName)).findFirst().get().getSensor();
	}
	private Room getRoomfromName(String roomName){
		return this.flat.getRooms().stream().filter(s->s!=null).filter(s->s.getName().equals(roomName)).findFirst().get();
	}
	@Override
	public void deleteSensors(ArrayList<Sensor> sensors) {
		// TODO Auto-generated method stub
		
	}
}
