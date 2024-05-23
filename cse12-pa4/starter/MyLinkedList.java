/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: Java Documentation, PA4 Write-up
 * 
 * This file is for CSE 12 PA4 in Spring 2023,
 * and contains the implementation of the MyLinkedList class
 * which emulates Java's LinkedList data structure.
 */

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * A generic class that specifies a data structure similar to Java's LinkedList
 * 
 * Instance Variables:
 * size - Keep track of the number of nodes in the linked list
 * head - Reference to the sentinel head of linked list
 * tail - Reference to the sentinel tail of linked list
 */
public class MyLinkedList<E> extends AbstractList<E> {

    static final String NULL_EXCEPTION = "data cannot be null";
    static final String INDEX_EXCEPTION = "index is out of bounds";

    int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         * 
         * @param element Element to add, can be null
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Set the parameter prev as the previous node
         * 
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        /**
         * Set the parameter next as the next node
         * 
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Set the parameter element as the node's data
         * 
         * @param element new element
         */
        public void setElement(E element) {
            this.data = element;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        }
    }

    // Implementation of the MyLinkedList Class

    /**
     * Constructor that creates an empty list and
     * initializes all the necessary variables.
     */
    public MyLinkedList() {
        // Creates dummy head and tail nodes
        this.head = new Node(null);
        this.tail = new Node(null);
        this.head.next = this.tail;
        this.head.prev = null;
        this.tail.next = null;
        this.tail.prev = this.head;
        this.size = 0;
    }

    /**
     * Returns the number of elements in this list.
     * If this list contains more than Integer.MAX_VALUE elements,
     * returns Integer.MAX_VALUE.
     * 
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    @Override
    public E get(int index) {
        Node nodeAtIndex = getNth(index);
        return (E) nodeAtIndex.data;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position,
     * and any subsequent elements to the right.
     * 
     * @param index index at which the specified element is to be inserted
     * @param data  element to be inserted
     */
    @Override
    public void add(int index, E data) {
        // Checks for exceptions
        if (data == null) {
            throw new NullPointerException(NULL_EXCEPTION);
        }
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION);
        }

        // Creates new node, changes pointers
        this.size++;
        Node newNode = new Node(data);
        Node nodeAtIndex = getNth(index);
        newNode.setPrev(nodeAtIndex.prev);
        newNode.setNext(nodeAtIndex);
        nodeAtIndex.prev.setNext(newNode);
        nodeAtIndex.setPrev(newNode);
    }

    /**
     * Appends the specified element to the end of this list.
     * 
     * @param data element to be appended to this list
     * @return true (as specified by Collection.add)
     */
    @Override
    public boolean add(E data) {
        // adds new node at last index of list
        add(this.size, data);
        return true;
    }

    /**
     * Replaces the element at the specified position in this list
     * with the specified element.
     * 
     * @param index index of the element to replace
     * @param data  element to be stored at the specified position
     * @return the element previously at the specified position
     */
    @Override
    public E set(int index, E data) {
        // Checks for Exceptions
        if (data == null) {
            throw new NullPointerException(NULL_EXCEPTION);
        }

        // Gets Node at desired index, stores original data,
        // replaces with new data, returns original data
        Node nodeAtIndex = getNth(index);
        E prevElement = nodeAtIndex.data;
        nodeAtIndex.setElement(data);
        return (E) prevElement;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left.
     * 
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     */
    @Override
    public E remove(int index) {
        // Gets node at index, changes pointers around node
        Node removedNode = getNth(index);
        removedNode.prev.setNext(removedNode.next);
        removedNode.next.setPrev(removedNode.prev);
        removedNode.setNext(null);
        removedNode.setPrev(null);
        size--;
        return (E) removedNode.data;
    }
  
    /**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        // resets head and tail pointers
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.size = 0;
    }

    /**
     * Returns true if this list contains no elements.
     * 
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        // all must be true for list to be empty
        if (this.head.next == this.tail
                && this.tail.prev == this.head
                && this.size == 0) {
            return true;
        }
        return false;
    }

    /**
     * A helper method that returns the Node at a specified index,
     * not the Node data.
     * 
     * @param index of Node to be returned
     * @return the Node at index
     */
    protected Node getNth(int index) {
        // throw exception if index is invalid
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION);
        }

        // Initially equals first node after head, shifts down untill index
        Node nodeAtIndex = head.next;
        for (int i = 0; i < index; i++) {
            nodeAtIndex = nodeAtIndex.next;
        }
        return (Node) nodeAtIndex;
    }

    // Implementation of the MyListIterator class

    /**
     * An iterator class that allows for faster removal and replacement
     * 
     * Instance Variables:
     * Left, right - Two node references to determine the iterator location
     * idx - Int value of the index of the next node
     * forward - Determine the current moving direction of the iterator
     * canRemoveOrSet - Keep track of whether the iterator allows remove or set
     */
    protected class MyListIterator implements ListIterator<E> {
        
        Node left, right;
        int idx;
        boolean forward;
        boolean canRemoveOrSet;

        /**
         * Constructor that is used to initialize the iterator.
         */
        public MyListIterator() {
            this.idx = 0;
            this.left = head;
            this.right = head.next;
            this.forward = true;
            this.canRemoveOrSet = false;
        }

        /**
         * Returns true if this list iterator has more elements when traversing
         * the list in the forward direction. (In other words, returns true if 
         * next would return an element rather than throwing an exception.)
         * 
         * @return true if the list iterator has more elements when 
         *         traversing the list in the forward direction
         */
        @Override
        public boolean hasNext() {
            if (right == tail) {
                return false;
            }
            else {
                return true;
            }
        }

        /**
         * Return the next element in the list when going forward, 
         * and move the iterator forward by one node. 
         * 
         * @return the next element in the list
         */       
        @Override
        public E next() {
            if (hasNext() == false) {
                throw new NoSuchElementException();
            }
            left = right;
            right = right.next;
            this.forward = true;
            canRemoveOrSet = true;
            idx ++;
            return left.data;
        }

        /**
         * Returns true if this list iterator has more elements when traversing
         * the list in the reverse direction. (In other words, returns true if 
         * previous would return an element rather than throwing an exception.)
         * 
         * @return true if the list iterator has more elements when 
         *         traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            if (left == head) {
                return false;
            }
            else {
                return true;
            }
        }

        /**
         * Return the next element in the list when going backward, 
         * and move the iterator backward by one node. 
         * 
         * @return the previous element in the list
         */
        @Override
        public E previous() {
            if (hasPrevious() == false) {
                throw new NoSuchElementException();
            }
            right = left;
            left = left.prev;
            this.forward = false;
            canRemoveOrSet = true;
            idx --;
            return right.data;
        }

        /**
         * Return the index of the element that would be returned by a call 
         * to next(). Return the list size if at the end of the list. 
         * 
         * @return the index of the element that would be returned by a 
         *         subsequent call to next, or list size if the list iterator 
         *         is at the end of the list
         */
        @Override
        public int nextIndex() {
            if (right == tail) {
                return size;
            }
            else {
                return idx;
            }
        }
        
        /**
         * Returns the index of the element that would be returned by a 
         * call to previous(). Return -1 if at the start of the list. 
         * 
         * @return the index of the element that would be returned by a 
         *         subsequent call to previous, or -1 if the list iterator is 
         *         at the start of the list
         */
        @Override
        public int previousIndex() {
            if (left == head) {
                return -1;
            }
            else {
                return idx - 1;
            }
        }

        /**
         * Insert the given item into the list immediately before the element 
         * that would be returned by next(). If we call previous() immediately 
         * following add, the newly added item would be returned. The value of 
         * the current index of the list iterator is increased by one. 
         * 
         * @param element the element to insert
         */
        @Override
        public void add(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            Node newNode = new Node(element);
            newNode.setNext(right);
            newNode.setPrev(left);
            left.setNext(newNode);
            right.setPrev(newNode);
            left = newNode;
            idx++;
            size++;
            canRemoveOrSet = false;
        }

        /**
         * For the node returned by the most recent next/previous call, 
         * replace its value with the new value element. 
         * 
         * @param element the element with which to replace the 
         *                last element returned by next or previous
         */
        @Override
        public void set(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            if (canRemoveOrSet == false) {
                throw new IllegalStateException();
            }
            if (forward == true) {
                left.setElement(element);
            }
            if (forward == false) {
                right.setElement(element);
            }
            canRemoveOrSet = false;
        }

        /**
         * Remove the last element node returned by 
         * the most recent next/previous call. 
         */
        @Override
        public void remove() {
            if (canRemoveOrSet == false) {
                throw new IllegalStateException();
            }
            if (forward == true) {
                left.prev.setNext(left.next);
                left.next.setPrev(left.prev);
                left.next = null;
                left.prev = null;
                left.setElement(null);
                idx --;
            }
            if (forward == false) {
                right.prev.setNext(right.next);
                right.next.setPrev(right.prev);
                right.next = null;
                right.prev = null;
                right.setElement(null);
            }
            canRemoveOrSet = false;
        }   
    }

    /**
     * Create a new MyListIterator and return it.
     * 
     * @return a new MyListIterator
     */
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }
        
    /**
     * Create a new MyListIterator and return it.
     * 
     * @return a new MyListIterator
     */
    public Iterator<E> iterator() {
        return new MyListIterator();
    }
}