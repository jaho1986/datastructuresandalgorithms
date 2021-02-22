package com.jaho.datastructuresandalgorithms.splaytrees;

public interface Tree<T> {
    void insert(T data);
    T find(T data, Node<T> node);
    void traverse();
}
