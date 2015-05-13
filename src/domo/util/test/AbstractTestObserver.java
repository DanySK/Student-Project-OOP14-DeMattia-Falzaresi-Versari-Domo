package domo.util.test;

/**
 * Abstract class needed to tell to the observer when refresh the view.
 * @author Simone De Mattia simone.demattia@studio.unibo.it
 *
 */
public abstract class AbstractTestObserver implements AbstracTestInterface {

	/**
	 * Reference to DomoTest interface.
	 */

	private DomoTest domoTest;
	
	/**
	 * Tell to the observer that sensors change state.
	 * 
	 * NB
	 * Because the graphic interface structure the observer
	 * is called every time one JCheckBox change state than 
	 * every call is for one sensor only
	 * 
	 */
	public abstract void sensorStateChange();
	
	/**
	 * set domoTest reference.
	 * @param domo
	 */
	protected void setDomoTest(final DomoTest domo) {
		this.domoTest = domo;
	}
}
