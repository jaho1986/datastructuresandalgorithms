package com.jaho.datastructuresandalgorithms.queues;

public class Queue <T extends Comparable<T>> {
    private Node<T> firstNode;
    private Node<T> lastNode;

    private int count;

    public boolean isEmpty(){
        return this.firstNode == null;
    }

    public int getSize() {
        return this.count;
    }

    /**
     * We insert the items at the beginning of the queue.
     *
     * @param data
     */
    public void enqueue(T data) {
        this.count++;
        Node<T> oldLastNode = this.lastNode;
        this.lastNode = new Node<>(data);
        this.lastNode.setNextNode(null);

        if(isEmpty()) {
            this.firstNode = this.lastNode;
        } else {
            oldLastNode.setNextNode(this.lastNode);
        }
    }

    /**
     * O(1)
     * @return
     */
    public T dequeue() {
        this.count--;
        T dataToDequeue = this.firstNode.getData();
        this.firstNode = this.firstNode.getNextNode();

        if(isEmpty()) {
            this.lastNode=null;
        }
        return dataToDequeue;
    }
}
