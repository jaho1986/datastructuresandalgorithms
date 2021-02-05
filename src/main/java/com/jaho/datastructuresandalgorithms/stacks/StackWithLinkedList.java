package com.jaho.datastructuresandalgorithms.stacks;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StackWithLinkedList <T extends Comparable<T>> {
    private Node<T> head;
    private int count;

    /**
     * Inserts a new item:
     * @param data
     */
    public void push(T data) {
        count++;
        if(head != null) {
            this.head = new Node<>(data);

        } else {
            Node<T> oldHead = this.head;
            this.head = new Node<>(data);
            this.head.setNextNode(oldHead);
        }
    }

    /**
     * Removes the las item we have inserted O(1):
     */
    public T pop(T data){
        T item = this.head.getData();
        this.head = this.head.getNextNode();
        count--;
        return item;
    }

    /**
     * Returns the size O(1):
     * @return
     */
    public Integer getSize(){
        log.info(this.count);
        return this.count;
    }
}
