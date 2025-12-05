package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentIndex;
    private Iterator<Map.Entry<K,V>> listIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        if (listIterator != null && listIterator.hasNext()) {
            return true;
        }

        int n = table.length;
        while (++currentIndex < n) {
            if (!table[currentIndex].isEmpty()) {
                listIterator = table[currentIndex].iterator();
                return listIterator.hasNext();
            }
        }

        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if (!hasNext())
            throw new NoSuchElementException();

        return listIterator.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        currentIndex = -1;
        listIterator = null;

        while (++currentIndex < table.length) {
            if (!table[currentIndex].isEmpty()) {
                listIterator = table[currentIndex].iterator();
                return;
            }
        }
    }
}

