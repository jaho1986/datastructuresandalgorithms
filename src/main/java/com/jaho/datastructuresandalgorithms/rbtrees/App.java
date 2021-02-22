package com.jaho.datastructuresandalgorithms.rbtrees;

import com.jaho.datastructuresandalgorithms.avl.AVLTree;

public class App {

    public static void main (String[] args) {
        Tree<Integer> avl = new RedBlackTree<>();
        avl.insert(5);
        avl.insert(2);
        avl.insert(3);
        avl.insert(4);
        avl.traverse();
    }
}

