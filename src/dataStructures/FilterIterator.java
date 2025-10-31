package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.Serializable;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E>, Serializable {
    private static final long serialVersionUID = 0L;

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
    // time complexity : O(n)
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
    // time complexity : O(1)
    public boolean hasNext() {
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    // time complexity : O(n)
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
    // time complexity : O(n)
    public void rewind() {
        iterator.rewind();
        advanceNext();
    }

    // time complexity : O(n)
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
