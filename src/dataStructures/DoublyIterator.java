package dataStructures;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class DoublyIterator<E> implements Iterator<E> {
    static final long serialVersionUID = 0L;
    /**
     * Node with the first element in the iteration.
     */
    protected DoublyListNode<E> firstNode;

    /**
     * Node with the next element in the iteration.
     */
    DoublyListNode<E> nextToReturn;


    /**
     * DoublyIterator constructor
     *
     * @param first - Node with the first element of the iteration
     */
    // time complexity : O(1)
    public DoublyIterator(DoublyListNode<E> first) {
        this.firstNode = first;
        rewind();
    }
    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    // time complexity : O(1)
    public E next( ){
        if ( !this.hasNext() )
            throw new NoSuchElementException();

        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    /**
     * Restart the iterator
     */
    // time complexity : O(1)
    public void rewind() {
        nextToReturn = firstNode;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements
     */
    // time complexity : O(1)
    public boolean hasNext( ) {
        return nextToReturn != null;
    }


}
