package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class TwoWayDoublyIterator<E> extends DoublyIterator<E> implements TwoWayIterator<E> {

    private static final long serialVersionUID = 0L;
    /**
     * Node with the first element in the iteration.
     */
    private DoublyListNode<E> lastNode;
    /**
     * Node with the previous element in the iteration.
     */
    private DoublyListNode<E> prevToReturn;

    /**
     * DoublyLLIterator constructor
     *
     * @param first - Node with the first element of the iteration
     * @param last  - Node with the last element of the iteration
     */
    // time complexity : O(1)
    public TwoWayDoublyIterator(DoublyListNode<E> first, DoublyListNode<E> last) {
        super(first);
        lastNode = last;
        this.rewind();
    }

    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements in the reverse direction
     */
    // time complexity : O(1)
    public boolean hasPrevious( ) {
        return prevToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    // time complexity : O(1)
    public E next( ){
        if ( !this.hasNext() )
            throw new NoSuchElementException();

        E element = nextToReturn.getElement();
        prevToReturn = nextToReturn.getPrevious();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    /**
     * Returns the previous element in the iteration.
     * @return previous element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    // time complexity : O(1)
    public E previous( ) {
        if ( !this.hasPrevious() )
            throw new NoSuchElementException();

        E element = prevToReturn.getElement();
        nextToReturn = prevToReturn;
        prevToReturn = prevToReturn.getPrevious();
        return element;
    }

    /**
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element
     */
    // time complexity : O(1)
    public void fullForward() {
        prevToReturn = lastNode;
        nextToReturn = null;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    // time complexity : O(1)
    public void rewind() {
        nextToReturn = firstNode;
        prevToReturn = null;
    }
}
