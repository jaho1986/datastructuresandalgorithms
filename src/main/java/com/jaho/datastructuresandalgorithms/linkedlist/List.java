package com.jaho.datastructuresandalgorithms.linkedlist;

public interface List <T extends Comparable<T>>{
    public void insert(T data);
    public void remove(T data);
    public void traverse();
    public int getSize();

    public Node<T> getMiddleNode();

    public void reverse();
}
