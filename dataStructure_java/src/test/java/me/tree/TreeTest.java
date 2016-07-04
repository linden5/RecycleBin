package me.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.util.*;

public class TreeTest {
    @Test
    public void binaryTreeTest() {
        BinarySearchTree<Integer> bt = new BinarySearchTree<Integer>();
        bt.insert(1);
        bt.insert(2);
        bt.insert(6);
        bt.insert(4);
        bt.insert(5);
        bt.insert(3);

        assertFalse(bt.isEmpty());
        assertEquals("123456", bt.printTree() );

        bt.remove(4);
        bt.remove(2);
        assertEquals("1356", bt.printTree() );
    }

    @Test
    public void expressionTreeTest() {
        ExpTree exp = new ExpTree("ab+cde+**");
        assertEquals("ab+cde+**", exp.printPostOrderTree() );
    }

    @Test
    public void avlTreeTest() {
        AvlTree<Integer> bt = new AvlTree<Integer>();
        bt.insert(5);
        bt.insert(6);
        bt.insert(4);
        bt.insert(2);
        bt.insert(19);
        bt.insert(3);

        assertEquals("2345619", bt.printAvlTree());
        assertFalse( bt.isEmpty() );

        bt.remove(4);
        bt.remove(2);
        assertEquals("35619", bt.printAvlTree());
    }

    @Test
    public void wordCountTest() {
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("wine");
        lst.add("fine");
        lst.add("line");
        lst.add("pine");
        lst.add("wind");
        lst.add("find");
        lst.add("nine");
        lst.add("vine");
        Map<String, List<String>> mp = WordCount.computedAdjacentWords(lst);
        WordCount.printHighChangeables(mp, 2);
        assertEquals(2, mp.get("find").size());

        mp = WordCount.computedAdjacentWords2(lst);
        WordCount.printHighChangeables(mp, 2);
        assertEquals(6, mp.get("fine").size());

        mp = WordCount.computedAdjacentWords3(lst);
        WordCount.printHighChangeables(mp, 2);
        assertEquals(5, mp.get("nine").size());
    }

    @Test
    public void splayTreeTest() {
        SplayTree<Integer> lst = new SplayTree<Integer>();
        lst.insert(1);
        lst.insert(2);
        lst.insert(10);
        lst.insert(4);
        lst.insert(3);
        lst.insert(6);
        lst.insert(7);
        lst.insert(8);

        assertEquals("121043678", lst.print());
        lst.visit(8);
        assertEquals("812436710", lst.print());
    }
}