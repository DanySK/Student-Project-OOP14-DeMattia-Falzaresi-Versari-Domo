package mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 *This is the view interface, is used to define methods available for the implementation of our view,
 *this is a simple view so only the "set observer method is public"
 */
public interface ViewInterface {

	/**	 This Method set the observer to be used.
	 * 
	 * @param observer to use with this instance of the view
	 */
	void setObserver(final AbstractObserverInterface observer);
}
