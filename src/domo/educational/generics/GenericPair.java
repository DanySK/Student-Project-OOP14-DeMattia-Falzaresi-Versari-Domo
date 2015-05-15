package domo.educational.generics;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 *  
 * A class that permit to create a generic pair object, the difference with "PairImpl" is 
 * the generic type of its arguments, whit this type of class the user decide the type of the parameter when the object is created
 * a so is not necessary to create different classes for different object type
 * 
 * @param <X> the first element in the pair
 * @param <Y> the second element in the pair
 */
public interface GenericPair<X, Y> {
	
	/**
	 * Get the first element.
	 * @return the first element.
	 */
	X getFirst();
	
	/**
	 * Get the second element.
	 * @return the second element.
	 */
	Y getSecond();
}
