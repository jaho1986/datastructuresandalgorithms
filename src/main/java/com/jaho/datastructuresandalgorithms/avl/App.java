package com.jaho.datastructuresandalgorithms.avl;

public class App {

    public static void main (String[] args) {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(3);
        avl.insert(5);
        avl.insert(4);

        avl.traverse();
    }
}

