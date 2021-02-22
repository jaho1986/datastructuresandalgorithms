package com.jaho.datastructuresandalgorithms.splaytrees;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SplayTree <T extends Comparable<T>> implements Tree<T> {

    private Node<T> rootNode;

    @Override
    public void insert(T data) {
        if(this.rootNode == null) {
            this.rootNode = new Node<>(data, null);
        } else {
            insert(data, this.rootNode);
        }
    }

    private void insert(T data, Node<T> node) {
        //This is the case when the data is smaller than the value in the node:
        //We go to the left subtree:
        if(node.getData().compareTo(data) > 0) {
            if(node.getLeftChild() != null) {
                insert(data,node.getLeftChild());

            } else {
                //There is no left child, so we create it:
                Node<T> tmpNode = new Node<T>(data, node);
                node.setLeftChild(tmpNode);
                splay(tmpNode);
            }

        //This is the case when the data is greater than the value in the node:
        //We go to the right subtree:
        } else {
            if(node.getRightChild() != null) {
                insert(data,node.getRightChild());
            } else {
                //There is no right child, so we create it:
                Node<T> tmpNode = new Node<T>(data, node);
                node.setRightChild(tmpNode);
                splay(tmpNode);
            }
        }
    }

    private void splay(Node<T> node) {
    }

    @Override
    public T find(T data, Node<T> node) {
        if (data.compareTo(node.getData()) < 0) {
            find(data, node.getLeftChild());
        } else if (data.compareTo(node.getData()) > 0){
            find(data, node.getRightChild());
        } else {
            //We have found the item we are looking for:
            splay(node);
            return data;
        }
        return null;
    }

    @Override
    public void traverse() {
        if (this.rootNode != null) {
            traverse(this.rootNode);
        }
    }

    private void traverse(Node<T> node) {
        if (node.getLeftChild() != null) {
            traverse(node.getLeftChild());
        }
        log.info(node);

        if (node.getRightChild() != null) {
            traverse(node.getRightChild());
        }
    }
}
