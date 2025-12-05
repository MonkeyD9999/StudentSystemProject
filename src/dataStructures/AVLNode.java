package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }
    
    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element,parent,left,right);
        height = Math.max(left.getHeight(), right.getHeight()) + 1;
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        if(node!=null)
            node.setParent(this);
        updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        if(node!=null)
            node.setParent(this);
        updateHeight();
    }


    public void updateHeight() {
        this.height = 1 + Math.max(getLeftHeight(), getRightHeight());
    }

    /**
     * Returns difference between the height of left subtree and right subtree
     */
    public int balanceFactor() {
        return getLeftHeight() - getRightHeight();
    }

    private int getLeftHeight() {
        return (getLeftChild() == null) ? 0 : ((AVLNode<E>)getLeftChild()).getHeight();
    }

    private int getRightHeight() {
        return (getRightChild() == null) ? 0 : ((AVLNode<E>)getRightChild()).getHeight();
    }

// others public methods

}
