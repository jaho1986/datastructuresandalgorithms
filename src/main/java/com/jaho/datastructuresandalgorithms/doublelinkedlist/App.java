package com.jaho.datastructuresandalgorithms.doublelinkedlist;

import com.jaho.datastructuresandalgorithms.linkedlist.LinkedList;

public class App {
    public static void main (String[] args) {
        DoublyLinkedList<String> linkedList = new DoublyLinkedList<>();
        linkedList.insert("Alfredo");
        linkedList.insert("Beto");
        linkedList.insert("Carmen");
        linkedList.insert("Denise");

        linkedList.traverseForward();

        linkedList.traverseBackward();


    }
}
