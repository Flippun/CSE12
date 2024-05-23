/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Docs, PA4 Write-up
 * 
 * This file is for CSE 12 PA4 in Spring 2023,
 * and contains hidden tests for MyListIterator.
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

/**
 * This class contains hidden test cases for MyListIterator. listLen1 is a
 * linkedlist of length 1 and listLen2 is a linkedlist of length 2.
 */
public class MyListIteratorCustomTester {

    private MyLinkedList listLen1, listLen2;
    private MyLinkedList.MyListIterator listLen1Iter, listLen2Iter;

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
        listLen1 = new MyLinkedList();
        listLen1.add("Christine");
        listLen1Iter = listLen1.new MyListIterator();

        listLen2 = new MyLinkedList();
        listLen2.add("Paul");
        listLen2.add("Cao");
        listLen2Iter = listLen2.new MyListIterator();
    }

    /**
     * Aims to test the next() method when iterator is at end of the list 
     */
    @Test
    public void testNextEnd() {
        listLen1Iter.next();
        assertThrows(NoSuchElementException.class, () -> {
            listLen1Iter.next();
        });
    }

    /**
     * Aims to test the previous() method when iterator is at the start of the 
     * list 
     */
    @Test
    public void testPreviousStart() {
        assertThrows(NoSuchElementException.class, () -> {
            listLen1Iter.previous();
        });
    }

    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test
    public void testAddInvalid() {
        assertThrows(NullPointerException.class, () -> {
            listLen1Iter.add(null);
        });
    }

    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test
    public void testCantSet() {
        listLen1Iter.canRemoveOrSet = false;
        assertThrows(IllegalStateException.class, () -> {
            listLen1Iter.set("");
        });
    }


    /**
     * Aims to test the set(E e) method when an invalid element is set
     */
    @Test
    public void testSetInvalid() {
        assertThrows(NullPointerException.class, () -> {
            listLen1Iter.set(null);
        });
    }

    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test
    public void testCantRemove() {
        listLen1Iter.canRemoveOrSet = false;
        assertThrows(IllegalStateException.class, () -> {
            listLen1Iter.remove();
        });
    }

    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {
        listLen1Iter.next();
        assertFalse("call to hasNext() at end",
                listLen1Iter.hasNext());

        listLen2Iter.next();
        listLen2Iter.next();
        assertFalse("call to hasNext() at end",
                listLen2Iter.hasNext());
    }

    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {
        assertFalse("call to hasprevious()",
                listLen1Iter.hasPrevious());
    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {
        assertEquals("When left = head", -1,
                listLen1Iter.previousIndex());
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {
        listLen1Iter.next();
        assertEquals("When right = tail", 1,
                listLen1Iter.nextIndex());

        listLen2Iter.next();
        listLen2Iter.next();
        assertEquals("When right = tail", 2,
                listLen2Iter.nextIndex());
    }
}