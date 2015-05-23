package domo.educational.mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This class manage ours "custom" exceptions related
 *
 */
public class CustomExceptions extends Exception {

	private static final long serialVersionUID = 1063662989458389142L;
	private String err;
	
	/**
	 * The constructor accept a String with the error Occurred.
	 * @param sErr the error occurred
	 */
	public CustomExceptions(final String sErr) {
		super(sErr);
		this.err = sErr;
	}
	
	@Override
	public String toString() {
		return "Error Occurred in the Procedure: " + this.err;
	}
}
