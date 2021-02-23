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

    /**
     * This is when we make rotations on the new node to become the root node.
     *
     * @param node
     */
    private void splay(Node<T> node) {

        //We iterate while the node is not the root node:
        while (node.getParentNode()!= null) {

            //If the grandparent is a null we have to make a single rotation:
            //Case 3: The ZIG case:
            if (node.getParentNode().getParentNode() == null) {

                //If the node is a left child of the root node we make a right rotation:
                if (node.getParentNode().getLeftChild() == node) {
                    makeRightRotation(node.getParentNode());

                //If the node is a right child of the root node we make a left rotation:
                } else {
                    makeLeftRotation(node.getParentNode());
                }

            //Case 2: The ZIG-ZIG case:
            } else if (node.getParentNode().getLeftChild() == node && node.getParentNode().getParentNode().getLeftChild() == node.getParentNode()) {
                makeRightRotation(node.getParentNode().getParentNode());
                makeRightRotation(node.getParentNode());

            } else if (node.getParentNode().getRightChild() == node && node.getParentNode().getParentNode().getRightChild() == node.getParentNode()) {
                makeLeftRotation(node.getParentNode().getParentNode());
                makeLeftRotation(node.getParentNode());

            //Case 1: The ZIG-ZAG case:
            } else if (node.getParentNode().getLeftChild() == node && node.getParentNode().getParentNode().getRightChild() == node.getParentNode()) {
                makeRightRotation(node.getParentNode());
                makeLeftRotation(node.getParentNode());
            } else {
                makeLeftRotation(node.getParentNode());
                makeRightRotation(node.getParentNode());

            }
        }
    }


    /**
     * Makes right rotation.
     *
     * @param node
     */
    private void makeRightRotation(Node<T> node) {

        log.info("Making right rotation on node: " + node.getData());
        Node<T> tmpLeftChild = node.getLeftChild();
        Node<T> grandChild = tmpLeftChild.getRightChild();

        //Makes the rotation: the new root node will
        tmpLeftChild.setRightChild(node);
        node.setLeftChild(grandChild);

        if(grandChild != null) {
            grandChild.setParentNode(node);
        }

        //We have to handle the parents of the node:
        Node<T> tmpParent = node.getParentNode();
        node.setParentNode(tmpLeftChild);
        tmpLeftChild.setParentNode(tmpParent);

        //We have to handle the parent and check if the parent node was a right child or left child:
        if (tmpLeftChild.getParentNode() != null && tmpLeftChild.getParentNode().getLeftChild() == node) {
            tmpLeftChild.getParentNode().setLeftChild(tmpLeftChild);
        }

        if (tmpLeftChild.getParentNode() != null && tmpLeftChild.getParentNode().getRightChild() == node) {
            tmpLeftChild.getParentNode().setRightChild(tmpLeftChild);
        }

        if (node == this.rootNode) {
            this.rootNode = tmpLeftChild;
        }
    }

    /**
     * Makes left rotation.
     *
     * @param node
     */
    private void makeLeftRotation(Node<T> node) {
        log.info("Making left rotation on node: " + node.getData());

        Node<T> tmpRightChild = node.getRightChild();
        Node<T> grandChild = tmpRightChild.getLeftChild();

        //Makes the rotation: the new root node will
        tmpRightChild.setLeftChild(node);
        node.setRightChild(grandChild);

        if(grandChild != null) {
            grandChild.setParentNode(node);
        }

        //We have to handle the parents of the node:
        Node<T> tmpParent = node.getParentNode();
        node.setParentNode(tmpRightChild);
        tmpRightChild.setParentNode(tmpParent);

        //We have to handle the parent and check if the parent node was a right child or left child:
        if (tmpRightChild.getParentNode() != null && tmpRightChild.getParentNode().getRightChild() == node) {
            tmpRightChild.getParentNode().setRightChild(tmpRightChild);
        }

        if (tmpRightChild.getParentNode() != null && tmpRightChild.getParentNode().getLeftChild() == node) {
            tmpRightChild.getParentNode().setLeftChild(tmpRightChild);
        }

        if (node == this.rootNode) {
            this.rootNode = tmpRightChild;
        }
    }

    @Override
    public T getRootNode() {
        if (this.rootNode == null) {
            return  null;
        }
        log.info(this.rootNode);
        return this.rootNode.getData();
    }

    @Override
    public T find (T data) {
        if (this.rootNode == null) {
            return null;
        }
        return find(data, this.rootNode);
    }

    private T find(T data, Node<T> node) {
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
