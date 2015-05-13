package domo.util.test;
/**
 * Interface of Abstract Test class.
 * @author Simone De Mattia - simopne.demattia@studio.unibo.it
 *
 */
public interface AbstracTestInterface {

	/**
	 * Tell to the observer that sensors change state.
	 * 
	 * NB
	 * Because the graphic interface structure the observer
	 * is called every time one JCheckBox change state than 
	 * every call is for one sensor only
	 * 
	 */
	void sensorStateChange();

}