package com.jaho.datastructuresandalgorithms.stacks;

public class StackWithArrays <T> {

    private T[] stack;
    private int count;

    public StackWithArrays() {
        this.stack = (T[]) new Object[1];
    }

    public void push(T data) {
        if(this.stack.length == this.count) {
            resize(this.count*2);
        }
        this.stack[this.count++] = data;
    }

    /**
     * Arrays are not dynamic structures.
     *
     * We have to resize the underlying array if necessary:
     *  - If there are too many items we will double the size of the array.
     *  - If there are to many items we will shrink the array.
     */
    private void resize(int capacity) {
        T[] stackCopy = (T[]) new Object[capacity];

        //We need to copy the items one by one:
        for (int i = 0; i < this.count; i++) {
            stackCopy[i] = this.stack[i];
        }
        this.stack = stackCopy;

        System.out.println("Size original and copy: " + this.stack.length + " - " +stackCopy.length);
    }

    /**
     * Returns the last item we have inserted in O(1):
     *
     * @return
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T item = this.stack[--this.count];

        //Obsolete references - avoid memory leaks:
        stack[this.count] = null;

        //We check if we need to resize the array:
        if (this.count > 0 && this.count == this.stack.length/4) {
            this.resize(this.stack.length/2);
        }

        return item;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int getSize() {
        return count;
    }
}

