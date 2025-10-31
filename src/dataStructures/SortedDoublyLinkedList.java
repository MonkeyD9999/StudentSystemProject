package dataStructures;

import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {
	private static final long serialVersionUID = 0L;
	
	/**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
    	head = null;
        tail = null;
        currentSize = 0;
        this.comparator = comparator;
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
            add(element); // recria os nós
        }
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
    	if (isEmpty())
            throw new NoSuchElementException();
    	
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
    	if (isEmpty())
            throw new NoSuchElementException();
    	
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
    	Iterator<E> it = iterator();
        while (it.hasNext()) {
            E current = it.next();
            int cmp = comparator.compare(element, current);
            if (cmp == 0)
                return current;
            else if (cmp < 0)
                return null;
        }
        return null;
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return get(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
    	DoublyListNode<E> newNode = new DoublyListNode<>(element, null, null);
    	if (isEmpty()) {
            head = newNode;
            tail = newNode; }
    	else {
    		DoublyListNode<E> current = head;
            DoublyListNode<E> previous = null;
    		
            while (current != null && comparator.compare(element, current.getElement()) >= 0) {
            	previous = current;
                current = current.getNext();
            }
            
            if (previous == null) {
            	newNode.setNext(head);
            	head.setPrevious(newNode);
            	head = newNode;
            }
            else if (current == null) {
            	newNode.setPrevious(tail);
            	tail.setNext(newNode);
            	tail = newNode;
            }
            else {
            	newNode.setNext(current);
            	newNode.setPrevious(previous);
            	previous.setNext(newNode);
            	current.setPrevious(newNode);
            }
    	}
    	currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
    	if(!contains(element)) return null;
    	
    	DoublyListNode<E> current = head;
        DoublyListNode<E> previous = null;
    	while (current != null && comparator.compare(element, current.getElement()) > 0) {
            previous = current;
            current = current.getNext();
        }
    	
    	E elementRemoved = current.getElement();
    	
    	if (previous == null) {
    		head = current.getNext();
    		if(head != null)
    			head.setPrevious(null);
    		else
    			tail = null;
    	}
    	else if(current.getNext() == null) {
    		previous.setNext(null);
    		tail = previous;
    	}
    	else {
    		previous.setNext(current.getNext());
    		current.getNext().setPrevious(previous);
    	}
    	
    	currentSize--;
    	return elementRemoved;
    }
}
