package com.jaho.datastructuresandalgorithms.stacks;

import com.jaho.datastructuresandalgorithms.linkedlist.LinkedList;

public class App {

    public static void main (String[] args) {
        StackWithLinkedList<String> linkedList = new StackWithLinkedList<>();
        linkedList.push("Alfredo");
        linkedList.push("Beto");
        linkedList.push("Carmen");
        linkedList.push("Denise");

        linkedList.getSize();

        linkedList.pop("Denise");
        linkedList.getSize();


        StackWithArrays<Integer> linkedList2 = new StackWithArrays<>();
        linkedList2.push(1);
        linkedList2.push(2);
        linkedList2.push(3);
        linkedList2.push(4);

        linkedList2.getSize();

        linkedList2.pop();
        linkedList2.pop();
        linkedList2.pop();
        linkedList2.getSize();

    }
}
