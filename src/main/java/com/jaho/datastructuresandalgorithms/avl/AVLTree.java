package com.jaho.datastructuresandalgorithms.avl;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AVLTree <T extends Comparable<T>> implements Tree<T> {

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
        updateHeight(node);
        //Settle the violation:
        settleViolations(node);
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

                updateHeight(parent);

            //Case 2: when we remove items with a single child:
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
                updateHeight(parent);

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
                updateHeight(parent);

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

        //Settle the violations:
        settleViolations(node);
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

    /**
     * Checks up to the root node if any of the AVL properties are violated or not.
     * @param node
     */
    private void settleViolations(Node<T> node) {
        while (node != null){
            updateHeight(node);
            settleViolationsHelper(node);
            node = node.getParentNode();
        }
    }

    private void settleViolationsHelper(Node<T> node) {
        int balance = getBalanceFactor(node);

        log.info("Checking if there is a violation on node: " + node.getData() + ". Balance factor: " + balance);

        //There is a left-heavy situation:
        if (balance > 1) {
            //This is a left-right heavy situation: Left Rotation.
            if(getBalanceFactor(node.getLeftChild()) < 0) {
                makeLeftRotation(node.getLeftChild());
            }
            //If there is a left-left heavy situation just a single right rotation is needed.
            makeRightRotation(node);
        }

        //There is a right-heavy situation:
        if (balance < -1) {
            //This is a right-left heavy situation: Right Rotation.
            if(getBalanceFactor(node.getRightChild()) > 0) {
                makeRightRotation(node.getRightChild());
            }
            //If there is a right-right heavy situation just a single right rotation is needed.
            makeLeftRotation(node);
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

        //After rotations height parameters have changed:
        updateHeight(node);
        updateHeight(tmpLeftChild);
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

        //After rotations height parameters have changed:
        updateHeight(node);
        updateHeight(tmpRightChild);
    }



    /**
     * Update the height of a given node:
     * @param node
     */
    public void updateHeight(Node<T> node) {
        node.setHeight(Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()))+1);
    }

    /**
     * Gets the height of a given node:
     * @param node
     * @return
     */
    private int getHeight(Node<T> node) {
        if(node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Calculates the balance factor to decide the left heavy or right heavy cases:
     * @param node
     * @return
     */
    private int getBalanceFactor(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeftChild()) - getHeight(node.getRightChild());
    }
}
