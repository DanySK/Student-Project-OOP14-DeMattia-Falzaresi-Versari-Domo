package domo.educational.io;

import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This class let the user restore his flat  (only flat and rooms names)
 * in this test a "DataInputStream" has been used because is more String friendly.
 * in the file path we can find some useful elements called "System.getProperty(String s)" this method allow
 * the user to access to some environment useful variable (for example the file separator) if the software made need to
 * in different computers or OS
 * 
 *  In the Javadoc at this link all the available variables
 *  https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getProperties--
 *
 */
public class Restore {
	
	/**
	 * doRestore method allow the user to import a file and start the restoring procedure of the flat and
	 * the rooms inside it.
	 * @param fName name of the file to import
	 * @return the restored flat
	 * @throws Exception some exception can occur when opening files
	 */
	public Flat doRestore(final String fName) throws Exception {
		Flat resFlat = null;
		DataInputStream dIs = new DataInputStream(new FileInputStream(new File(System.getProperty("user.home") + System.getProperty("file.separator") + fName)));
		resFlat = new Flat(dIs.readUTF());
		String s = dIs.readUTF();
		do {
			resFlat.addRoom(new Room(s));
			s = dIs.readUTF();
			System.out.println();
		} while (!s.equals("resEnd"));
		dIs.close();
		return resFlat;
	}

}
