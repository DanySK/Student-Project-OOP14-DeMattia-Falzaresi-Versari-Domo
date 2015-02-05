import BckRst.*;
import domo.GUI.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome!");
		Restore res = new RestoreImpl();
		Backup bac = new BackupImpl("domo.xml");
		if(bac.BackupNow()){
			System.out.println("OK!");
		}
		else{
			System.out.println("No Buono!");
		}
		//branch test
		//usato per OSX (barra menu a schermo)
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		FlatGUI t = new FlatGUI("Domo");

	}

}
