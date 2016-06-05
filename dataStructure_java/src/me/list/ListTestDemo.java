package me.list;

import java.util.Iterator;

public class ListTestDemo {

    // MyArrayList Test
    public static void myArrayListTest() {
        MyArrayList myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);

        MyArrayList myArrayList2 = new MyArrayList();
        myArrayList2.add(4);
        myArrayList2.add(5);
        myArrayList2.add(6);
        // System.out.println( myArrayList.get(1) );

        myArrayList.addAll( myArrayList2 );

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
        myLinkedList.add("java");

        MyLinkedList myLinkedList2 = new MyLinkedList();
        myLinkedList2.add("a");
        myLinkedList2.add("b");
        myLinkedList2.add("fuck");
        myLinkedList2.add("java");

        myLinkedList.removeAll( myLinkedList2 );
        // System.out.println( myLinkedList.get(2) );
        // System.out.println( myLinkedList.contains("java") );
        // System.out.println( myLinkedList.contains("kk") );
        // System.out.println( "------" );

        Iterator<String> linkIt = myLinkedList.iterator();
        while ( linkIt.hasNext() ) {
            System.out.println( linkIt.next() );
        } 

        // myLinkedList.exchange(2, 1);

        // linkIt = myLinkedList.iterator();
        // while ( linkIt.hasNext() ) {
        //     System.out.println( linkIt.next() );
        // }
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

    public static void CircularTest() {
        CircularLinkedList<Integer> cirList = new CircularLinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            cirList.add(i);
        }

        // cirList.remove(2);
        Iterator<Integer> it = cirList.iterator();
        while( it.hasNext() ) {
            System.out.println( it.next() );
            it.remove();
        }
    }

    public static void SingleLinkedListNoTailTest() {
        SingleLinkedListNoTail<Integer> list = 
                new SingleLinkedListNoTail<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        list.remove(new Integer(2));
        list.remove(new Integer(5));
        list.add(new Integer(1));
        list.add(new Integer(2));
        Iterator<Integer> it = list.iterator();
        while( it.hasNext() ) {
            System.out.println( it.next() );
            it.remove();
        }
    }

    public static void main( String[] args ) throws Exception{
        // myArrayListTest();
        // myLinkedListTest();
        // myStackTest();
        // arithmeticTest();
        // myLinkedQueueTest();
        // myArrayQueueTest();
        // CircularTest();
        // new Josephus(5, 0).startGame();
        SingleLinkedListNoTailTest();
    }
}