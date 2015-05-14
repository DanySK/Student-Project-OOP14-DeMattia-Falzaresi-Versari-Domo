package domo.bckrst;


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
	 * This method start to backup everything.
	 * @param flatB the flat to be backupped
	 * @throws BackupDomoConfException 
	 */
	void backupNow(Flat flatB) throws BackupDomoConfException;


}
