import java.util.Iterator;

public class TestDemo {

    // MyArrayList Test
    public static void myArrayListTest() {
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);

        // System.out.println( myArrayList.get(1) );

        Iterator<Integer> it = myArrayList.iterator();
        while ( it.hasNext() ) {
            System.out.println( it.next() );
        }
    }

    // MyLinkedList Test
    public static void myLinkedListTest() {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.add("hello");
        myLinkedList.add("world");
        myLinkedList.add("fuck");

        System.out.println( myLinkedList.get(2) );

        Iterator<String> linkIt = myLinkedList.iterator();
        while ( linkIt.hasNext() ) {
            System.out.println( linkIt.next() );
        } 
    }

    // MyLinkedList Test
    public static void myStackTest() {
        MyStack myStack = new MyStack();
        myStack.push("hello");
        myStack.push("world");

        System.out.println(myStack.isEmpty());
        System.out.println(myStack.top());
        System.out.println(myStack.pop());
    }

    public static void arithmeticTest() throws Exception{
        String exp = "1 +12* (4 -2) + 8 /2";
        System.out.println("Original:" + exp);
        String revPolish = FourOperations.reversePolishString(exp);
        System.out.println("Reverse Polish:" + revPolish);
        System.out.println("Result:" + FourOperations.calculate(exp));
    }

    public static void myLinkedQueueTest(){
        MyLinkedQueue<String> queue = new MyLinkedQueue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        for (int i = 0; i < queue.size(); i++) {
            System.out.println( queue.get(i) );
        }
    }

    public static void myArrayQueueTest() {
        MyArrayQueue<Integer> queue = new MyArrayQueue<Integer>();
        for (int i = 0; i < 16; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 16; i++) {
            System.out.println( queue.dequeue() );
        }
    }

    public static void main( String[] args ) throws Exception{
        // myArrayListTest();
        // myLinkedListTest();
        // myStackTest();
        // arithmeticTest();
        // myLinkedQueueTest();
        myArrayQueueTest();
    }
}