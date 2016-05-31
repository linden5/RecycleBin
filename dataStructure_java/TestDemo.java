import java.util.Iterator;

public class TestDemo {
    public static void main( String[] args ) {
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);

        Iterator<Integer> it = myArrayList.iterator();

        while ( it.hasNext() ) {
            System.out.println( it.next() );
        }
    }
}