package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{
    /**
 	* Performs a single left rotation rooted at z node.
 	* Node y was a  right  child  of z before the  rotation,
 	* then z becomes the left child of y after the rotation.
 	* @param z - root of the rotation
	 * @pre: z has a right child
 	*/
	protected void rotateLeft( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getRightChild();
        BTNode<Entry<K,V>> leftSubTree = (BTNode<Entry<K,V>>) y.getLeftChild();

        z.setRightChild(leftSubTree);

        y.setLeftChild(z);
        replaceParentLink(z, y);
	}

     /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getLeftChild();
        BTNode<Entry<K,V>> rightSubTree = (BTNode<Entry<K,V>>) y.getRightChild();

        z.setLeftChild(rightSubTree);
        y.setRightChild(z);
        replaceParentLink(z, y);
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) x.getParent();
        BTNode<Entry<K,V>> z = (BTNode<Entry<K,V>>) y.getParent();
        // z > y > x (zigzig dir)
        if (y == z.getLeftChild() && x == y.getLeftChild()) {
            rotateRight(z);
            return y;
        }
        // z < y < x (zigzig esq)
        if (y == z.getRightChild() && x == y.getRightChild()) {
            rotateLeft(z);
            return y;
        }
        // x no meio
        if (y == z.getLeftChild() && x == y.getRightChild()) {
            rotateLeft(y);
            rotateRight(z);
            return x;
        }
        if (y == z.getRightChild() && x == y.getLeftChild()) {
            rotateRight(y);
            rotateLeft(z);
            return x;
        }

        return null;
    }


    private void replaceParentLink(BTNode<Entry<K,V>> oldNode, BTNode<Entry<K,V>> newNode) {
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) oldNode.getParent();
        newNode.setParent(parent);

        if (parent == null)
            root = newNode;
        else if (parent.getLeftChild() == oldNode)
            parent.setLeftChild(newNode);
        else
            parent.setRightChild(newNode);
    }
}
