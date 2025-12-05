package dataStructures;
/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends Tree<E> {

    /**
     * Returns the height of the tree.
     */
    public int getHeight() {
        if(isEmpty())
            return 0;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * @return
     */
    BTNode<E> furtherLeftElement() {
        if(root == null) return null;
        BTNode<E> furtherLeft = (BTNode<E>) root;
        while(furtherLeft.getLeftChild()!=null) {
            furtherLeft= (BTNode<E>) furtherLeft.getLeftChild();
        }
        return furtherLeft;
    }

    /**
     * Return the further right node of the tree
     * @return
     */
    BTNode<E> furtherRightElement() {
        if(root == null) return null;
        BTNode<E> furtherRight = (BTNode<E>) root;
        while(furtherRight.getRightChild()!=null) {
            furtherRight= (BTNode<E>) furtherRight.getRightChild();
        }
        return furtherRight;
    }

   //new methods: Left as an exercise.
}
