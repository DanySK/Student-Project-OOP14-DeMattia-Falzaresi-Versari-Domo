package domo.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import domo.GUI.*;
import domo.bckRst.*;

public class MainClass {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome!");
		Restore res = new RestoreImpl();
		Backup bac = new BackupImpl("domo.xml");
		if (bac.backupNow()) {
			System.out.println("OK!");
		}
		else {
			System.out.println("No Buono!");
		}

		//usato per OSX (barra menu a schermo)
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		//GUIFlatImpl t = new GUIFlatImpl("Domo");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Scegli se vuoi creare un nuovo appartamento o se vuoi caricare da file"C);
		System.out.println("Digita N per nuovo appartamento o C per caricare");
		String s = br.readLine();
		if(s.equals("C")){
			System.out.println("HAi scelto di caricare");
		}
		else if (s.equals("N")){
			System.out.println("HAi scelto nuovo appartamento");
			
			Flat fl = new FlatImpl("appartamento");
			fl.addRoom(new RoomImpl("Cucina"));
		}

	}

}
