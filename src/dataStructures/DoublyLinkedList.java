package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Doubly Linked List
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class DoublyLinkedList<E> implements TwoWayList<E>,Serializable {
    private static final long serialVersionUID = 0L;
	/**
     *  Node at the head of the list.
     */
    private transient DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private transient DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    // time complexity : O(1)
    public DoublyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // escreve os campos normais (não temos aqui, mas é boa prática)
        oos.writeInt(currentSize); // escreve o tamanho
        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement()); // escreve cada elemento
            node = node.getNext();
        }
        oos.flush();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // lê os campos normais
        int size = ois.readInt(); // lê o tamanho
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addLast(element); // recria os nós
        }
    }


    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    // time complexity : O(1)
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */
    // time complexity : O(1)
    public int size() {
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */
    // time complexity : O(1)
    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    // time complexity : O(1)
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     */
    // time complexity : O(1)
    public void addFirst( E element ) {
        DoublyListNode<E> newNode = new DoublyListNode<E>(element, null, head);
        if (this.isEmpty())
            tail = newNode;
        else
            head.setPrevious(newNode);

        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     */
    // time complexity : O(1)
    public void addLast( E element ) {
        DoublyListNode<E> newNode = new DoublyListNode<E>(element, tail, null);
        if (this.isEmpty()){
            head = newNode;
        }
        else{
            tail.setNext(newNode);
        }
        tail = newNode;
        currentSize++;
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    // time complexity : O(1)
    public E getFirst( ) {
        if (!this.isEmpty())
            throw new NoSuchElementException();

        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    // time complexity : O(1)
    public E getLast( ) {
        if (this.isEmpty())
            throw new NoSuchElementException();

        return tail.getElement();
    }


    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    // time complexity : O(n)
    public E get( int position ) {
        if( position < 0 || position > currentSize ) {
            throw new InvalidPositionException();
        }

        DoublyListNode<E> node;
        if ( position <= ( currentSize - 1 ) / 2 ) {
            node = head;
            for (int i = 0; i < position; i++)
                node = node.getNext();
        }
        else {
            node = tail;
            for (int i = currentSize-1; i > position; i--)
                node = node.getPrevious();
        }
        return node.getElement();
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    // time complexity : O(n)
    public int indexOf( E element ) {
        DoublyListNode<E> node = head;
        int counter = 0;
        while(node != null && !node.getElement().equals(element)) {
            node = node.getNext();
            counter++;
        }
        return node == null ? -1 : counter;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    // time complexity : O(n)
    public void add( int position, E element ) {
        if ( position < 0 || position > currentSize )
            throw new InvalidPositionException();

        if ( position == 0 )
            this.addFirst(element);
        else if ( position == currentSize )
            this.addLast(element);
        else {
            DoublyListNode<E> prevNode = this.getNode(position-1);
            DoublyListNode<E> nextNode = prevNode.getNext();
            DoublyListNode<E> newNode = new DoublyListNode<>(element, prevNode, nextNode);
            prevNode.setNext(newNode);
            nextNode.setPrevious(newNode);
            newNode.setNext(nextNode);
            newNode.setPrevious(prevNode);
            currentSize++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    // time complexity : O(1)
    public E removeFirst( ) {
        if (this.isEmpty())
            throw new NoSuchElementException();

        E element = head.getElement();
        head = head.getNext();
        if ( head == null )
            tail = null;
        else
            head.setPrevious(null);
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    // time complexity : O(1)
    public E removeLast( ) {
        if (this.isEmpty())
            throw new NoSuchElementException();

        E element = tail.getElement();
        tail = tail.getPrevious();
        if (tail == null)
            head = null;
        else
            tail.setNext(null);
        currentSize--;
        return element;
    }

    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    // time complexity : O(1)
    public E remove( int position ) {
        if ( position < 0 || position >= currentSize )
            throw new InvalidPositionException();

        if ( position == 0 )
            return this.removeFirst();
        else if ( position == currentSize - 1 )
            return this.removeLast();
        else {
            DoublyListNode<E> node = getNode(position);
            DoublyListNode<E> prevNode = node.getPrevious();
            DoublyListNode<E> nextNode = node.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrevious(prevNode);
            currentSize--;
            return node.getElement();
        }
    }

    // time complexity : O(n)
    private DoublyListNode<E> getNode( int position ) {
        if( position < 0 || position > currentSize ) {
            throw new InvalidPositionException();
        }

        DoublyListNode<E> node;
        if ( position <= ( currentSize - 1 ) / 2 ) {
            node = head;
            for (int i = 0; i < position; i++)
                node = node.getNext();
        }
        else {
            node = tail;
            for (int i = currentSize-1; i > position; i--)
                node = node.getPrevious();
        }
        return node;
    }

}
