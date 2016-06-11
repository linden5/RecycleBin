package me.tree;

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

    public static void main(String[] args) {
        // binaryTreeTest();
        // expressionTreeTest();
        avlTreeTest();
    }
}