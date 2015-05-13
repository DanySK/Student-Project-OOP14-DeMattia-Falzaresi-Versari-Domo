package domo.util.test;

import domo.general.Flat;
/**
 * Domo test class public interface.
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public interface DomoTest {

	/**
	 * Set the observer.
	 * @param testObserver the observer that implements "AbstractTestObserver" class
	 */
	void setObserver(final AbstracTestInterface testObserver);
	
	
	/**
	 * Refresh the frame, ex when is added a sensor to the project.
	 * or a room is create
	 * @param flat the flat with the change
	 */
	void refresh(final Flat flat);
}
