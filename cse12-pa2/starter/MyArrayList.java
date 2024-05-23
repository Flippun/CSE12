/**
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: Java Interface Documentation, PA2 Write-up
 * 
 * This file is for CSE 12 PA2 in Spring 2023,
 * and contains the implementation of the MyArrayList class
 * which emulates Java's ArrayList data structure.
 */

/**
 * A generic class that specifies a data structure similar to Java's ArrayList
 * 
 * Instance Variables:
 * data - Object array that is the underlying data structure of the ArrayList
 * size - Equal to the number of valid elements in the data array.
 */
public class MyArrayList<E> implements MyList<E> {
    
    private static final int DEFAULT_CAPACITY = 5;
    private static final int MY_CAPACITY = 3;
    private static final String INVALID_CAPACITY = 
            "requiredCapacity is less than initial capacity";
    private static final String INVALID_INDEX = 
            "invalid index";

    Object[] data;
    int size;
    
    /**
     * Initialize the Object array with the default length of 5
     * 
     */
    public MyArrayList() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Initialize the Object array with the length of initialCapacity
     * throws exception when invalid initialCapacity
     * 
     * @param initialCapacity inputed capacity for custom array
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                INVALID_CAPACITY);
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Initialize the instance variables with the input array (shallow copy)
     * If arr is null, constructs an ArrayList with the default capacity
     * 
     * @param arr inputed array to be copied
     */
    public MyArrayList (E[] arr){ 
        if (arr == null) {
            this.data = new Object[DEFAULT_CAPACITY];
            this.size = 0;
        }
        else {
            this.data = arr;
            this.size = arr.length;
        }
    }

    /**
     *Increase the capacity of underlying array if needed
     *
     * @param requiredCapacity minimum capacity after expanding
     */
    public void expandCapacity (int requiredCapacity) {
        // throws exception if data capacity already meets required capacity
        if (data.length > requiredCapacity) {
            throw new IllegalArgumentException(
                INVALID_CAPACITY);
        }
        // resets the capacity to the default capacity of 5
        if (data.length == 0) {
            Object[] tempArray = {null, null, null, null, null};
            data = tempArray;
        }
        // increases capacity by 3 if less than required
        if (data.length < requiredCapacity) {
            // creates temporary array of new size, transfers data
            Object[] tempArray = new Object[data.length + MY_CAPACITY];
            for (int i = 0; i < data.length; i++) {
                tempArray[i] = data[i];
            }
            for (int i = data.length; i < tempArray.length; i++) {
                tempArray[i] = null;
            }
            data = tempArray;
        }
        // if still less than required, expand capacity to exactly required
        if (data.length < requiredCapacity) {
            // creates temporary array of new size, transfers data
            Object[] tempArray = new Object[requiredCapacity];
            for (int i = 0; i < data.length; i++) {
                tempArray[i] = data[i];
            }
            for (int i = data.length; i < tempArray.length; i++) {
                tempArray[i] = null;
            }
            data = tempArray;
        }
    }

    /**
     * Get the amount of elements array can hold
     *
     * @return number of elements that can be held
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * Add an element at the specified index
     *
     * @param index   position to insert the element
     * @param element the element to insert
     */
    public void insert(int index, E element) {
        // throws exception if invalid index
        if (index < 0 || index > data.length) {
            throw new IndexOutOfBoundsException(INVALID_INDEX);
        }
        // expands capacity if at capacity
        if (data.length == size) {
            expandCapacity(data.length + 1);
        }
        // inserts element at index
        for (int i = data.length - 1; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    /**
     * Add an element to the end of the list
     *
     * @param element the element to append
     */
    public void append(E element) {
        insert(size, element);
    }

    /**
     * Add an element to the beginning of the list 
     *
     * @param element the element to prepend
     */
    public void prepend(E element) {
        insert(0, element);
    }

    /**
     * Get the element at the given index
     *
     * @param index the index at which to return the element
     * @return the element at the index
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        // throws exception if invalid index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INVALID_INDEX);
        }
        return (E) data[index];
    }

    /**
     * Replaces an element at the specified index with a new element and return
     * the original element
     *
     * @param index   the index at which to replace
     * @param element the element with which to replace
     * @return the original element
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        Object removedElement = get(index);
        data[index] = element;
        return (E) removedElement;
    }
    
    /**
     * Remove the element at the specified index and return the removed element
     *
     * @param index the index at which to remove the element
     * @return the removed element
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        Object removedElement = get(index);
        for (int i = index; i < data.length - 1; i++) {
            data[i] = data[i + 1];
        }
        data[data.length - 1] = null;
        size --;
        return (E) removedElement;
    }

    /**
     * Get the number of elements in the list
     *
     * @return number of elements in the list
     */
    public int size() {
        return size;
    }
}