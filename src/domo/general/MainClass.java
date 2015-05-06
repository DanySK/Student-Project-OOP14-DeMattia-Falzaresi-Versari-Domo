package domo.general;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import domo.GUI.*;
import domo.bckRst.*;
import domo.devices.Sensor;
import domo.devices.SensorTypology;
import domo.devices.loader.DynamicLoader;
import domo.devices.loader.DynamicLoaderImpl;
import domo.devices.sensor.MotionSensor;

public class MainClass {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome!");
		Restore res = new RestoreImpl();
		Backup bac = new BackupImpl("domo.xml");
		Flat fl;
		//usato per OSX (barra menu a schermo)
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		final String classPath = "classi";
		System.out.println(classPath);
		final DynamicLoader<Sensor> listaClassiSensori = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor");			
		listaClassiSensori.setModulePath(classPath);
		final Set<String> resLoader = listaClassiSensori.updateModuleList();
		
		ArrayList <Map <String, String>> sensorTypeList = new ArrayList<>();
		resLoader.forEach(x -> {
			try {
				HashMap <String, String> t = new HashMap<>();
				t.put("name", listaClassiSensori.createClassInstance(x).getName());
				t.put("image", listaClassiSensori.createClassInstance(x).getImagePath());
				t.put("type", listaClassiSensori.createClassInstance(x).getType().toString());
				sensorTypeList.add(t);
				
			} catch (Exception e) {
				fail(e.toString());
			}
		});
		
		GUIFlatImpl t = new GUIFlatImpl("Domo", sensorTypeList);
		
		new TheController(t);
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Scegli se vuoi creare un nuovo appartamento o se vuoi caricare da file");
		System.out.println("Digita N per nuovo appartamento o C per caricare");
		String s = br.readLine();
		if (s.equals("C")) {
			System.out.println("Hai scelto di caricare");
			try {
				fl = res.restoreNow("tmp.dom");
				System.out.println("Restore Terminato!");
				System.out.println("Nome Appartamento: "+fl.getName());
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (s.equals("N")) {
			int roomId;
			Set <Room> sRoom = new HashSet<>();
			System.out.println("HAi scelto nuovo appartamento");
			
			fl = new FlatImpl("appartamento");
			
			roomId = fl.addRoom(new RoomImpl("Cucina"));
			roomId = fl.addRoom(new RoomImpl("Salotto"));
			sRoom = fl.getRooms();
			Room nowRoom = null;
			for (Room room : sRoom) {
				if (room.getId() == roomId) {
					//creation of a new dynamic loader
					DynamicLoader<Sensor> instance = new DynamicLoaderImpl<Sensor>("domo.devices", "Sensor");
					System.out.println("Stampo l'elenco delle classi");
					Set<String> setString = new HashSet<>();
					setString = instance.updateModuleList();
					for (String string : setString) {
						System.out.println(string);
					}
					System.out.println("Stampa finita");
					nowRoom=room;
					room.addSensor(new MotionSensor());
				}
			}
			
			System.out.println("Il Nome Dell'appartamento è "+fl.getName());
			System.out.println("Il Nome di una stanza è " +nowRoom.getName());
			System.out.println("Avvio il backup");
			if(bac.backupNow(fl)){
				System.out.println("Backup terminato");
			}
			else {
				System.out.println("Errore nel Backup");
			}
			System.in.read();
		}

	}


}
