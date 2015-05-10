package domo.bckRst;

import domo.general.Flat;

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
	 * @param Flat, the flat to be backupped
	 * @throws BackupDomoConfException 
	 */
	void backupNow(Flat flatB) throws BackupDomoConfException;


}
