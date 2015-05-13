package domo.bckrst;

import domo.general.Flat;

	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is the interface for the restore class
	 */
public interface Restore {

	/**
	 * Restore a flat element from the given file.
	 * @param fileName the name of the file to restore
	 * @return a flat element with the complete configuration
	 * @throws RestoreDomoConfException custom exception related to this procedure
	 */
	Flat restoreNow(String fileName) throws RestoreDomoConfException;
}
