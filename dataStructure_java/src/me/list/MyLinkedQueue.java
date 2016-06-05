package me.list;

public class MyLinkedQueue<Anytype> {
    private int theSize;
    private Node<Anytype> head;
    private Node<Anytype> rear;

    public MyLinkedQueue() {
        theSize = 0;
        head = new Node<Anytype>(null, null, null);
        rear = new Node<Anytype>(null, null, head);
        head.next = rear;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public Node<Anytype> getHead() {
        return head.next;
    }

    public Node<Anytype> getRear() {
        return rear.prev;
    }

    public Anytype get(int idx) {
        Node<Anytype> p;
        if (idx < 0 || idx > theSize) {
            throw new IndexOutOfBoundsException();
        }

        if (idx < theSize / 2) {
            p = head.next;
            for (int i = 0; i < idx; i++) {
                p = p.next;
            }
        } else {
            p = rear;
            for (int i = theSize; i > idx; i--) {
                p = p.prev;
            }
        }

        return p.data;
    }

    public void enqueue(Anytype data) {
        Node<Anytype> node = new Node<Anytype>(data, rear, rear.prev);
        rear.prev.next = node;
        rear.prev = node;
        theSize++;
    }

    public Anytype dequeue() {
        Node<Anytype> node = head.next;
        head.next = node.next;
        head.next.prev = head;
        theSize--;
        return node.data;  
    }

    private static class Node<Anytype> {
        private Anytype data;
        private Node<Anytype> next;
        private Node<Anytype> prev;

        public Node( Anytype data, Node<Anytype> next, Node<Anytype> prev ) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}