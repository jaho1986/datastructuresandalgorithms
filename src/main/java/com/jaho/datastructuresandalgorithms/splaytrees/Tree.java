package com.jaho.datastructuresandalgorithms.splaytrees;

public interface Tree<T> {
    void insert(T data);
    T find(T data);
    T getRootNode();
    void traverse();
}
