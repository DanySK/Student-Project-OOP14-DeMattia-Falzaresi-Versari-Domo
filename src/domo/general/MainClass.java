package domo.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import domo.GUI.*;
import domo.bckRst.*;
import domo.devices.Sensor;
import domo.devices.SensorImpl;
import domo.devices.SensorTypology;

public class MainClass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome!");
		
		//System.setProperty("apple.laf.useScreenMenuBar", "true");
		GUIFlatImpl t = new GUIFlatImpl("Domo");
		
		Restore res = new RestoreImpl();
		Backup bac = new BackupImpl("domo.xml");
		Flat fl;
		//usato per OSX (barra menu a schermo)
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		//GUIFlatImpl t = new GUIFlatImpl("Domo");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Scegli se vuoi creare un nuovo appartamento o se vuoi caricare da file");
		System.out.println("Digita N per nuovo appartamento o C per caricare");
		String s = br.readLine();
		if (s.equals("C")) {
			System.out.println("Hai scelto di caricare");
			try {
				fl = res.restoreNow("tmp.dom");
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
			sRoom = fl.getRooms();
			Room nowRoom = null;
			for (Room room : sRoom) {
				if (room.getId() == roomId) {
					nowRoom=room;
					room.addSensor(new SensorImpl(0, "sensoreTest", SensorTypology.MOTION, "mot.jpg"));
				}
			}
			System.out.println("Il Nome Dell'appartamento è "+fl.getName());
			System.out.println("Il Nome di una stanza è " +nowRoom.getName());
			System.out.println("Avvio il backup");
			bac.backupNow(fl);
			System.out.println("Backup terminato");
			System.in.read();
		}

	}

}
