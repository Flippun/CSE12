/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA7 Write-up
 * 
 * This file is for CSE 12 PA7 in Spring 2023,
 * and contains the implementation of the MyMinHeap class.
 */

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generic class that implements a data structure similar to 
 * java's builtin MinHeap structure.
 * 
 * Instance Variablees:
 * data - The underlying data structure of MyMinHeap.
 */
public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E>{
    
    private static final int DOUBLE = 2;
    private static final int HALF = 2;
    private static final int TWO_INDICES = 2;

    protected ArrayList<E> data;

    /**
     * Constructor for MyMinHeap class with no argument.
     */
    public MyMinHeap() {
        this.data = new ArrayList<E>();
    }

    /**
     * Constructor for MyMinHeap class with initial data.
     * 
     * @param collection initial collection of elements to be initialized
     */
    public MyMinHeap(Collection<? extends E> collection) {
        if (collection == null || collection.contains(null)) {
            throw new NullPointerException();
        }
        this.data = new ArrayList<>(collection);
        for (int i = data.size(); i >= 0; i--) {
            percolateDown(i);
        }
    }


    // ====================== HELPER METHODS =========================


    /**
     * Swap the elements at from and to indices in data.
     * 
     * @param from first element's index
     * @param to second element's index
     */
    protected void swap(int from, int to) {
        E fromData = data.get(from);
        E toData = data.get(to);
        data.set(from, toData);
        data.set(to, fromData);
    }

    /**
     * Calculate and return the parent index of the parameter index. 
     * 
     * @param index child index
     * @return parnt index of given child
     */
    protected static int getParentIdx(int index) {
        if (index == 0) {
            return -1;
        }
        int parentIdx = (index - 1) / HALF;
        return parentIdx;
    }

    /**
     * Calculate and return the left child index of the parameter index.
     * 
     * @param index given parent index
     * @return left child index of given parent
     */
    protected static int getLeftChildIdx(int index) {
        int leftChildIdx = (DOUBLE * index) + 1;
        return leftChildIdx;
    }

    /**
     * Calculate and return the right child index of the parameter index.
     * 
     * @param index given parent index
     * @return right child index of given parent
     */
    protected static int getRightChildIdx(int index) {
        int rightChildIdx = (DOUBLE * index) + TWO_INDICES;
        return rightChildIdx;
    }

    /**
     * Return the index of the smaller child of the element at index. 
     * 
     * @param index parent index
     * @return smaller child of parent index
     */
    protected int getMinChildIdx(int index) {
        int leftChildIdx = getLeftChildIdx(index);
        int rightChildIdx = getRightChildIdx(index);

        if (leftChildIdx >= data.size()) {
            return -1;
        }
        else if (rightChildIdx >= data.size()) {
            return leftChildIdx;
        }
        if (data.get(leftChildIdx).compareTo(data.get(rightChildIdx)) <= 0) {
            return leftChildIdx;
        }
        else {
            return rightChildIdx;
        }
    }

    /**
     * Percolate the element at index up until no heap properties are violated
     * by swapping the element with its parent as needed. 
     * 
     * @param index indeex of element to be percolated
     */
    protected void percolateUp(int index) {
        int parentIdx = getParentIdx(index);
        while (parentIdx >= 0  
                && data.get(index).compareTo(data.get(parentIdx)) < 0) {
            swap(index, parentIdx);
            index = parentIdx;
            parentIdx = getParentIdx(index);
        }
    }

    /**
     * Percolate the element at index down until no heap properties are violated
     * 
     * @param index index of element of be percolated
     */
    protected void percolateDown(int index) {
        int minChildIdx = getMinChildIdx(index);
        while (minChildIdx >= 0  
                && data.get(index).compareTo(data.get(minChildIdx)) > 0) {
            swap(index, minChildIdx);
            index = minChildIdx;
            minChildIdx = getMinChildIdx(index);
        }
    }

    /**
     * Remove the element at index from data and return it. 
     * 
     * @param index index of element to b removed
     * @return removed element at index
     */
    protected E deleteIndex(int index) {
        E deletedElement = data.get(index);
        swap(index, data.size() - 1);
        data.remove(data.size() - 1); 
        if (index == data.size()) {
            return deletedElement;
        }
        percolateDown(index);
        percolateUp(index);
        return deletedElement;
    }


    // ====================== CORE METHODS =========================


    /**
     * Add element to the end of the heap and percolate only the inserted 
     * element up until no heap properties are violated. 
     * 
     * @param element element to be inserted
     */
    public void insert(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        data.add(element);
        percolateUp(data.size() - 1);
    }

    /**
     * Return the root of the heap
     * 
     * @return the root of the heap
     */
    public E getMin() {
        if (data.isEmpty()) {
            return null;
        }
        return data.get(0);
    }
    
    /**
     * Remove and return the root
     * 
     * @return the root
     */
    public E remove() {
        if (data.isEmpty()) {
            return null;
        }
        return deleteIndex(0);
    }

    /**
     * Return the number of elements in this min-heap. 
     * 
     * @return the number of elements in this min-heap. 
     */
    public int size() {
        return data.size();
    }

    /**
     * Clear out the entire heap
     */
    public void clear() {
        data.clear();
    }
}
