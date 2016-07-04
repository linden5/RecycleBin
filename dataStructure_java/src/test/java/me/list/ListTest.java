package me.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.util.Iterator;

public class ListTest {
    @Test
    public void myArrayListTest() {
        MyArrayList<Integer> myArrayList = new MyArrayList();
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add(3);

        MyArrayList<Integer> myArrayList2 = new MyArrayList();
        myArrayList2.add(4);
        myArrayList2.add(5);
        myArrayList2.add(6);

        assertEquals(2, myArrayList.get(1).intValue() );

        myArrayList.addAll( myArrayList2 );

        Iterator<Integer> it = myArrayList.iterator();
        int i = 0;
        while ( it.hasNext() ) {
            i++;
            assertEquals(i, it.next().intValue() );
        }
    }

    @Test
    public void myLinkedListTest() {
        MyLinkedList<String> myLinkedList = new MyLinkedList();
        myLinkedList.add("hello");
        myLinkedList.add("world");
        myLinkedList.add("fuck");
        myLinkedList.add("java");

        assertEquals( "fuck", myLinkedList.get(2) );
        assertTrue( myLinkedList.contains("java") );
        assertFalse( myLinkedList.contains("kk") );

        // Test exchange
        myLinkedList.exchange(2, 1);

        String[] expected = {"hello", "fuck", "world", "java"};
        Iterator<String> linkIt = myLinkedList.iterator();
        int i = 0;
        while ( linkIt.hasNext() ) {
            assertEquals( expected[i], linkIt.next() );
            i++;
        }

        // Test remove
        MyLinkedList myLinkedList2 = new MyLinkedList();
        myLinkedList2.add("a");
        myLinkedList2.add("b");
        myLinkedList2.add("fuck");
        myLinkedList2.add("java");

        myLinkedList.remove( 2 );

        expected = new String[]{"hello", "fuck", "java"};
        
        linkIt = myLinkedList.iterator();
        i = 0;
        while ( linkIt.hasNext() ) {
            assertEquals( expected[i], linkIt.next() );
            i++;
        }


        // Test removeAll
        myLinkedList.add("hehe");
        myLinkedList.removeAll( myLinkedList2 );

        expected = new String[]{"hello", "hehe"};
        linkIt = myLinkedList.iterator();
        i = 0;
        while ( linkIt.hasNext() ) {
            assertEquals( expected[i], linkIt.next() );
            i++;
        }
    }

    @Test
    public void myStackTest() {
        MyStack myStack = new MyStack();
        myStack.push("hello");
        myStack.push("world");

        assertFalse( myStack.isEmpty() );
        assertEquals( "world", myStack.top() );
        assertEquals( "world", myStack.pop() );
        assertEquals( "hello", myStack.pop() );
        assertTrue( myStack.isEmpty() );
    }

    @Test
    public void arithmeticTest() throws Exception{
        String exp = "1 +12* (4 -2) + 8 /2";
        String revPolish = FourOperations.reversePolishString(exp);
        assertEquals("1 12 4 2 - * + 8 2 / + ", revPolish);
        assertEquals(29, FourOperations.calculate(exp));
    }

    @Test
    public void myLinkedQueueTest(){
        MyLinkedQueue<String> queue = new MyLinkedQueue<String>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        assertEquals(3, queue.size());

        String[] expected = new String[]{"a", "b", "c"};
        int i = 0;
        while (queue.size() > 0) {
            assertEquals(expected[i], queue.dequeue());
            i++;
        }
    }

    @Test
    public void myArrayQueueTest() {
        MyArrayQueue<Integer> queue = new MyArrayQueue<Integer>();
        for (int i = 0; i < 16; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 16; i++) {
            assertEquals(i, queue.dequeue().intValue());
        }
    }

    @Test
    public void CircularTest() {
        CircularLinkedList<Integer> cirList = new CircularLinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            cirList.add(i);
        }

        Iterator<Integer> it = cirList.iterator();
        int i = 0;
        while( it.hasNext() ) {
            assertEquals(i, it.next().intValue());
            it.remove();
            i++;
        }
    }

    @Test
    public void SingleLinkedListNoTailTest() {
        SingleLinkedListNoTail<Integer> list = 
                new SingleLinkedListNoTail<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        list.remove(new Integer(2));
        list.remove(new Integer(5));
        list.add(new Integer(1));
        list.add(new Integer(2));
        int[] expected = new int[]{0, 1, 3, 4, 2};

        Iterator<Integer> it = list.iterator();
        int i = 0;
        while( it.hasNext() ) {
            assertEquals(expected[i], it.next().intValue());
            it.remove();
            i++;
        }
    }
}
