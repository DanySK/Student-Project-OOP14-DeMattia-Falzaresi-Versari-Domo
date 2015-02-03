package BckRst;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * @version 1.0.0
	 * This is the interface for the backup class
	 *
	 */

public interface Backup {

	/**
	 * Start do backup everything and return true if no error occour
	 * @throws Exception 
	 */
	boolean BackupNow();
}
