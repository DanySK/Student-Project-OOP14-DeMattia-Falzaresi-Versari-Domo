package domo.bckrst2;
	/**
	 * 
	 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
	 * 
	 * This is the class for backup related exception
	 *
	 */
public class BackupDomoConfException extends Exception {
	
	private static final long serialVersionUID = 5705352020144465859L;
	private String err;
	
	/**
	 * The constructor of the exception for showing backup related errors. 
	 * @param e the error message to show
	 */
	public BackupDomoConfException(final String e) {
		this.err = e;
	}
	
	@Override
	public String toString() {
		return "Backup Got an Error: " + err;
	}
}
