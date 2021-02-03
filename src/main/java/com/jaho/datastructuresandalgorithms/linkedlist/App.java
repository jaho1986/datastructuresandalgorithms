package com.jaho.datastructuresandalgorithms.linkedlist;

public class App {
    public static void main (String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.insert("Alfredo");
        linkedList.insert("Beto");
        linkedList.insert("Carmen");
        linkedList.insert("Denise");

        linkedList.traverse();
        linkedList.getSize();

        linkedList.remove("Denise");
        linkedList.traverse();
        linkedList.getSize();

    }
}
