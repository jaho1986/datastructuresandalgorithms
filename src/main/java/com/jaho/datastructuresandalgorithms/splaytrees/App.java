package com.jaho.datastructuresandalgorithms.splaytrees;

import com.jaho.datastructuresandalgorithms.avl.AVLTree;

public class App {

    public static void main (String[] args) {
        Tree<Integer> splayTree = new SplayTree<>();
        splayTree.insert(1);
        splayTree.insert(10);
        splayTree.insert(4);
        splayTree.insert(5);
        splayTree.insert(-2);

        splayTree.find(5);
    }
}
