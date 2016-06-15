package me.tree;

import java.util.*;

public class TreeTest {
    public static void binaryTreeTest() {
        BinarySearchTree<Integer> bt = new BinarySearchTree<Integer>();
        bt.insert(1);
        bt.insert(2);
        bt.insert(6);
        bt.insert(4);
        bt.insert(5);
        bt.insert(3);

        bt.printTree();
        System.out.println(bt.isEmpty());
        bt.remove(4);
        bt.remove(2);
        bt.printTree();
    }

    public static void expressionTreeTest() {
        ExpTree exp = new ExpTree("ab+cde+**");
        exp.printPostOrderTree();
    }

    public static void avlTreeTest() {
        AvlTree<Integer> bt = new AvlTree<Integer>();
        bt.insert(5);
        bt.insert(6);
        bt.insert(4);
        bt.insert(2);
        bt.insert(19);
        bt.insert(3);

        bt.printAvlTree();
        System.out.println(bt.isEmpty());
        bt.remove(4);
        bt.remove(2);
        bt.printAvlTree();
    }

    public static void wordCountTest() {
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
        mp = WordCount.computedAdjacentWords2(lst);
        WordCount.printHighChangeables(mp, 2);
        mp = WordCount.computedAdjacentWords3(lst);
        WordCount.printHighChangeables(mp, 2);
    }

    public static void splayTreeTest() {
        SplayTree<Integer> lst = new SplayTree<Integer>();
        lst.insert(1);
        lst.insert(2);
        lst.insert(10);
        lst.insert(4);
        lst.insert(3);
        lst.insert(6);
        lst.insert(7);
        lst.insert(8);
        lst.print();
        System.out.println("------");
        lst.visit(8);
        lst.print();
    }

    public static void main(String[] args) {
        // binaryTreeTest();
        // expressionTreeTest();
        // avlTreeTest();
        // wordCountTest();
        splayTreeTest();
    }
}