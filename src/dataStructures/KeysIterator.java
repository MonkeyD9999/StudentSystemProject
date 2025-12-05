package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {

    
     private Iterator<Map.Entry<E,?>> it;
    private Iterator<Map.Entry<E,?>> backup;


    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
       this.it=it;
       backup=it;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
	    return  it.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        return it.next().key();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        it=backup;
    }
}
