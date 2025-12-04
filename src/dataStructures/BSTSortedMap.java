package dataStructures;

import dataStructures.exceptions.EmptyMapException;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    private BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        if (node == null)
            return null;

        int comp = key.compareTo(node.getElement().key());
        if (comp == 0)
            return node;
        else if (comp < 0)
            return getNode((BTNode<Entry<K,V>>) node.getLeftChild(), key);
        else
            return getNode((BTNode<Entry<K,V>>) node.getRightChild(), key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {

        if (root == null) {
            root = new BTNode<>(new Entry<>(key, value));
            currentSize++;
            return null;
        }

        BTNode<Entry<K,V>> node = (BTNode<Entry<K,V>>) root;
        BTNode<Entry<K,V>> parent = null;
        int comp = 0;

        while (node != null) {
            comp = key.compareTo(node.getElement().key());
            if (comp == 0) {
                V oldValue = node.getElement().value();
                node.setElement(new Entry<>(key, value));
                return oldValue;
            }

            parent = node;
            if(comp<0)
                node = (BTNode<Entry<K,V>>) node.getLeftChild();
            else
                node = (BTNode<Entry<K,V>>) node.getRightChild();
        }

        BTNode<Entry<K,V>> newNode = new BTNode<>(new Entry<>(key, value));
        if (comp < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);
        return null;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        BTNode<Entry<K,V>> node = (BTNode<Entry<K,V>>) root;
        BTNode<Entry<K,V>> parent = null;
        int comp = 0;

        // find node to remove
        while (node != null) {
            comp = key.compareTo(node.getElement().key());
            if (comp == 0) break;
            parent = node;
            if (comp < 0)
                node = (BTNode<Entry<K,V>>) node.getLeftChild();
            else
                node = (BTNode<Entry<K,V>>) node.getRightChild();
        }

        if (node == null) return null;
        V removedValue = node.getElement().value();

        // node with 2 children
        // successor is node after removed node, in order
        // switch removed with successor
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            BTNode<Entry<K, V>> successor = (BTNode<Entry<K, V>>) node.getRightChild();
            BTNode<Entry<K, V>> succParent = node;

            while (successor.getLeftChild() != null) {
                succParent = successor;
                successor = (BTNode<Entry<K, V>>) successor.getLeftChild();
            }

            node.setElement(successor.getElement());
            node = successor;
            parent = succParent;
        }

        // node with 1 or 0 child
        BTNode<Entry<K,V>> child = (node.getLeftChild() != null) ? (BTNode<Entry<K,V>>) node.getLeftChild() :(BTNode<Entry<K,V>>) node.getRightChild();

        if (parent == null) { root = child; }
        else if (parent.getLeftChild() == node) { parent.setLeftChild(child); }
        else { parent.setRightChild(child); }

        currentSize--;
        return removedValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
