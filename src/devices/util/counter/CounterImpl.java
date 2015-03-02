package devices.util.counter;

/**
 * 
 * @author Marco Versari
 *
 */
public class CounterImpl implements Counter {
	
	private int counter;
	
	/**
	 * Initialize the counter class.
	 * @param counterStart the point where to start the counter
	 */
	public CounterImpl(final int counterStart) {
		this.counter = counterStart;
	}
	
	@Override
	public int incCounter() {
		this.counter++;
		return getCounter();
	}

	@Override
	public int incCounter(final int step) {
		this.counter += step;		
		return getCounter();
	}

	@Override
	public int getCounter() {
		// TODO Auto-generated method stub
		return counter;
	}

}
