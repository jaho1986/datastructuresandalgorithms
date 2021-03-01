package com.jaho.datastructuresandalgorithms.bheaps;

public class Heap {

    private int[] heap;

    //Number of items in the heap
    private int heapSize;

    public Heap() {
        this.heap = new int[Constants.CAPACITY];
    }

    public void insert(int data) {
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

    public int getMax() {
        return this.heap[0];
    }

    public void heapSort() {
        int n = this.heapSize;
        int max = 0;
        for (int i=0; i<n; i++) {
            max = poll();
            System.out.println(max);
        }
    }

    public int poll() {
        int max = this.getMax();

        //We have to swap the root node with the max item:
        swap(0, this.heapSize-1);
        this.heapSize--;

        //Fix the properties if needed:
        fixDown(0);
        return max;
    }

    /**
     * O(logN)
     * @param index
     */
    private void fixDown(int index) {
        int leftChildIndex = 2*index + 1;
        int rightChildIndex = 2*index + 2;
        int theLargestIndex = index;

        //Compare the left child with the parent:
        if (leftChildIndex < heapSize && heap[leftChildIndex] > this.heap[index]) {
            theLargestIndex = leftChildIndex;
        }

        //Compare the right child with the parent:
        if (rightChildIndex < heapSize && heap[rightChildIndex] > this.heap[index]) {
            theLargestIndex = rightChildIndex;
        }

        //If one of the children is larger than the parent we have to swap the items:
        if(index != theLargestIndex) {
            swap(index, theLargestIndex);
            fixUp(theLargestIndex);
        }
    }

    public boolean isEmpty() {
        return heapSize==0;
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
