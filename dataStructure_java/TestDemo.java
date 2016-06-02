import java.util.Iterator;

public class TestDemo {
    public static void main( String[] args ) {
        // MyArrayList Test
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);

        Iterator<Integer> it = myArrayList.iterator();

        while ( it.hasNext() ) {
            System.out.println( it.next() );
        }

        // MyLinkedList Test
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.add("hello");
        myLinkedList.add("world");

        Iterator<String> linkIt = myLinkedList.iterator();

        while ( linkIt.hasNext() ) {
            System.out.println( linkIt.next() );
        }        
    }
}