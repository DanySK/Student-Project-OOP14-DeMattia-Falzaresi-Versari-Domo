package devices.util.counter;

/**
 * 
 * @author Marco Versari
 *
 */
public interface Counter {
	/**
	 * Increments the counter by one step.
	 * @return counter
	 */
	int incCounter();
	
	/**
	 * increments the counter by step.
	 * @param step number of steps to increase.
	 * @return counter
	 */
	int incCounter(int step);
	
	/**
	 * Get the counter status.
	 * @return counter
	 */
	int getCounter();
}
