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

    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        this.setLeftChild(node);
        if(node!=null)
            node.setParent(this);
        updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        this.setRightChild(node);
        if(node!=null)
            node.setParent(this);
        updateHeight();
    }


    public void updateHeight() {
        height = 1 + Math.max(height((AVLNode<E>) getLeftChild()), height((AVLNode<E>) getRightChild()));
    }

    /**
     * Returns difference between the height of left subtree and right subtree
     */
    public int balanceFactor() {
        return height((AVLNode<E>) getLeftChild()) -
                height((AVLNode<E>) getRightChild());
    }

// others public methods

}
