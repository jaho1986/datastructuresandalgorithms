package com.jaho.datastructuresandalgorithms.avl;

import com.jaho.datastructuresandalgorithms.bst.BinarySearchTree;

public class App {

    public static void main (String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(12);
        bst.insert(99);
        bst.insert(54);
        bst.insert(93);
        bst.insert(45);

        bst.traversal();
    }
}
