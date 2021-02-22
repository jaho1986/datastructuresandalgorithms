package com.jaho.datastructuresandalgorithms.bst;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BinarySearchTree <T extends Comparable<T>> implements Tree<T> {

    private Node<T> rootNode;

    @Override
    public Node<T> getRoot() {
        return this.rootNode;
    }

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
                node.setLeftChild(new Node<T>(data, node));
            }

        //This is the case when the data is greater than the value in the node:
        //We go to the right subtree:
        } else {
            if(node.getRightChild() != null) {
                insert(data,node.getRightChild());
            } else {
                //There is no right child, so we create it:
                node.setRightChild(new Node<T>(data, node));
            }
        }
    }

    @Override
    public void remove(T data) {
        if (this.rootNode != null) {
            remove(data, this.rootNode);
        }
    }

    private void remove(T data, Node<T> node) {
        if (node == null) {
            return;
        }
        //We have to search the item we have to remove:
        if (data.compareTo(node.getData()) < 0) {
            remove(data, node.getLeftChild());

        } else if (data.compareTo(node.getData()) > 0) {
            remove(data, node.getRightChild());

        } else {
            /**
             * We have found the item we have to remove:
             * If the node is a leaf node without left o right children:
             */
            if (node.getLeftChild() == null && node.getRightChild() == null) {

                Node<T> parent = node.getParentNode();

                //This node is a left child node:
                if (parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(null);
                } if (parent != null && parent.getRightChild() == node) {
                    parent.setRightChild(null);
                }

                //This is the case when the root node is the node we want to remove:
                if (parent == null) {
                    this.rootNode = null;
                }
                node = null;

            //Case 2: when we remove items with a single child.
            } else if (node.getLeftChild() == null && node.getRightChild() != null) {

                Node<T> parent = node.getParentNode();

                if (parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(node.getRightChild());
                } else if (parent != null && parent.getRightChild() == node) {
                    parent.setRightChild(node.getRightChild());
                }

                //The case when we want to delete the root node:
                if (parent == null) {
                    this.rootNode = node.getRightChild();
                }

                //We have to update the right child's parent:
                node.getRightChild().setParentNode(parent);
                node = null;

            } else if (node.getLeftChild() != null && node.getRightChild() == null) {

                Node<T> parent = node.getParentNode();

                if (parent != null && parent.getLeftChild() == node) {
                    parent.setLeftChild(node.getLeftChild());
                } else if (parent != null && parent.getRightChild() == node) {
                    parent.setRightChild(node.getLeftChild());
                }

                //The case when we want to delete the root node:
                if (parent == null) {
                    this.rootNode = node.getLeftChild();
                }

                //We have to update the right child's parent:
                node.getLeftChild().setParentNode(parent);
                node = null;

            //Case 3: The case when we have to remove 2 children:
            } else {
                //Find the predecessor (Max item in the left subtree):
                Node<T> predecessor = getPredecessor(node.getLeftChild());

                //Swap the values:
                T temp = predecessor.getData();
                predecessor.setData(node.getData());
                node.setData(temp);

                //We have to delete the method recursively on the predecessor:
                remove(data, predecessor);
            }
        }
    }

    private Node<T> getPredecessor(Node<T> node) {
        if(node.getRightChild() != null) {
            return getPredecessor(node.getRightChild());
        }
        return node;
    }

    @Override
    public void traverse() {
        traverse(this.rootNode);
    }

    private void traverse(Node<T> node) {
        if (node.getLeftChild() != null) {
            traverse(node.getLeftChild());
        }

        log.info(node + " - ");

        if (node.getRightChild() != null) {
            traverse(node.getRightChild());
        }
    }

    @Override
    public T getMin() {
        if(this.rootNode == null) {
            return null;
        }
        return getMin(this.rootNode);
    }

    private T getMin(Node<T> node) {
        if(node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    @Override
    public T getMax() {
        if(this.rootNode == null) {
            return null;
        }
        return getMax(this.rootNode);
    }

    @Override
    public Node<T> getKSmallest(Node<T> node, int k) {
        //We get the number of nodes in the left subtree
        //+1 because we count the root node of subtree as well
        int n = getTreeSize(rootNode.getLeftChild())+1;

        if(n==k) {
            return  node;
        }

        //If the number of nodes in the left subtree > k-th smalles item
        //it means the kh smalles item is in the left subtree.
        if (n > k) {
            return getKSmallest(node.getLeftChild(), k);
        }

        //If the number of nodes in the left subtree is smaller than the k-th
        //smallest item we can discard the left subtree and consider the right subtree
        //Now we are looking for the k-th smallest item:

        if (k > n) {
            return getKSmallest(node.getRightChild(), (k-n));
        }
        return null;
    }

    private int getTreeSize(Node<T> node) {
        if (node == null) {
            return 0;
        }

        //Recursively sum up the size of the left subtree + size of right subtree
        //Size of tree = size left subtree + size right subtree + 1 (because of the root)
        return (getTreeSize(node.getLeftChild()) + getTreeSize(node.getRightChild()) + 1);
    }

    public T getMax(Node<T> node) {
        if(node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }
}
