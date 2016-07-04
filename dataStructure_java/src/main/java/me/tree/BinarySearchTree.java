package me.tree;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    protected BinaryNode<AnyType> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains( AnyType x ) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if ( isEmpty() ) throw new RuntimeException("Underflow");
        return findMin( root ).element;
    }

    public AnyType findMax() {
        if ( isEmpty() ) throw new RuntimeException("Underflow");
        return findMax( root ).element;
    }

    public void insert( AnyType x ) {
        root = insert( x, root );
    }

    public void remove( AnyType x ) {
        root = remove( x, root ); 
    }

    public String printTree() {
        if ( isEmpty() ) {
            System.out.println( "Empty tree" );
            return null;
        } else {
            return printTree( root );
        }
    }

    private boolean contains( AnyType x, BinaryNode<AnyType> t ) {
        if ( t == null ) 
            return false;

        int compareResult = x.compareTo( t.element );

        if ( compareResult < 0 )
            return contains( x, t.left );
        else if ( compareResult > 0)
            return contains( x, t.right );
        else
            return true;
    }

    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t ) {
        if (t == null)
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t ) {
        if ( t != null )
            while (t.right != null)
                t = t.right;

        return t; 
    }

    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t ) {
        if (t == null)
            return new BinaryNode<AnyType>( x, null, null );

        int compareResult = x.compareTo(t.element);

        if ( compareResult < 0 )
            t.left = insert( x, t.left );
        else if ( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;

        return t;
    }

    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t ) {
        if (t == null)
            return t; //Item not found, do nothing

        int compareResult = x.compareTo( t.element );

        if ( compareResult < 0 )
            t.left = remove( x, t.left );
        else if ( compareResult > 0 )
            t.right = remove( x, t.right );
        else if ( t.left != null && t.right != null ) {
            // t.element = findMin( t.right ).element;
            // t.right = remove( t.element, t.right );
            BinaryNode<AnyType> newT = removeMin(t.right);
            t.element = newT.element;
            t.right = newT.right;
        } else {
            t = ( t.left != null ) ? t.left : t.right;
        }

        return t;
    }

    private BinaryNode<AnyType> removeMin( BinaryNode<AnyType> root ) {
        BinaryNode<AnyType> t = root;

        if (t == null) return null;

        BinaryNode<AnyType> pre = t;
        while ( t.left != null ) {
            pre = t;
            t = t.left;
        }

        if ( t == pre ) return t;

        pre.left = t.right;

        return new BinaryNode<AnyType>(t.element, null, root);
    }

    private String printTree( BinaryNode<AnyType> t ) {
        StringBuilder sb = new StringBuilder();

        if (t != null) {
            sb.append( printTree( t.left ));
            System.out.println( t.element );
            sb.append( t.element);
            sb.append( printTree( t.right ));
        }
        return sb.toString();
    }

    protected static class BinaryNode<AnyType> {
        AnyType element;           // Data in the node
        BinaryNode<AnyType> left;  // Left child
        BinaryNode<AnyType> right; // Right child

        // Constructors
        BinaryNode( AnyType theElement ) {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }
    }
}