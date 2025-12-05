package dataStructures;

/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public V insert(K key, V value) {
        if (root == null) {
            root = new AVLNode<>(new Entry<>(key, value));
            currentSize = 1;
            return value;
        }

        AVLNode<Entry<K,V>> node = (AVLNode<Entry<K,V>>) root;
        AVLNode<Entry<K,V>> parent = null;

        while (node != null) {
            parent = node;
            int cmp = key.compareTo(node.getElement().key());

            if (cmp == 0) {
                V old = node.getElement().value();
                node.setElement(new Entry<>(key, value));
                return old;
            }

            if (cmp < 0)
                node = (AVLNode<Entry<K,V>>) node.getLeftChild();
            else
                node = (AVLNode<Entry<K,V>>) node.getRightChild();
        }

        AVLNode<Entry<K,V>> newNode = new AVLNode<>(new Entry<>(key, value));
        newNode.setParent(parent);

        if (key.compareTo(parent.getElement().key()) < 0)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);
        currentSize++;

        rebalance(parent);

        return value;
    }

    private void rebalance(AVLNode<Entry<K,V>> node) {

        while (node != null) {

            node.updateHeight();

            int balanceFactor = node.balanceFactor();

            if (Math.abs(balanceFactor) > 1) {

                AVLNode<Entry<K,V>> y = tallerChild(node);
                if(y==null) System.out.println("y deu merda is null");
                AVLNode<Entry<K,V>> x = tallerChild(y);
                if(x==null) System.out.println("x deu merda is null");

                node = (AVLNode<Entry<K,V>>) restructure(x);

                AVLNode<Entry<K,V>> leftChild = (AVLNode<Entry<K,V>>) node.getLeftChild();
                AVLNode<Entry<K,V>> rightChild = (AVLNode<Entry<K,V>>) node.getRightChild();
                if (leftChild != null) { leftChild.updateHeight(); }
                if (rightChild != null) { rightChild.updateHeight(); }
                node.updateHeight();
            }

            node = (AVLNode<Entry<K,V>>) node.getParent();
        }
    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    public V remove(K key) {
        AVLNode<Entry<K, V>> node = (AVLNode<Entry<K, V>>) root;

        while (node != null) {
            int cmp = key.compareTo(node.getElement().key());
            if (cmp == 0) break;
            node = (cmp < 0)
                    ? (AVLNode<Entry<K, V>>) node.getLeftChild()
                    : (AVLNode<Entry<K, V>>) node.getRightChild();
        }

        if (node == null) return null;

        V oldValue = node.getElement().value();

        if (node.getLeftChild() != null && node.getRightChild() != null) {
            AVLNode<Entry<K, V>> successor = (AVLNode<Entry<K, V>>) node.getRightChild();
            while (successor.getLeftChild() != null) {
                successor = (AVLNode<Entry<K, V>>) successor.getLeftChild();
            }
            node.setElement(successor.getElement());
            node = successor;
        }

        AVLNode<Entry<K, V>> child = (AVLNode<Entry<K, V>>) ((node.getLeftChild() != null)
                ? node.getLeftChild() : node.getRightChild());
        AVLNode<Entry<K, V>> parent = (AVLNode<Entry<K, V>>) node.getParent();

        if (child != null) child.setParent(parent);

        if (parent == null) { root = child; }
        else if (parent.getLeftChild() == node) { parent.setLeftChild(child); }
        else { parent.setRightChild(child); }

        currentSize--;
        rebalance(parent);

        return oldValue;
    }

    private AVLNode<Entry<K, V>> tallerChild(AVLNode<Entry<K, V>> node) {
        AVLNode<Entry<K, V>> left = (AVLNode<Entry<K, V>>) node.getLeftChild();
        AVLNode<Entry<K, V>> right = (AVLNode<Entry<K, V>>) node.getRightChild();
        AVLNode<Entry<K, V>> parent = (AVLNode<Entry<K, V>>) node.getRightChild();

        int hLeft = (left == null) ? 0 : left.getHeight();
        int hRight = (right == null) ? 0 : right.getHeight();

        if (hLeft > hRight) return left;
        if (hRight > hLeft) return right;

        if (parent != null && node == (AVLNode<Entry<K, V>>) parent.getLeftChild()) {
            return left;
        } else {
            return right;
        }
    }


}
