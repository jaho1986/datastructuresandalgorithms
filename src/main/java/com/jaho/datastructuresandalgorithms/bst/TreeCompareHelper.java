package com.jaho.datastructuresandalgorithms.bst;

public class TreeCompareHelper <T extends Comparable<T>> {

    public boolean compareTrees(Node<T> node1, Node<T> node2) {

        //We have to check the base cases (it may be the leaf node so we have to use ==)
        if((node1==null && node2==null) || (node1==null && node2!=null) || (node1!=null && node2==null)) {
            return node1 == node2;
        }

        //If the values withing the nodes are not the same we return false (trees are not the same):
        if (node1.getData().compareTo(node2.getData()) != 0) {
            return  false;
        }

        //The left subtree values and right subtree values must mach as well:
        return compareTrees(node1.getLeftChild(), node2.getLeftChild()) && compareTrees(node1.getRightChild(), node2.getRightChild());
    }
}
