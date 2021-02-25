package com.jaho.datastructuresandalgorithms.bheaps;

public class Heap {

    private int[] heap;

    //Number of items in the heap
    private int heapSize;

    public Heap() {
        this.heap = new int[Constants.CAPACITY];
    }

    public void inser(int data) {
        if (isFull()) {
            throw new RuntimeException("The heap is full...");
        }

        //We append the dat ato the end of the array:
        this.heap[this.heapSize] = data;
        this.heapSize++;

        //We have to check the heap properties.
        //We start with the las item that has index heapSize-1.
        fixUp(this.heapSize-1);
    }

    private void fixUp(int index) {
        //Index of the parent:
        int parentIndex = (index-1)/2;

        //In worst case, we have to consider all the nodes up to the root node (index 0)
        //In a maximum heap the parent is always larger.
        if (index > 0 && heap[index] > heap[parentIndex]) {
            swap(index, parentIndex);
            fixUp(parentIndex);
        }

    }

    private void swap(int index1, int index2) {
        int tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

    private boolean isFull() {
        return this.heapSize == Constants.CAPACITY;
    }
}
