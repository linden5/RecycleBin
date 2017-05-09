package me.list;

public class CircularLinkedList<T> implements Iterable<T>{
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public CircularLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T data) {
        if (head == null && tail == null) {
            head = new Node(data, null);
            tail = head; 
        } else {
            tail.next = new Node(data, null);
            tail = tail.next;
            tail.next = head;
        }
        size++;
    }

    public T get(int idx) { 
        return getNode( idx ).data;
    }

    public void set(int idx, T data) {
        getNode( idx ).data = data;
    }

    public T remove(int idx) {
        return remove( getNode(idx - 1) );
    }

    public java.util.Iterator<T> iterator() {
        return new CircularIterator();
    }

    private T remove(Node<T> targetPrev) {
        T result = targetPrev.next.data;
        targetPrev.next = targetPrev.next.next;
        size--;
        return result;
    }

    private Node<T> getNode(int idx) {
        Node<T> p;
        if ( idx < 0 || idx > size() )
            throw new IndexOutOfBoundsException();

        p = head;
        for ( int i = 0; i < idx; i++) {
            p = p.next;
        }

        return p;
    }

    private class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private class CircularIterator implements java.util.Iterator<T> {
        Node<T> current = tail;
        Node<T> prev = null;

        public boolean hasNext() {
            return size != 0;
        }

        public T next() {
            prev = current;
            current = current.next;
            return current.data;
        }

        public void remove() {
            CircularLinkedList.this.remove(prev);
        }
    }
}