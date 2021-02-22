package com.jaho.datastructuresandalgorithms.rbtrees;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RedBlackTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> rootNode;

    @Override
    public void insert(T data) {
        if(this.rootNode == null) {
            this.rootNode = new Node<>(data, null);
            settleViolations(this.rootNode);
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
                Node<T> newNode = new Node<T>(data, node);
                node.setLeftChild(newNode);
                settleViolations(newNode);
            }

            //This is the case when the data is greater than the value in the node:
            //We go to the right subtree:
        } else {
            if(node.getRightChild() != null) {
                insert(data,node.getRightChild());
            } else {
                //There is no right child, so we create it:
                Node<T> newNode = new Node<T>(data, node);
                node.setRightChild(newNode);
                settleViolations(newNode);
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

    private void settleViolations(Node<T> node) {
        Node<T> parentNode = null;
        Node<T> grandParentNode = null;

        //We have to check the violations up to de root node:
        while (node != this.rootNode && isRed(node) && isRed(node.getParentNode())) {
            parentNode = node.getParentNode();
            grandParentNode = parentNode.getParentNode();

            //We check if the parent is a left child of it´s parent (grandparent):
            if(parentNode == grandParentNode.getLeftChild()) {
                Node<T> uncle = grandParentNode.getRightChild();

                //Case 1 and case 4: Recoloring
                if (uncle != null && isRed(uncle)){
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncle.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {
                    //Case 2.
                    if (node == parentNode.getRightChild()) {
                        makeLeftRotation(parentNode);
                        node = parentNode;
                        parentNode = grandParentNode;
                    }

                    //Case 3.
                    makeRightRotation(parentNode);
                    NodeColor tmpColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    grandParentNode.setColor(tmpColor);
                    node = parentNode;
                }

            //We check if the parent is a right child of it´s parent (grandparent):
            } else {
                Node<T> uncle = grandParentNode.getLeftChild();

                //Case 1 and case 4: Recoloring
                if (uncle != null && isRed(uncle)){
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncle.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {
                    //Case 2.
                    if (node == parentNode.getRightChild()) {
                        makeLeftRotation(parentNode);
                        node = parentNode;
                        parentNode = grandParentNode;
                    }
                    //Case 3.
                    makeLeftRotation(parentNode);
                    NodeColor tmpColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    grandParentNode.setColor(tmpColor);
                    node = parentNode;
                }
            }
        }

        //Root node can not be red, so we have to recolor if it's necessary:
        if (isRed(this.rootNode)) {
            this.rootNode.setColor(NodeColor.BLACK);
        }
    }

    private boolean isRed(Node<T> node) {
        if (node == null) {
            return false;
        }
        return node.getColor() == NodeColor.RED;
    }

    private Node<T> getPredecessor(Node<T> node) {
        if(node.getRightChild() != null) {
            return getPredecessor(node.getRightChild());
        }
        return node;
    }

    @Override
    public void traverse() {
        if(this.rootNode != null) {
            traverse(this.rootNode);
        }
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

}
