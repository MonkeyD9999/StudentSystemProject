package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Array Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    static final long serialVersionUID = 0L;
    private E[] elems;
    private int counter;
    private int current;

    // time complexity : O(1)
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    // time complexity : O(1)
    @Override
    public void rewind() {
       current = 0;
    }

    // time complexity : O(1)
    @Override
    public boolean hasNext() {
	    return current < counter;
    }

    // time complexity : O(1)
    @Override
    public E next() {
	    if (!hasNext()) { throw new NoSuchElementException(); }
        return elems[current++];
    }

}
