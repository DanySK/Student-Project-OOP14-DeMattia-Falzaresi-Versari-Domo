package mvc;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This Class save all elements of a "Flat" in a txt file
 * in this example we are using the object "DataOutpuStream" because is more useful with Strings.
 * all method related with files throw exceptions about many aspects (for example file not accessible or file not present)
 * so is important to handle all of this errors
 *
 */
public class Backup {

	private final Flat myFlat;
	
	/**
	 * Constructor of the Backup Class accept the flat to be saved.
	 * @param sFlat flat to backup
	 */
	public Backup(final Flat sFlat) {
		this.myFlat = sFlat;
	}
	
	/**
	 * This method allow the user to backup a Flat element (only its name and rooms names).
	 * 
	 * @param sFile the destination file where we want to put everything
	 * @return true or false if the backup procedure end correctly
	 */
	public boolean doBackup(final String sFile) {
		try {
			// A new DataOutputStream is created, this will be the destination of our backup
			final DataOutputStream file = new DataOutputStream(new FileOutputStream(sFile));
			//writeUTF allow us to write a string in the file
			file.writeUTF(this.myFlat.getName());
			file.writeUTF(this.myFlat.getImage());
			
			for (final Room room : this.myFlat.getRooms()) {
				file.writeUTF(room.getName());
			}
			file.writeUTF("resEnd");
			//All stream (input or output) need to be closed when the work is done
			file.close();
			return true;
		} catch (IOException e) {
			//catch all the exceptions file related but without blocking the application (but the return is false)
			System.out.print("Error in the backup procedure: " + e.toString());
			return false;
		}
		
	}
}
