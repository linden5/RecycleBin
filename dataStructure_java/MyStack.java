public class MyStack<AnyType> {
    private static final int DEFAULT_CAPACITY = 10;
    private AnyType[] theArray;
    private int topOfStack;

    public MyStack() {
        topOfStack = -1;
        ensureCapacity(DEFAULT_CAPACITY);    
    }

    public boolean isEmpty() {
        return topOfStack == -1;
    }

    public boolean push( AnyType newVal ) {
        if ( ++topOfStack >= theArray.length ) 
            ensureCapacity( theArray.length * 2 + 1 );

        theArray[ topOfStack ] = newVal;
        return true;
    }

    public AnyType pop() {
        if (topOfStack == -1)
            return null;
        return theArray[ topOfStack-- ];        
    }

    public AnyType top() {
        if (topOfStack == -1)
            return null;
        return theArray[ topOfStack ];
    }

    public void ensureCapacity( int newCapacity ) {
        if ( theArray == null ) {
            theArray = (AnyType[]) new Object[ newCapacity ];
            return;
        } else if ( newCapacity <= theArray.length ) {
            return;
        }

        AnyType[] old = theArray;
        theArray = (AnyType[]) new Object[ newCapacity ];
        for ( int i = 0; i < theArray.length; i++ ) 
            theArray[ i ] = old[ i ];
    }
}