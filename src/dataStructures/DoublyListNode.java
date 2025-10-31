package dataStructures;

import java.io.Serializable;

/**
     * Double List Node Implementation
     * @author AED  Team
     * @version 1.0
     * @param <E> Generic Element
     * 
     */
class DoublyListNode<E> implements Serializable {


    private final long serialVersionUID = 0L;

		/**
         * Element stored in the node.
         */
        private E element;

        /**
         * (Pointer to) the previous node.
         */
        private DoublyListNode<E> previous;

        /**
         * (Pointer to) the next node.
         */
        private DoublyListNode<E> next;

        /**
         * 
         * @param theElement - The element to be contained in the node
         * @param thePrevious - the previous node
         * @param theNext - the next node
         */
        // time complexity : O(1)
        public DoublyListNode(E theElement, DoublyListNode<E> thePrevious,
                              DoublyListNode<E> theNext ) {
            element = theElement;
            previous = thePrevious;
            next = theNext;
        }

        /**
         * 
         * @param theElement to be contained in the node
         */
        public DoublyListNode(E theElement ) {
            this(theElement, null, null);
        }

        /**
         * 
         * @return the element contained in the node
         */
        // time complexity : O(1)
        public E getElement( ) {
            return element;
        }

        /**
         * 
         * @return the previous node
         */
        // time complexity : O(1)
        public DoublyListNode<E> getPrevious( ) {
            return previous;
        }

        /**
         * 
         * @return the next node
         */
        // time complexity : O(1)
        public DoublyListNode<E> getNext( ) {
            return next;
        }

        /**
         * 
         * @param newElement - New element to replace the current element
         */
        // time complexity : O(1)
        public void setElement( E newElement ) {
            this.element = newElement;
        }

        /**
         * 
         * @param newPrevious - node to replace the current previous node
         */
        // time complexity : O(1)
        public void setPrevious( DoublyListNode<E> newPrevious ) {
            this.previous = newPrevious;
        }

        /**
         * 
         * @param newNext - node to replace the next node
         */
        // time complexity : O(1)
        public void setNext( DoublyListNode<E> newNext ) {
            this.next = newNext;
        }
    }