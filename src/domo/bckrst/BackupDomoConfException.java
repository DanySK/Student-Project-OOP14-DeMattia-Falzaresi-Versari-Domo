package domo.bckrst;
/**
 * 
 * @author  Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is the class for backup related exception
 *
 */
public class BackupDomoConfException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7846535753284073698L;
	private final String err;
	/**
	 * The constructor of the exception for showing backup related errors. 
	 * @param e the error message to show 
	 */
	public BackupDomoConfException(final String e) {
		super(e);
		this.err = e;
	}
	@Override
	public String toString() {
		return "Backup Got an Error: " + err;
	}
}
