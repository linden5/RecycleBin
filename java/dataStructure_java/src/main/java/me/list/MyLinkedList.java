package me.list;

public class MyLinkedList<AnyType> implements Iterable<AnyType> {

    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;

    private static class Node<AnyType> {
        public AnyType data;
        public Node<AnyType> prev;
        public Node<AnyType> next;

        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n ) {
            data = d;
            prev = p;
            next = n;
        }
    }

    public MyLinkedList() {
        clear();
    }

    public void clear() {
        beginMarker = new Node<AnyType>( null, null, null );
        endMarker = new Node<AnyType>( null, beginMarker, null );
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add( AnyType x ) {
        add( size(), x );
        return true;
    }

    public void add( int idx, AnyType x ) {
        addBefore( getNode( idx ), x );
    }

    public AnyType get( int idx ) {
        return getNode( idx ).data;
    }

    public AnyType set( int idx, AnyType newVal ) {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    public AnyType remove( int idx ) {
        return remove( getNode( idx ) );
    }

    public void exchange( int idx1, int idx2) {
        Node<AnyType> node1 = getNode( idx1 );
        Node<AnyType> node2 = getNode( idx2 );

        Node<AnyType> node1Next = node1.next;
        Node<AnyType> node1Prev = node1.prev;

        if (node1.next == node2) {
            node1.prev.next = node2;
            node2.next.prev = node1;

            node1.prev = node2;
            node1.next = node2.next;

            node2.prev = node1Prev;
            node2.next = node1;
        } else if (node1.prev == node2) {
            node2.prev.next = node1;
            node1.next.prev = node2;

            node1.prev = node2.prev;
            node1.next = node2;

            node2.prev = node1;
            node2.next = node1Next;            
        } else {
            node1.prev.next = node2;
            node2.prev.next = node1;

            node1.next.prev = node2;
            node2.next.prev = node1;

            node1.next = node2.next;
            node1.prev = node2.prev;

            node2.next = node1Next;
            node2.prev = node1Prev;            
        }
    }

    public boolean contains(AnyType data) {
        LinkedListIterator it = new LinkedListIterator();
        while ( it.hasNext() ) {
            if ( it.next().equals( data ) )
                return true;
        }

        return false;
    }

    public void removeAll( Iterable<? extends AnyType> items ) {
        java.util.Iterator<? extends AnyType> it = items.iterator();
        java.util.Iterator<AnyType> thisIt;

        AnyType current, thisPrev, thisCurrent = null;

        while( it.hasNext() ) {
            current = it.next();
            thisIt = iterator();
            while( thisIt.hasNext() ) {
                thisCurrent = thisIt.next();
                if ( thisCurrent.equals(current) ) {
                    thisIt.remove();
                }
            }
        }
    }

    private void addBefore( Node<AnyType> p, AnyType x ) {
        Node<AnyType> newNode = new Node<AnyType>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;
        theSize++;
        modCount++;
    }

    private AnyType remove( Node<AnyType> p ) {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        p.next = null;
        p.prev = null;

        theSize--;
        modCount++;
        return p.data;
    }

    private Node<AnyType> getNode( int idx ) {
        Node<AnyType> p;
        if ( idx < 0 || idx > size() )
            throw new IndexOutOfBoundsException();

        if ( idx < size() / 2 ) {
            p = beginMarker.next;
            for ( int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = endMarker;
            for ( int i = size(); i > idx; i-- )
                p = p.prev;
        }

        return p;
    }

    public java.util.Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<AnyType> {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != endMarker;
        }

        public AnyType next() {
            if ( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException();
            if ( !hasNext() )
                throw new java.util.NoSuchElementException();

            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove() {
            if ( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException();
            if ( !okToRemove )
                throw new IllegalStateException();

            MyLinkedList.this.remove( current.prev );
            okToRemove = false;
            expectedModCount++;
        }
    } 
}