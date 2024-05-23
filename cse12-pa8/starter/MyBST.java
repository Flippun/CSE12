/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA8 Write-up
 * 
 * This file is for CSE 12 PA8 in Spring 2023,
 * and contains the implementation of the MyBST class.
 */

import java.util.ArrayList;

/**
  * Generic class that implements a data structure similar to 
  * a Binary Search Tree.
  * 
  * Instance Variablees:
  * root - A reference to the root node of our tree. 
  * size - Represents the number of nodes in the tree. 
  */
public class MyBST<K extends Comparable<K>, V> {
    MyBSTNode<K, V> root = null;
    int size = 0;

    /**
     * Returns the number of nodes in the tree. 
     * 
     * @return the number of nodes in the tree. 
     */
    public int size() {
        return size;
    }

    
    /**
     * Insert a new node containing the arguments key and value into the binary
     * search tree according to the binary search tree properties.
     * 
     * @param key key of node to be inserted
     * @param value value of node to be inserted
     * @return value of overidden node if duplicate key, null otherwise
     */
    public V insert(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        else if (root == null) {
            root = new MyBSTNode<K, V>(key, value, null);
            size = 1;
            return null;
        }
        else {
            return insertRecursive(root, key, value);
        }
    }

    /**
     * Recursive helper method for BST insert
     * 
     * @param parent current node within iteration
     * @param key key of new node
     * @param value value of new node
     * @return value of overwritten node, null otherwise
     */
    private V insertRecursive(MyBSTNode<K, V> parent, K key, V value) {
        // If key is less than current key
        if (key.compareTo(parent.getKey()) < 0) {
            if (parent.getLeft() == null) {
                MyBSTNode<K, V> insertedNode = 
                    new MyBSTNode<K, V>(key, value, parent);
                parent.setLeft(insertedNode);
                size++;
                return null;
            }
            else {
                return insertRecursive(parent.getLeft(), key, value);
            }
        }

        // If key is greater than current key
        else if (key.compareTo(parent.getKey()) > 0) {
            if (parent.getRight() == null) {
                MyBSTNode<K, V> insertedNode = 
                    new MyBSTNode<K, V>(key, value, parent);
                parent.setRight(insertedNode);
                size++;
                return null;
            }
            else {
                return insertRecursive(parent.getRight(), key, value);
            }
        }

        // If key is equal to current key
        else {
            V oldValue = parent.getValue();
            parent.setValue(value);
            return oldValue;
        }
    }


    /**
     * Search for a node with key equal to key and return the value 
     * associated with that node.
     * 
     * @param key key to be searched for
     * @return node with matching key, null otherwise
     */
    public V search(K key) {
        if (key == null) {
            return null;
        }
        else {
            return searchRecursive(root, key);
        }
    }

    /**
     * Recursive helper method for BST search
     * 
     * @param currNode current Node of iteration
     * @param key key to be looked for
     * @return value of node with matching key, null otherwise
     */
    private V searchRecursive(MyBSTNode<K, V> currNode, K key) {
        if (currNode != null) {
            // If found, return value
            if (key == currNode.getKey()) {
                return currNode.getValue();
            }

            // If key is less than current, search left
            else if (key.compareTo(currNode.getKey()) < 0) {
                return searchRecursive(currNode.getLeft(), key);
            }

            // If key is greater than current, search right
            else {
                return searchRecursive(currNode.getRight(), key);
            }
        }
        return null;
    }


    /**
     * Search for a node with key equal to key and return 
     * the value associated with that node.
     * 
     * @param key key of node to be removed
     * @return value of removed node
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        else { 
            MyBSTNode<K, V> removedNode = removeSearch(root, key);
            if (removedNode == null) { 
                return null; 
            }
            MyBSTNode<K, V> parent = removedNode.getParent();
            return removeRecursive(parent, removedNode);
        }
    }

    /**
     * Recursive helper method for BST remove
     * 
     * @param parent parent of node to be removed
     * @param removedNode node to be removed
     * @return value of removed node
     */
    private V removeRecursive(MyBSTNode<K, V> parent, 
            MyBSTNode<K, V> removedNode) {
        if (removedNode == null) {
            return null;
        }
        // Saves return value
        V nodeValue = removedNode.getValue();

        // If 2 Children
        if (removedNode.getLeft() != null && removedNode.getRight() != null) {
            MyBSTNode<K, V> succNode = removedNode.successor();
            removedNode.setValue(succNode.getValue());
            removedNode.setKey(succNode.getKey());
            removedNode.setParent(succNode.getParent());
            removeRecursive(succNode.getParent(), succNode);
        }

        else {
            // If node is root
            if (removedNode == root) {
                if (removedNode.getLeft() != null) {
                    root = removedNode.getLeft();
                }
                else {
                    root = removedNode.getRight();
                }
            }

            // Only left child
            else if (removedNode.getLeft() != null) {
                if (parent.getLeft() == removedNode) {
                    parent.setLeft(removedNode.getLeft());
                }
                else {
                    parent.setRight(removedNode.getLeft());
                }
                removedNode.getLeft().setParent(parent);
            }

            // Only right child or leaf
            else {
                if (parent.getLeft() == removedNode) {
                    parent.setLeft(removedNode.getRight());
                }
                else {
                    parent.setRight(removedNode.getRight());
                }
                if (removedNode.getRight() != null) {
                    removedNode.getRight().setParent(parent);
                }
            }

            // Remove pointers in all cases:
            removedNode.setParent(null);
            removedNode.setLeft(null);
            removedNode.setRight(null);
            size--;
        }

        // Fixes root pointer if 2 children
        if (root == removedNode) {
            removedNode.setParent(null);
        }

        return nodeValue;
    }

    /**
     * Recursive helper method for BST remove that searches
     * and returns the node with matching key
     * 
     * @param currNode current node of iteration
     * @param key key of node to be found
     * @return node with matching key
     */
    private MyBSTNode<K, V> removeSearch(MyBSTNode<K, V> currNode, K key) {
        if (currNode != null) {
            // If key is found, return Node
            if (key == currNode.getKey()) {
                return currNode;
            }

            // If key is less than current, search left
            else if (key.compareTo(currNode.getKey()) < 0) {
                return removeSearch(currNode.getLeft(), key);
            }

            // If key is greater than current, search right
            else {
                return removeSearch(currNode.getRight(), key);
            }
        }
        return null;
    }

    /**
     * Performs in-order traversal of the tree, adding each node to the end 
     * of an ArrayList, which will be returned.
     * 
     * @return An arraylist sorted in ascending order
     */
    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> sortedArray = new ArrayList<>();
        inorderRecursive(root, sortedArray);
        return sortedArray;
    }

    /**
     * Recursive helper method for inorder
     * 
     * @param node current node to be added
     * @param sortedArray array node is to be added to
     */
    private void inorderRecursive(MyBSTNode<K, V> node, 
                ArrayList<MyBSTNode<K, V>> sortedArray) {
        if (node == null) {
            return;
        }
        inorderRecursive(node.getLeft(), sortedArray);
        sortedArray.add(node);
        inorderRecursive(node.getRight(), sortedArray);
    }

    /**
     * A static nested class of the MyBST class. Objects of this class 
     * represent the nodes of the binary search tree.
     * 
     * Instance Variables:
     * key - The key that we are using to sort our nodes 
     * value - The data stored in this MyBSTNode 
     * parent - A reference to the parent of this MyBSTNode 
     * left - A reference to the left child of this MyBSTNode 
     * right - A reference to the right child of this MyBSTNode 
     */
    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode storing specified data
         *
         * @param key    the key the MyBSTNode will store
         * @param value  the data the MyBSTNode will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode
         *
         * @return the key stored in the MyBSTNode
         */
        public K getKey() {
            return key;
        }

        /**
         * Set the key stored in the MyBSTNode
         *
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Return data stored in the MyBSTNode
         *
         * @return the data stored in the MyBSTNode
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the data stored in the MyBSTNode
         *
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Return the parent
         *
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Set the parent
         *
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Return the left child
         *
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Set the left child
         *
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Return the right child
         *
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the right child
         *
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        /**
         * This method finds the node with the smallest key that is 
         * still larger than the key of the current node.
         * 
         * @return the node with the smallest key that is 
         * still larger than the key of the current node
         */
        public MyBSTNode<K, V> successor() {
            if (getRight() != null) {
                MyBSTNode<K, V> succNode = getRight();
                while (succNode.getLeft() != null ) {
                    succNode = succNode.getLeft();
                }
                return succNode;
            }   
            else {
                MyBSTNode<K, V> parentNode = getParent();
                MyBSTNode<K, V> currentNode = this;
                while (parentNode != null 
                        && currentNode == parentNode.getRight()) {
                    currentNode = parentNode;
                    parentNode = parentNode.getParent();
                }
                return parentNode;
            }
        }

        /**
         * This method compares if two node objects are equal.
         *
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null :
                    this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null :
                    this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         *
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }
}