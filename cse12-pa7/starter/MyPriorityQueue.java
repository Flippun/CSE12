/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA7 Write-up
 * 
 * This file is for CSE 12 PA7 in Spring 2023,
 * and contains the implementation of the MyPriorityQueue class.
 */

import java.util.Collection;

/**
 * Generic class that implements of a priority queue using
 * the MyMinHeap implementation
 * 
 * Instance Variablees:
 * heap - The underlying data structure of MyPriorityQueue.
 */
public class MyPriorityQueue<E extends Comparable<E>> {
   
    protected MyMinHeap<E> heap;

    /**
     * This no-argument constructor initializes heap to be an empty MyMinHeap
     */
    public MyPriorityQueue() {
        this.heap = new MyMinHeap<>();
    }

    /**
     * This constructor initializes heap to contain the elements in collection
     * 
     * @param collection initial collection of elements to be initialized
     */
    public MyPriorityQueue(Collection<? extends E> collection) {
        if (collection == null || collection.contains(null)) {
            throw new NullPointerException();
        }
        this.heap = new MyMinHeap<>(collection);
    }

    /**
     * Add element to this priority queue
     * 
     * @param element element to be added
     */
    public void push(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        heap.insert(element);
    }

    /**
     * Return the element with the highest priority
     * 
     * @return element of highest piority
     */
    public E peek() {
        if (heap.size() == 0) {
            return null;
        }
        return heap.getMin();
    }

    /**
     * Remove and return the element with the highest priority
     * 
     * @return element with highest priority
     */
    public E pop() {
        if (heap.size() == 0) {
            return null;
        }
        return heap.remove();
    }

    /**
     * Return the number of elements in the priority queue.
     * 
     * @return number of elements in the priority queue.
     */
    public int getLength() {
        return heap.size();
    }

    /**
     * Clear out the entire priority queue
     */
    public void clear() {
        heap.clear();
    }
}