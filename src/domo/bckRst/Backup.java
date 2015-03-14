package domo.bckRst;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is the interface for the backup class
	 *
	 */

public interface Backup {

	/**
	 * Start to backup everything.
	 * @throws Exception 
	 * @return True if backup is correctly done,False if an error occur
	 */
	boolean backupNow();
}
