package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    Iterator<E> iterator;

    /**
     *  Filter.
     */
    Predicate<E> criterion;

    /**
     * Node with the next element in the iteration.
     */
    E nextToReturn;


    /**
     *
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        iterator = list;
        this.criterion = criterion;
        advanceNext();
    }

    /**
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!hasNext())
        	throw new NoSuchElementException();
        
        E element = nextToReturn;
        advanceNext();
        
        return element;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        iterator.rewind();
        advanceNext();
    }
    
    private void advanceNext() {
    	nextToReturn = null;
    	
    	while(iterator.hasNext()) {
    		E candidate = iterator.next();
    		if(criterion.check(candidate)) {
    			nextToReturn = candidate;
    			break;
    		}
    	}
    }

}
