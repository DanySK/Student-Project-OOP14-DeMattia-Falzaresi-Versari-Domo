package exceptions;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * This class manage ours "custom" exceptions related to the flat
 *
 */
public class FlatException extends RuntimeException {

	private static final long serialVersionUID = 7521820571064994254L;
	private String [] args;
	
	/**
	 * The Constructor of this class.
	 * @param eS the parameter for the runtime exception constructor
	 * @param sArgs what's happened when the exception was thrown
	 */
	public FlatException(final String eS, final String[] sArgs) {
		super(eS);
		this.args = sArgs;
	}
	
	/**
	 * Override of the toString method to underline this.args.
	 */
	@Override
	public String toString() {
		String str = " Arguments Status : ";
		str = str + java.util.Arrays.toString(args);
		str = str + "\n" + super.toString();
		return str;
	}

}
