package domo.bckrst;
/**
 * 
 * @author  Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This is the class for crypter related exception
 *
 */
public class CrypterException extends Exception {
	private static final long serialVersionUID = -3298013204058102514L;
	private final String err;
	/**
	 * The constructor of the exception for showing backup related errors. 
	 * @param e the error message to show 
	 */
	public CrypterException(final String e) {
		super(e);
		this.err = e;
	}
	@Override
	public String toString() {
		return "Crypt procedure got an error: " + err;
	}
}
