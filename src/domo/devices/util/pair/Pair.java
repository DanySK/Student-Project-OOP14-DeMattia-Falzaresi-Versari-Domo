package domo.devices.util.pair;

/**
 * An interfaces that permit to create a Pair object.
 * 
 * @author Marco Versari 
 * 
 * @param <K> first value type.
 * @param <L> second value type.
 */
public interface Pair<K, L> {
	/**
	 * Get the first element.
	 * @return the first element.
	 */
	K getFirst();
	
	/**
	 * Get the second element.
	 * @return the second element.
	 */
	L getSecond();
}
