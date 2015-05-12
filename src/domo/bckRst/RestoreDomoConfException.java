package domo.bckRst;
/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is the class for restore related exception
 *
 */
public class RestoreDomoConfException extends Exception {

	private static final long serialVersionUID = 1L;
	private String err;
	/**
	 * The constructor of the exception for showing backup related errors. 
	 * @param e the error message to showe 
	 */
	public RestoreDomoConfException(final String e) {
		this.err = e;
	}
	
	@Override
	public String toString() {
		return "Error during restore procedure: " + err;
	}
}
