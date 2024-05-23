/**
 * Name: Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: Java Interface Documentation, PA2 Write-up
 * 
 * This file is for CSE 12 PA2 in Spring 2023,
 * and contains hidden test cases for MyArrayList.
 */

import static org.junit.Assert.*;

import org.junit.*;

/**
 * This class creates a test fixture and runs multiple tests on 
 * the implementation for MyArrayList.  
 * 
 * Instance variables:
 * arr - Object array for the test fixture
 * arrInts - Integer array for the test fixture
 * arrNonEmptyInts - Integer array for the test fixture
 * arrNull - Null array for the test fixture
 */
public class MyArrayListHiddenTester {
    // Do not change the method signatures!
    
    static final int DEFAULT_CAPACITY = 5;
    static final int MY_CAPACITY = 3;

    Object[] arr = new Object[10];
    Integer[] arrInts = { 1, 2, 3 };
    Integer[] arrNonEmptyInts = {1, null, null}; // NOTE: LIST OF SIZE THREE
    Object[] arrNull = null;

    private MyArrayList listEmpty, listNonEmpty, listWithInt, listNull;
    
    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test 
     */
    @Before
    public void setUp() throws Exception {
        listEmpty = new MyArrayList();
        listNonEmpty = new MyArrayList<>(arrNonEmptyInts);
        listWithInt = new MyArrayList<Integer>(arrInts);
        listNull = new MyArrayList(arrNull);
    }

    /**
     * Aims to test the constructor when the input argument
     * is not valid
     */
    @Test
    public void testConstructorInvalidArg() {
        assertThrows(IllegalArgumentException.class, 
                () -> new MyArrayList(-1));
    }

    /**
     * Aims to test the constructor when the input argument is null
     */
    @Test
    public void testConstructorNullArg() {
        assertEquals("Check size for default constructor", 0, listNull.size);
        assertArrayEquals("Check data", new Object[5], listNull.data);
    }

    /**
     * Aims to test the constructor when the input array has null elements
     */
    @Test
    public void testConstructorArrayWithNull() {
        assertEquals("Check size for constructor with null value", 
                3, listNonEmpty.size);
        assertArrayEquals("Check data", new Integer[]{1 , null, null}, 
                listNonEmpty.data);
    }

    /**
     * Aims to test the append method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testAppendAtCapacity() {
        listWithInt.append(4);

        assertArrayEquals("Check for successful append", 
                new Integer[]{1, 2, 3, 4, null, null}, listWithInt.data);
        assertEquals("Check list size after the append", 4, listWithInt.size);
        assertEquals("Check list capacity after the append", 6, 
                listWithInt.data.length);
    }

    /**
     * Aims to test the append method when null is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testAppendNull() {
        listWithInt.append(null);

        assertArrayEquals("Check for successful append", 
                new Integer[]{1, 2, 3, null, null, null}, listWithInt.data);
        assertEquals("Check list size after the append", 4, listWithInt.size);
        assertEquals("Check list capacity after the append", 6, 
                listWithInt.data.length);
    }

    /**
     * Aims to test the prepend method when an element is added to a full list
     * Check reflection on size and capacity
     */
    @Test
    public void testPrependAtCapacity() {
        listWithInt.prepend(0);

        assertArrayEquals("Check for successful prepend", 
                new Integer[]{0, 1, 2, 3, null, null}, listWithInt.data);
        assertEquals("Check list size after the prepend", 4, listWithInt.size);
        assertEquals("Check list capacity after the prepend", 6, 
                listWithInt.data.length);
    }
    
    /**
     * Aims to test the prepend method when a null element is added
     * Checks reflection on size and capacity
     * Checks whether null was added successfully
     */
    @Test
    public void testPrependNull() {
        listWithInt.prepend(null);

        assertArrayEquals("Check for successful prepend", 
                new Integer[]{null, 1, 2, 3, null, null}, listWithInt.data);
        assertEquals("Check list size after the prepend", 4, listWithInt.size);
        assertEquals("Check list capacity after the prepend", 6, 
                listWithInt.data.length);
    }
    
    /**
     * Aims to test the insert method when input index is out of bounds
     */
    @Test
    public void testInsertOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.insert(-1, null));
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.insert(18, null));
    }

    /**
     * Insert multiple (eg. 1000) elements sequentially beyond capacity -
     * Check reflection on size and capacity
     * Hint: for loop could come in handy
     */
    @Test
    public void testInsertMultiple() {
        for (int i = 0; i < 1000; i++) {
            listEmpty.insert(i, i);
        }
        assertEquals("Check list size after the insert", 1000, listEmpty.size);
        assertEquals("Check list capacity after the append", 1001, 
                listEmpty.data.length);
    }

    /**
     * Aims to test the get method when input index is out of bound
     */
    @Test
    public void testGetOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.get(-1));
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.get(3));
    }

    /**
     * Aims to test the set method when input index is out of bound
     */
    @Test
    public void testSetOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.set(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.set(3, 1));
    }
    
    /**
     * Aims to test the remove method when removing multiple items from a list
     */
    @Test
    public void testRemoveMultiple() {
        listWithInt.remove(0);
        listWithInt.remove(1);

        assertArrayEquals("Check for successful removal", 
                new Integer[]{2, null, null}, listWithInt.data);
        assertEquals("Check list size after removal", 1, listWithInt.size);
        assertEquals("Check list capacity after removal", 3, 
                listWithInt.data.length);
    }

    /**
     * Aims to test the remove method when input index is out of bound
     */
    @Test
    public void testRemoveOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, 
                () -> listWithInt.remove(3));
    }

    /**
     * Aims to test the expandCapacity method when 
     * requiredCapacity is strictly less than the current capacity
     */
    @Test
    public void testExpandCapacitySmaller() {
        assertThrows(IllegalArgumentException.class, 
                () -> listWithInt.expandCapacity(1));
    }

    /**
     * Aims to test the expandCapacity method when 
     * requiredCapacity is greater than current capacity+3 and default capacity
     */
    @Test
    public void testExpandCapacityLarge() {
        listWithInt.expandCapacity(7);
        
        assertEquals("Check list capacity after expansion", 7, 
                listWithInt.data.length);

        listWithInt.expandCapacity(20);
        
        assertEquals("Check list capacity after expansion", 20, 
                listWithInt.data.length);
    }
}
