package domo.educational.generics;

/**
 * 
 * @author Stefano Falzaresi Stefano.Falzaresi2@studio.unibo.it
 * 
 * A class that permit to create a Pair object.
 */
public class PairImpl implements Pair {

    private final int first;
    private final int second;
    
    /**
     * Create a Pair object.
     * @param pFirst the first element.
     * @param pSecond the second element.
     */
    public PairImpl(int pFirst, int pSecond) {
        this.first = pFirst;
        this.second = pSecond;
     }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

}
