package domo.educational.mvc;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *
 */
public interface ViewInterface {

	/**
	 * 
	 * @param text -
	 */
	void updateView(final String text);
	
	/**
	 * 
	 * @param observer -
	 */
	void setObserver(final AbstractObserverInterface observer);
}
