package me.tree;

public class AvlTree<T extends Comparable<? super T>> {
    AvlNode<T> root;

    public AvlTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printAvlTree() {
        inOrderPrint(root);
    }

    public void insert( T x ) {
        root = insert( x, root );
    }

    public void remove( T x ) {
        root = remove( x, root );
    }

    private int height( AvlNode<T> t ) {
        return t == null ? -1 : t.height;
    }

    private AvlNode<T> insert( T x, AvlNode<T> t ) {
        if ( t == null )
            return new AvlNode<T>(x);

        int compareResult = x.compareTo(t.element);

        if ( compareResult < 0 ) {
            t.left = insert( x, t.left );
            if ( height( t.left ) - height( t.right ) == 2 )
                if ( x.compareTo( t.left.element ) < 0 )
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
        } else if ( compareResult > 0 ) {
            t.right = insert( x, t.right );
            if ( height( t.right ) - height( t.left ) == 2 )
                if ( x.compareTo( t.right.element ) > 0 )
                    t = rotateWithRightChild( t );
                else 
                    t = doubleWithLeftChild( t );
        } else;

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    private AvlNode<T> remove( T x, AvlNode<T> t ) {
        if ( t == null ) return null;

        int compareResult = x.compareTo( t.element );

        if ( compareResult < 0 ) {
            t.left = remove(x, t.left);
            if ( height( t.right ) - height( t.left ) == 2 )
                if ( x.compareTo( t.left.element ) < 0 )
                    t = rotateWithRightChild(t);
                else
                    t = rotateWithLeftChild(t);
        } else if ( compareResult > 0 ) {
            t.right = remove(x, t.right);
            if ( height( t.left ) - height( t.right ) == 2 )
                if ( x.compareTo( t.right.element ) < 0 )
                    t = rotateWithRightChild(t);
                else
                    t = rotateWithLeftChild(t);
        } else if ( t.left != null && t.right != null ) {
            if ( height(t.right) > height(t.left) )
                t.height -= 1;
            AvlNode<T> newT = removeMin(t.right);
            t.element = newT.element;
            t.right = newT.right;
        } else {
            t = ( t.left != null ) ? t.left : t.right;
        }

        return t;
    }

    AvlNode<T> removeMin( AvlNode<T> root ) {
        AvlNode<T> t = root;

        if (t == null) return null;

        AvlNode<T> pre = t;
        while ( t.left != null ) {
            pre = t;
            t = t.left;
        }

        if ( t == pre ) return t;

        pre.left = t.right;

        return new AvlNode<T>(t.element, null, root);
    }

    private AvlNode<T> rotateWithLeftChild( AvlNode<T> k2 ) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    private AvlNode<T> rotateWithRightChild( AvlNode<T> k1 ) {
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.right ), height( k1.left ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    private AvlNode<T> doubleWithLeftChild( AvlNode<T> k3 ) {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    private void inOrderPrint( AvlNode<T> tree ) {
        if ( tree == null ) return;
        inOrderPrint(tree.left);
        System.out.println(tree.element);
        inOrderPrint(tree.right);
    }

    private static class AvlNode<T> {
        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;

        AvlNode( T theElement ) {
            this( theElement, null, null );
        }

        AvlNode( T theElement, AvlNode<T> lt, AvlNode<T> rt ) {
            element = theElement;
            left = lt;
            right = rt;
            height = 0;
        }
    }
}