package me.list;

public class SingleLinkedListNoTail<T> implements Iterable<T>{
    private Node<T> head;

    public SingleLinkedListNoTail() {
        head = new Node<T>(null, null);
    }

    public int size() {
        int size = 0;
        Node<T> current = head;
        while ( current.next != null ) {
            size++;
            current = current.next;
        }
        return size;
    }

    public void print() {
        java.util.Iterator<T> it = iterator();
        int idx = 0;
        while( it.hasNext() ) {
            System.out.println("List item " + idx + ":" + it.next().toString());
        }
    }

    public boolean contains(T data) {
        java.util.Iterator<T> it = iterator();
        while( it.hasNext() ) {
            if ( it.next().equals(data) )
                return true;
        }
        return false;
    }

    public boolean add(T data) {
        if ( contains( data ) ) 
            return false;
        Node<T> current = head;
        while( current.next != null ) {
            current = current.next;
        }

        current.next = new Node<T>(data, null);
        return true;
    }

    public boolean remove(T data) {
        java.util.Iterator<T> it = iterator();
        while( it.hasNext() ) {
            if ( it.next().equals(data) ) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public T remove(int idx) {
        return remove( getNode(idx - 1) );
    }

    public java.util.Iterator<T> iterator() {
        return new LinkedListNoTail();
    }

    private T remove(Node<T> prev) {
        T result = prev.next.data;
        prev.next = prev.next.next;
        return result;
    }

    private Node<T> getNode(int idx) {
        Node<T> p;
        if ( idx < 0 || idx > size() )
            throw new IndexOutOfBoundsException();

        p = head.next;
        for ( int i = 0; i < idx; i++) {
            p = p.next;
        }

        return p;
    }

    private class LinkedListNoTail implements java.util.Iterator<T> {
        Node<T> current = head;
        Node<T> prev = head;

        public boolean hasNext() {
            return current.next != null;
        }

        public T next() {
            prev = current;
            current = current.next;
            return current.data;
        }

        public void remove() {
            SingleLinkedListNoTail.this.remove( prev );
            current = prev;
        }
    }

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}