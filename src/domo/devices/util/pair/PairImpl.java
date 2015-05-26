package domo.devices.util.pair;

/**
 * A class that permit to create a Pair object.
 * 
 * @author Marco Versari 
 * 
 * @param <K> first value type.
 * @param <L> second value type.
 */
public class PairImpl<K, L> implements Pair<K, L> {

    private final K first;
    private final L second;
    
    /**
     * Create a Pair object.
     * @param pFirst the first element.
     * @param pSecond the second element.
     */
    public PairImpl(final K pFirst, final L pSecond) {
        this.first = pFirst;
        this.second = pSecond;
     }

    @Override
    public K getFirst() {
        return first;
    }

    @Override
    public L getSecond() {
        return second;
    }

}
