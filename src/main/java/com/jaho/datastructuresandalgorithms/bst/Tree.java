package com.jaho.datastructuresandalgorithms.bst;

public interface Tree<T> {

    Node<T> getRoot();
    void insert (T data);
    void remove (T data);
    void traversal();
    T getMin();
    T getMax();
    Node<T> getKSmallest(Node<T> node, int k);
}
