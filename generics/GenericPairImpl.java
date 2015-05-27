package domo.educational.generics;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * A class that permit to create a generic pair object, the difference with "PairImpl" is 
 * the generic type of its arguments, whit this type of class the user decide the type of the parameter when the object is created
 * a so is not necessary to create different classes for different object type
 * 
 * @param <X> 
 * @param <Y> 
 */
public class GenericPairImpl<X, Y> implements GenericPair<X, Y> {

    private final X first;
    private final Y second;
    
    /**
     * Create a generic Pair object.
     * @param pFirst the first element.
     * @param pSecond the second element.
     */
    public GenericPairImpl(X pFirst, Y pSecond) {
        this.first = pFirst;
        this.second = pSecond;
     }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

}
