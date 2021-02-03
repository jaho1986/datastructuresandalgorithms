package com.jaho.datastructuresandalgorithms.linkedlist;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LinkedList <T extends Comparable<T>> implements List<T> {

    public LinkedList() {
        this.numberOfItems = 0;
    }

    private Node<T> rootNode;
    private Integer numberOfItems;

    @Override
    public void insert(T data) {
        //This is the first item in the linked list:
        if (this.rootNode == null) {
            this.rootNode = new Node<T>(data);
            this.numberOfItems++;

        //This is not the first item in the linked list:
        } else {
            insertBeginning(data);
        }
    }

    /**
     * We just have to update the references in O(1):
     *
     * @param data
     */
    private void insertBeginning(T data) {
        Node<T> newNode = new Node<T>(data);
        newNode.setNextNode(this.rootNode);
        this.rootNode = newNode;
        this.numberOfItems++;
    }

    /**
     * Because we have to start with the root node, first
     * we have the last node in O(N):
     *
     * @param data
     */
    private void insertEnd(T data, Node<T> node) {
        if (node.getNextNode() !=  null) {
            insertEnd(data, node.getNextNode());

        } else {
            //We have found the last node:
            Node<T> newNode = new Node<T>(data);
            node.setNextNode(newNode);
        }
    }

    @Override
    public void remove(T data) {
        if (this.rootNode == null) {
            return;
        }

        if(rootNode.getData().compareTo(data) == 0) {
            this.rootNode = this.rootNode.getNextNode();
            this.numberOfItems--;
        } else {
            remove(data, this.rootNode, this.rootNode.getNextNode());
        }
    }

    private void remove(T data, Node<T> previousNode, Node<T> actualNode) {
        //We have to find the node we want to remove:
        while (actualNode != null) {
            //This is the node we want to remove:
            if(actualNode.getData().compareTo(data) == 0) {
                //Update the references:
                previousNode.setNextNode(actualNode.getNextNode());
                actualNode=null;
                return;
            }
            previousNode = actualNode;
            actualNode = actualNode.getNextNode();
        }
    }

    @Override
    public void traverse() {
        if (this.rootNode == null) {
            return;
        }

        Node<T> actualNode = this.rootNode;

        while (actualNode != null) {
            log.info(actualNode);
            actualNode = actualNode.getNextNode();
        }
    }

    @Override
    public int getSize() {
        log.info("The size is: " + this.numberOfItems);
        return this.numberOfItems;
    }

    /**
     * Interview question:
     *
     *
     * @return
     */
    @Override
    public Node<T> getMiddleNode() {
        Node<T> fastPointer = this.rootNode;
        Node<T> slowPointer = this.rootNode;

        while (fastPointer.getNextNode() != null && fastPointer.getNextNode().getNextNode() != null) {
            fastPointer = fastPointer.getNextNode().getNextNode();
            slowPointer = slowPointer.getNextNode();
        }

        return slowPointer;
    }

    @Override
    public void reverse() {
        Node<T> currentNode = this.rootNode;
        Node<T> previousNode = null;
        Node<T> nextNode = null;

        while (currentNode != null) {
            nextNode = currentNode.getNextNode();
            currentNode.setNextNode(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        this.rootNode = previousNode;
    }
}
