package com.jaho.datastructuresandalgorithms.doublelinkedlist;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DoublyLinkedList <T extends Comparable<T>> {

    private Node<T> head;
    private Node<T> tail;

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);

        //This is the first item in the linked list:
        if (this.tail == null) {
            //Both pointers are pointing to the new node:
            this.tail = newNode;
            this.head = newNode;
        } else {
            //We have to insert the new item to the end of the list:
            //We just have to manipulate the tail node and its references in O(1):
            newNode.setPreviousNode(this.tail);
            this.tail.setNextNode(newNode);
            this.tail = newNode;
        }
    }

    /**
     * Traversing forward:
     */
    public void traverseForward() {
        Node<T> actualNode = this.head;
        while (actualNode != null) {
            log.info(actualNode);
            actualNode = actualNode.getNextNode();
        }
    }

    /**
     * Traversing backward:
     */
    public void traverseBackward() {
        Node<T> actualNode = this.tail;
        while (actualNode != null) {
            log.info(actualNode);
            actualNode = actualNode.getPreviousNode();
        }
    }
}
