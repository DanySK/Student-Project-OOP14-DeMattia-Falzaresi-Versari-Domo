package domo.educational.mvc;

/**
 * 
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
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
