/*
 * Name: Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: Java Interface Documentation, PA3 Write-up
 * 
 * This file is for CSE 12 PA3 in Spring 2023,
 * and contains hidden test cases for MyLinkedList.
 */

/**
 * IMPORTANT: Do not change the method headers. Your tests will be run against
 * good and bad implementations of MyLinkedList. You will only receive points
 * if your test passes when run on a good implementation and fails for the
 * corresponding bad implementation.
 */

import static org.junit.Assert.*;
import org.junit.*;

/**
 * This class creates a test fixture and runs multiple tests on 
 * the implementation for MyLinkedList.  
 *
 * Instance Variables:
 * emptyIntegerList - An empty integer list for the test fixture
 * threeStringList - An empty string list for the test fixture
 * strData - A string array for the test fixture
 */
public class MyLinkedListCustomTester {

	private MyLinkedList<Integer> emptyIntegerList;
    private MyLinkedList<String> threeStringList;
    private String[] strData = {"CSE 12", "Paul Cao", "Flip Frauenzimmer"};

	/**
	 * This sets up the test fixture. JUnit invokes this method before
	 * every testXXX method. The @Before tag tells JUnit to run this method
	 * before each test.
	 */
	@Before
	public void setUp() throws Exception {
        emptyIntegerList = new MyLinkedList<Integer>();
        threeStringList = new MyLinkedList<>();
	}

    /**
     * Manually populates the list to ensure 
     * the internal state is correct. In this way, potential errors in add() 
     * won't cause failures for other methods.
     */
    private void populateLinkedList() {
        MyLinkedList<String>.Node node0 =  
            this.threeStringList.new Node(this.strData[0]);
        MyLinkedList<String>.Node node1 =  
            this.threeStringList.new Node(this.strData[1]);
        MyLinkedList<String>.Node node2 =  
            this.threeStringList.new Node(this.strData[2]);

        this.threeStringList.head.next = node0;
        node0.prev = this.threeStringList.head;
        node0.next = node1;
        node1.prev = node0;
        node1.next = node2;
        node2.prev = node1;
        node2.next = this.threeStringList.tail;
        this.threeStringList.tail.prev = node2;
        this.threeStringList.size = 3;
    }

	/**
	 * Aims to test the add(E data) method with a valid argument.
	 */
	@Test
	public void testCustomAdd() {
		this.populateLinkedList();
        
        assertEquals("Add method should return true", 
            true, this.threeStringList.add("Goodbye."));
        assertEquals("Add method should return true", 
            true, this.threeStringList.add("ToEnd"));
        
        this.emptyIntegerList.add(1);
        assertEquals("Head should have access to newNode", 
            Integer.valueOf(1), this.emptyIntegerList.head.next.data);
        assertEquals("newNode prev should point to head", 
            this.emptyIntegerList.head, this.emptyIntegerList.head.next.prev);
        assertEquals("newNode next should point to tail", 
            this.emptyIntegerList.tail, this.emptyIntegerList.head.next.next);
        assertEquals("head should point to newNode", 
            Integer.valueOf(1), this.emptyIntegerList.head.next.data);
        assertEquals("tail should point to newNode", 
            Integer.valueOf(1), this.emptyIntegerList.tail.prev.data);
        assertEquals("size should be updated properply", 
            1, this.emptyIntegerList.size);
	}

	/**
	 * Aims to test the add(int index, E data) method.
	 * Add a valid argument to the beginning of MyLinkedList.
	 */
	@Test
	public void testCustomAddIdxToStart() {
		this.populateLinkedList();
        this.threeStringList.add(0, "Start");

        assertEquals("New node should be accessible from head", 
            "Start", this.threeStringList.head.next.data);
        assertEquals("New node should be accessible from next", 
            "Start", this.threeStringList.head.next.next.prev.data);
        assertEquals("New node next should point to proper next", 
            "CSE 12", this.threeStringList.head.next.next.data);
        assertEquals("New node prev should point to head", 
            null, this.threeStringList.head.next.prev.data);
        assertEquals("Proper pointers adjusted from tail", 
            null, this.threeStringList.tail.prev.prev.prev.prev.prev.data);
        assertEquals("Proper pointers adjusted from head", 
            null, this.threeStringList.head.next.next.next.next.next.data);
        assertEquals("size should be updated properply", 
            4, this.threeStringList.size);

        this.emptyIntegerList.add(0, 1);
        assertEquals("New node next should point to tail", 
            null, this.emptyIntegerList.tail.data);
	}

	/**
	 * Aims to test the add(int index, E data) method.
	 * Add a valid argument to the middle of MyLinkedList.
	 */
	@Test
	public void testCustomAddIdxToMiddle() {
		this.populateLinkedList();
        this.threeStringList.add(1, "Middle");

        assertEquals("New node should be accessible from head", 
            "Middle", this.threeStringList.head.next.next.data);
        assertEquals("New node should be accessible from next", 
            "Middle", this.threeStringList.head.next.next.next.prev.data);
        assertEquals("New node should be accessible from prev", 
            "Middle", this.threeStringList.head.next.next.data);
        assertEquals("New node next should point to proper next", 
            "Paul Cao", this.threeStringList.head.next.next.next.data);
        assertEquals("New node prev should point to proper node", 
            "CSE 12", this.threeStringList.head.next.next.prev.data);
        assertEquals("Proper pointers adjusted from tail", 
            null, this.threeStringList.tail.prev.prev.prev.prev.prev.data);
        assertEquals("Proper pointers adjusted from head", 
            null, this.threeStringList.head.next.next.next.next.next.data);
        assertEquals("size should be updated properply", 
            4, this.threeStringList.size);
	}

	/**
	 * Aims to test the remove(int index) method. Remove from an empty list.
	 */
	@Test
	public void testCustomRemoveFromEmpty() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyIntegerList.remove(0);
            emptyIntegerList.remove(-1);
            emptyIntegerList.remove(5);
        });
	}

	/**
	 * Aims to test the remove(int index) method.
	 * Remove a valid argument from the middle of MyLinkedList.
	 */
	@Test
	public void testCustomRemoveFromMiddle() {
		this.populateLinkedList();

        assertEquals("removedNode data should be returned", 
            "Paul Cao", this.threeStringList.remove(1));
        assertEquals("Node at removed index should change", 
            "Flip Frauenzimmer", this.threeStringList.head.next.next.data);
        assertEquals("Proper pointers adjusted for node", 
            null, this.threeStringList.head.next.next.next.data);
        assertEquals("Proper pointers adjusted for tail", 
            "Flip Frauenzimmer", this.threeStringList.tail.prev.data);
        assertEquals("Proper pointers adjusted for 1st", 
            "Flip Frauenzimmer", this.threeStringList.head.next.next.data);
        assertEquals("Proper pointers adjusted from tail", 
            null, this.threeStringList.tail.prev.prev.prev.data);
        assertEquals("Proper pointers adjusted from head", 
            null, this.threeStringList.head.next.next.next.data);
        assertEquals("size should be updated properply", 
            2, this.threeStringList.size);
	}

	/**
	 * Aims to test the set(int index, E data) method.
	 * Set an out-of-bounds index with a valid data argument.
	 */
	@Test
	public void testCustomSetIdxOutOfBounds() {
		this.populateLinkedList();
    
        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyIntegerList.set(5, 1);
            emptyIntegerList.set(-1, 1);
            emptyIntegerList.set(0, 1);
        });
	}
}