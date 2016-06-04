public class MyArrayQueue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] theItems;
    private int theSize;
    private int head;
    private int rear;

    public MyArrayQueue() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public T get(int idx) {
        int index = head + idx;
        if ( index > theItems.length - 1 ) {
            index -= theItems.length;
        }

        if ( index > rear - 1 ) return null;
        return theItems[ index ];
    }

    public void enqueue(T data) {
        theItems[rear++] = data;
        theSize++;
        if ( theSize ==  theItems.length ) {
            ensureCapacity( 2 * theSize + 1 );
        } else {
            rear = ensureBounds( rear );
        }
    }

    public T dequeue() {
        if ( isEmpty() ) return null;

        T headValue = theItems[head++];
        theSize--;
        head = ensureBounds( head );
        return headValue;
    }

    public void ensureCapacity(int newCapaticy) {
        if (newCapaticy < theSize) return;

        T[] old = theItems;
        theItems = (T[])new Object[newCapaticy];
        for (int i = 0; i < theSize; i++) {
            theItems[i] = old[ ensureBounds( head + i ) ];
        }
        head = 0;
        rear = head + theSize;
    }

    private int ensureBounds(int idx) {
        if (idx > theItems.length - 1)
            return idx -= theItems.length;
        return idx;
    }
}