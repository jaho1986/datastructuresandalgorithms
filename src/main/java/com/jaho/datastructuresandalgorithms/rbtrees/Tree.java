package com.jaho.datastructuresandalgorithms.rbtrees;

public interface Tree<T> {
    void insert(T data);
    void remove(T data);
    void traverse();
}
