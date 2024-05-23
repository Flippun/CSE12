/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA6 Write-up
 * 
 * This file is for CSE 12 PA6 in Spring 2023,
 * and contains the implementation of the MyDeque class.
 */


/**
 * Generic class that implements a data structure similar to 
 * java's builtin Deque structure.
 * 
 * Instance Variablees:
 * data - the underlying data structure of the Deque.
 * size - the number of valid elements in the data array
 * rear - the index of the last element in the Deque
 * front - the index of the first element in the Deque
 */
public class MyDeque<E> implements DequeInterface<E>{
    
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DOUBLE = 2;
    
    Object[] data;
    int size;
    int rear;
    int front;

    /**
     * Constructor for Deque class.
     * 
     * @param initialCapacity intital length of data array
     */
    public MyDeque(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
        this.rear = 0;
        this.front = 0;
    }

    /**
     * Returns the number of elements in this DequeInterface.
     *
     * @return the number of elements in this DequeInterface
     */
    public int size() {
        return this.size; 
    }

    /**
     * Doubles the capacity of this DequeInterface. If the capacity
     * is 0, set capacity to some default capacity. No elements in this
     * DequeInterface should change after the expansion.
     */
    public void expandCapacity() {
        if (data.length == 0) {
            data = new Object[DEFAULT_CAPACITY];
            return;
        }
        Object[] expandedData = new Object[data.length * DOUBLE];
        int currIndex = 0;
        for (int i = front; currIndex < size; i++) {
            if (i == data.length) {
                i = 0;
            }
            expandedData[currIndex] = data[i];
            currIndex++;
        }
        front = 0;
        if (expandedData[front] == null) {
            rear = 0;
        }
        else {
            rear = size - 1;
        }
        data = expandedData;
    }

    /**
     * Adds the specified element to the front of this DequeInterface. If this
     * DequeInterface is at capacity, expandCapacity is called to double the
     * size of this container.
     *
     * @param element the element to add to the front of the array
     * @throws NullPointerException if the specified element is null.
     */
    public void addFirst(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (data.length == size()) {
            expandCapacity();
        }
        for (int i = front;; i--) {
            if (data[i] == null) {
                data[i] = element;
                front = i;
                size++;
                break;
            }
            if (i == 0) {
                i = data.length;
            }
        }
    }

    /**
     * Adds the specified element to the back of this DequeInterface. If the
     * DequeInterface is at capacity, expandCapacity is called to double the
     * size of this container.
     *
     * @param element the element to add to the back of the array
     * @throws NullPointerException if the specified element is null.
     */
    public void addLast(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (data.length == size()) {
            expandCapacity();
        }
        for (int i = rear;; i++) {
            if (i == data.length) {
                i = 0;
            }
            if (data[i] == null) {
                data[i] = element;
                rear = i;
                size++;
                break;
            }
        }
    }

    /**
     * Removes the element at the front of this DequeInterface, and returns the
     * element removed, or null if there was no such element.
     *
     * @return the element removed, or null if there's none.
     */
    public E removeFirst() {
        if (size == 0 || data.length == 0) {
            return null;
        }
        Object removedElement = data[front];    
        data[front] = null;
        size--;
        if (size == 0) {
            // do nothing
        }
        else if (front == data.length - 1) {
            front = 0;
        }
        else {
            front++;
        }
        return (E) removedElement;
    }

    /**
     * Removes the element at the back of this DequeInterface, and returns the
     * element removed, or null if there was no such element.
     *
     * @return the element removed, or null if there's none.
     */
    public E removeLast() {
        if (size == 0 || data.length == 0) {
            return null;
        }
        Object removedElement = data[rear];
        data[rear] = null;
        size--;
        if (size == 0) {
            // do nothing
        }
        else if (rear == 0) {
            rear = data.length - 1;
        }
        else {
            rear--;
        }
        return (E) removedElement;
    }

    /**
     * Returns the element at the front of this DequeInterface,
     * or null if there was no such element.
     *
     * @return the element at the front, or null if there's none.
     */
    public E peekFirst() {
        if (size() > 0) {
            return (E) data[front];
        }
        return null;
    } 

    /**
     * Returns the element at the back of this DequeInterface,
     * or null if there was no such element.
     *
     * @return the element at the back, or null if there's none.
     */
    public E peekLast() {
        if (size() > 0) {
            return (E) data[rear];
        }
        return null;
    }
}