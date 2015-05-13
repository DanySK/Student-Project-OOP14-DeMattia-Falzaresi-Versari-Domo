package domo.util.test;

public abstract class AbstractTestObserver implements AbstracTestInterface {

	protected DomoTest domoTest;
	
	/* (non-Javadoc)
	 * @see domo.util.test.AbstracTestInterface#sensorStateChange()
	 */
	public abstract void sensorStateChange();
}
