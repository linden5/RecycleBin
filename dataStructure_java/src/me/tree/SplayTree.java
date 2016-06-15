package me.tree;

public class SplayTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    public boolean visit(T element) {
        BinaryNode<T> oldRoot = root;

        int compareResult = element.compareTo(root.element);
        if (compareResult > 0) {
            root = visitRight(element, root);
        } else if (compareResult < 0) {
            root = visitLeft(element, root);
        }

        if (oldRoot == root) return false;
        return true;
    }

    public void print() {
        preOrderPrint(root);
    }

    private BinaryNode<T> visitRight( T element, BinaryNode<T> t) {
        if ( t.right == null ) return t;

        int rightResult = element.compareTo(t.right.element);

        if (rightResult > 0) {
            t.right = visitRight(element, t.right);
        } else if (rightResult < 0) {
            t.right = visitLeft(element, t.right);
        }
        t = rotateWithRightChild(t);

        return t;
    }

    private BinaryNode<T> visitLeft( T element, BinaryNode<T> t) {
        if ( t == null || t.left == null) return t;

        int leftResult = element.compareTo(t.left.element);

        if (leftResult > 0) {
            t.left = visitRight(element, t.left);
        } else if (leftResult < 0) {
            t.left = visitLeft(element, t.left);
        }
        t = rotateWithLeftChild(t);

        return t;
    }

    private BinaryNode<T> rotateWithLeftChild( BinaryNode<T> k2 ) {
        BinaryNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    private BinaryNode<T> rotateWithRightChild( BinaryNode<T> k1 ) {
        BinaryNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private BinaryNode<T> doubleWithLeftChild( BinaryNode<T> k3 ) {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    private void preOrderPrint(BinaryNode<T> t) {
        if (t == null) return;
        System.out.println(t.element);
        preOrderPrint(t.left);
        preOrderPrint(t.right);
    }
}