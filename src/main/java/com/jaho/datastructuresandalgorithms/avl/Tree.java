package com.jaho.datastructuresandalgorithms.avl;

public interface Tree<T> {
    Node<T> getRoot();
    void insert (T data);
    void remove (T data);
    void traverse();
}
