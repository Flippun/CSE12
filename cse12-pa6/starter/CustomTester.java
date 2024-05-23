/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA6 Write-up
 * 
 * This file is for CSE 12 PA6 in Spring 2023,
 * and contains custom tests for MyDeque, MyStack, MyQueue.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class contains custom test cases for MyDeque, MyStack, and MyQueue
 */
public class CustomTester {
    /**
     * Helper method to initialize all instance variables of MyDeque
     * @param deque The deque to initialize
     * @param data The data array
     * @param size The value for size
     * @param front The value for front
     * @param rear The value for rear
     */
    static void initDeque(MyDeque<Integer> deque, Object[] data, int size,
                          int front, int rear) {
        deque.data = data;
        deque.size = size;
        deque.front = front;
        deque.rear = rear;
    }

    // ------------ Deque ---------------

    /** Test the Deque constructor */
    @Test
    public void testDequeConstructor() {
        MyDeque<Integer> deque = new MyDeque<>(10);
        assertEquals("Capacity", 10, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);

        deque = new MyDeque<>(0);
        assertEquals("Capacity", 0, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);

        assertThrows(IllegalArgumentException.class, () -> {
            new MyDeque<>(-1);
        });
    }

    /** Test the ExpandCapacity method for wrapping */
    @Test
    public void testExpandCapacity() {
        MyDeque<Integer> deque = new MyDeque<>(5);
        Integer[] orig = {4, null, 1, 2, 3};
        Integer[] finalOrdering = {1, 2, 3, 4, null, null, null, null, 
            null, null};
        initDeque(deque, orig, 4, 2, 0);

        deque.expandCapacity();

        assertEquals("Capacity", 10, deque.data.length);
        assertEquals("Size", 4, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 3, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(0);
        orig = new Integer[] {};
        finalOrdering = new Integer[] {null, null, null, null, null, null, 
            null, null, null, null};
        initDeque(deque, orig, 0, 0, 0);

        deque.expandCapacity();

        assertEquals("Capacity", 10, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(2);
        orig = new Integer[] {null, null};
        finalOrdering = new Integer[] {null, null, null, null};
        initDeque(deque, orig, 0, 0, 0);

        deque.expandCapacity();

        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(5);
        orig = new Integer[] {null, null, 1, 2, null};
        finalOrdering = new Integer[] {1, 2, null, null, null};
        initDeque(deque, orig, 2, 2, 3);

        deque.expandCapacity();

        assertEquals("Capacity", 10, deque.data.length);
        assertEquals("Size", 2, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 1, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }
    }

    /** Test the AddFirst method for proper wrapping */
    @Test
    public void testAddFirst() {
        MyDeque<Integer> deque = new MyDeque<>(10);
        Integer[] orig = {null, null, null, null, null, null, null, null, 
            null, null};
        Integer[] finalOrdering = {0, null, null, null, null, null, null, 
            null, 8, 9};
        initDeque(deque, orig, 0, 0, 0);

        deque.addFirst(0);
        deque.addFirst(9);
        deque.addFirst(8);

        assertEquals("Capacity", 10, deque.data.length);
        assertEquals("Size", 3, deque.size);
        assertEquals("Front", 8, deque.front);
        assertEquals("Rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(3);
        orig = new Integer[] {1, 2, 3};
        finalOrdering = new Integer[] {1, 2, 3, null, 5, 6};
        initDeque(deque, orig, 3, 0, 2);

        deque.addFirst(6);
        deque.addFirst(5);

        assertEquals("Capacity", 6, deque.data.length);
        assertEquals("Size", 5, deque.size);
        assertEquals("Front", 4, deque.front);
        assertEquals("Rear", 2, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        assertThrows(NullPointerException.class, () -> {
            new MyDeque<>(10).addFirst(null);
        });
    }

    /** Test the Addlast method for wrapping and expansion */
    @Test
    public void testAddLast() {
        MyDeque<Integer> deque = new MyDeque<>(4);
        Integer[] orig = {3, null, 1, 2};
        Integer[] finalOrdering = {1, 2, 3, 4, 5, null, null, null};
        initDeque(deque, orig, 3, 2, 0);

        deque.addLast(4);
        deque.addLast(5);

        assertEquals("Capacity", 8, deque.data.length);
        assertEquals("Size", 5, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 4, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(4);
        orig = new Integer[] {null, 1, 2, 3};
        finalOrdering = new Integer[] {0, 1, 2, 3};
        initDeque(deque, orig, 3, 1, 3);

        deque.addLast(0);

        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 4, deque.size);
        assertEquals("Front", 1, deque.front);
        assertEquals("Rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        assertThrows(NullPointerException.class, () -> {
            new MyDeque<>(10).addFirst(null);
        });
    }

    /** Test the removeFirst method */
    @Test
    public void testremoveFirst() {
        MyDeque<Integer> deque = new MyDeque<>(4);
        Integer[] orig = {3, null, 1, 2};
        Integer[] finalOrdering = {3, null, null, null};
        initDeque(deque, orig, 3, 2, 0);

        Object removed1 = deque.removeFirst();
        Object removed2 = deque.removeFirst();

        assertEquals("removed1", 1, removed1);
        assertEquals("removed2", 2, removed2);
        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 1, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(5);
        orig =  new Integer[] {2, 3, 4, 5, 1};
        finalOrdering =  new Integer[] {null, 3, 4, 5, null};
        initDeque(deque, orig, 5, 4, 3);

        Object removed3 = deque.removeFirst();
        Object removed4 = deque.removeFirst();

        assertEquals("removed3", 1, removed3);
        assertEquals("removed4", 2, removed4);
        assertEquals("Capacity", 5, deque.data.length);
        assertEquals("Size", 3, deque.size);
        assertEquals("Front", 1, deque.front);
        assertEquals("Rear", 3, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(2);
        orig =  new Integer[] {null, null};
        initDeque(deque, orig, 0, 0, 0);

        Object removed5 = deque.removeFirst();
        assertEquals("removed5", null, removed5);
        assertEquals("Capacity", 2, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);

        deque = new MyDeque<>(0);
        orig =  new Integer[] {};
        initDeque(deque, orig, 0, 0, 0);

        Object removed6 = deque.removeFirst();
        assertEquals("removed6", null, removed6);
        assertEquals("Capacity", 0, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);
    }

    /** Test the removeLast method */
    @Test
    public void testremoveLast() {
        MyDeque<Integer> deque = new MyDeque<>(4);
        Integer[] orig = {3, null, 1, 2};
        Integer[] finalOrdering = {null, null, 1, null};
        initDeque(deque, orig, 3, 2, 0);

        Object removed1 = deque.removeLast();
        Object removed2 = deque.removeLast();

        assertEquals("removed1", 3, removed1);
        assertEquals("removed2", 2, removed2);
        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 1, deque.size);
        assertEquals("Front", 2, deque.front);
        assertEquals("Rear", 2, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(5);
        orig =  new Integer[] {4, 5, 1, 2, 3};
        finalOrdering =  new Integer[] {null, null, 1, 2, null};
        initDeque(deque, orig, 5, 2, 1);

        Object removed3 = deque.removeLast();
        Object removed4 = deque.removeLast();
        Object removed5 = deque.removeLast();
        
        assertEquals("removed3", 5, removed3);
        assertEquals("removed4", 4, removed4);
        assertEquals("removed5", 3, removed5);
        assertEquals("Capacity", 5, deque.data.length);
        assertEquals("Size", 2, deque.size);
        assertEquals("Front", 2, deque.front);
        assertEquals("Rear", 3, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(2);
        orig =  new Integer[] {null, null};
        finalOrdering =  new Integer[] {null, null};
        initDeque(deque, orig, 0, 0, 0);

        Object removed6 = deque.removeLast();
        assertEquals("removed6", null, removed6);
        assertEquals("Capacity", 2, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);

        deque = new MyDeque<>(0);
        orig =  new Integer[] {};
        initDeque(deque, orig, 0, 0, 0);

        Object removed7 = deque.removeFirst();
        assertEquals("removed7", null, removed7);
        assertEquals("Capacity", 0, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 0, deque.front);
        assertEquals("Rear", 0, deque.rear);

        deque = new MyDeque<>(4);
        orig =  new Integer[] {2, null, null, 1};
        finalOrdering =  new Integer[] {null, null, null, 1};
        initDeque(deque, orig, 2, 3, 0);

        Object removed8 = deque.removeLast();
        
        assertEquals("removed8", 2, removed8);
        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 1, deque.size);
        assertEquals("Front", 3, deque.front);
        assertEquals("Rear", 3, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(4);
        orig =  new Integer[] {null, null, null, 1};
        finalOrdering =  new Integer[] {null, null, null, null};
        initDeque(deque, orig, 1, 3, 3);

        Object removed9 = deque.removeLast();
        
        assertEquals("removed9", 1, removed9);
        assertEquals("Capacity", 4, deque.data.length);
        assertEquals("Size", 0, deque.size);
        assertEquals("Front", 3, deque.front);
        assertEquals("Rear", 3, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }
    }

    /** Test the peekFirst method */
    @Test
    public void testPeekFirst() {
        MyDeque<Integer> deque = new MyDeque<>(10);
        Integer[] orig = {23, 5, 1, null, null, null, null, null, null,
            null};
        Integer[] finalOrdering = {23, 5, 1, null, null, null, null, null, null,
            null};
        initDeque(deque, orig, 3, 0, 2);

        assertEquals("Value at front should be returned",
                Integer.valueOf(23), deque.peekFirst());

        assertEquals("capacity", 10, deque.data.length);
        assertEquals("size", 3, deque.size);
        assertEquals("front", 0, deque.front);
        assertEquals("rear", 2, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(2);
        orig = new Integer[] {null, null};
        finalOrdering = new Integer[] {null, null};
        initDeque(deque, orig, 0, 0, 0);

        assertEquals("value", null, deque.peekFirst());
        
        assertEquals("capacity", 2, deque.data.length);
        assertEquals("size", 0, deque.size);
        assertEquals("front", 0, deque.front);
        assertEquals("rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }
    }

    /** Test the peekLast method */
    @Test
    public void testPeekLast() {
        MyDeque<Integer> deque = new MyDeque<>(10);
        Integer[] orig = {23, 5, 1, null, null, null, null, null, null,
            null};
        Integer[] finalOrdering = {23, 5, 1, null, null, null, null, null, null,
            null};
        initDeque(deque, orig, 3, 0, 2);

        assertEquals("Value at rear",
                Integer.valueOf(1), deque.peekLast());

        assertEquals("capacity", 10, deque.data.length);
        assertEquals("size", 3, deque.size);
        assertEquals("front", 0, deque.front);
        assertEquals("rear", 2, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }

        deque = new MyDeque<>(2);
        orig = new Integer[] {null, null};
        finalOrdering = new Integer[] {null, null};
        initDeque(deque, orig, 0, 0, 0);

        assertEquals("value", null, deque.peekLast());
        
        assertEquals("capacity", 2, deque.data.length);
        assertEquals("size", 0, deque.size);
        assertEquals("front", 0, deque.front);
        assertEquals("rear", 0, deque.rear);
        for (int i = 0; i < deque.size; i++) {
            assertEquals("Deque structure should be maintained",
                    finalOrdering[i], deque.data[i]);
        }
    }


    // ------------------------ Stack ---------------------------


    /** Test the Stack constructor, initialize stack with capacity 10 */
    @Test
    public void testStackConstructor() {
        MyStack<Integer> stack = new MyStack<>(10);

        assertEquals("Capacity should be initialized to 10", 10,
                stack.theStack.data.length);
        assertEquals("Size should be initialized to 0", 0,
                stack.theStack.size);
        assertEquals("Front should be initialized to 0", 0,
                stack.theStack.front);
        assertEquals("Rear should be initialized to 0", 0,
                stack.theStack.rear);

        assertThrows(IllegalArgumentException.class, () -> {
            new MyStack<>(-1);
        });
    }

    /** Test empty on stack with size 0 */
    @Test
    public void testStackEmpty() {
        MyStack<Integer> stack = new MyStack<>(10);
        Integer[] orig = {null, null, null, null, null, null, null, null,
            null, null};
        initDeque(stack.theStack, orig, 0, 0, 0);

        assertTrue("Call to empty should return true", stack.empty());
        assertEquals("Capacity should not have changed", 10,
            stack.theStack.data.length);
        assertEquals("Size should not have changed", 0, stack.theStack.size);
        assertEquals("Front should not have changed", 0, stack.theStack.front);
        assertEquals("Rear should not have changed", 0, stack.theStack.rear);
    }

    /** Test to ensure the LIFO behavior of stack */
    @Test
    public void testStackBehavior() {
        MyStack<Integer> stack = new MyStack<>(5);
        Integer[] orig = {2, 1, 5, 4, 3};
        initDeque(stack.theStack, orig, 5, 2, 1);

        stack.push(6);
        assertEquals("Capacity", 10, stack.theStack.data.length);
        assertEquals("Size", 6, stack.theStack.size);
        
        Integer firstElement = stack.theStack.peekFirst();
        Integer lastElement = stack.theStack.peekLast();
        if (stack.peek() == firstElement) {
            assertEquals("Size", 6, stack.theStack.size);
            assertEquals("Capacity", 10, stack.theStack.data.length);
            
            Integer element = stack.pop();
            assertEquals("Size should have decremented", 5,
                    stack.theStack.size);
            assertEquals("Capacity", 10, stack.theStack.data.length);
            if (element != firstElement) {
                fail("Wrong element popped");
            } 
            if (stack.theStack.peekFirst() != 5) {
                fail("Next element to remove is wrong");
            }
        }
        
        else if (stack.peek() == lastElement) {
            assertEquals("Size", 6, stack.theStack.size);
            assertEquals("Capacity", 10, stack.theStack.data.length);
            
            Integer element = stack.pop();
            assertEquals("Size should have decremented", 5,
                    stack.theStack.size);
            assertEquals("Capacity", 10, stack.theStack.data.length);
            if (element != lastElement) {
                fail("Wrong element popped");
            } 
            if (stack.theStack.peekLast() != 1) {
                fail("Next element to remove is wrong");
            }
        }

        else {
            fail("pushed element should be at an end");
        }
    }


    // ------------------------ Queue ---------------------------


    /** Test the Queue constructor */
    @Test
    public void testQueueConstructor() {
        MyQueue<Integer> queue = new MyQueue<>(10);
       
        assertEquals("Capacity", 10, queue.theQueue.data.length);
        assertEquals("Size", 0, queue.theQueue.size);
        assertEquals("Front", 0, queue.theQueue.front);
        assertEquals("Rear", 0, queue.theQueue.rear);

        assertThrows(IllegalArgumentException.class, () -> {
            new MyQueue<>(-1);
        });
    }

    /** Test empty on queue */
    @Test
    public void testQueueEmpty() {
        MyQueue<Integer> queue = new MyQueue<>(5);
        Integer[] orig = {null, null, null, null, null};
        initDeque(queue.theQueue, orig, 0, 0, 0);

        assertTrue("Empty Call", queue.empty());
        assertEquals("Capacity", 5, queue.theQueue.data.length);
        assertEquals("Size", 0, queue.theQueue.size);
        assertEquals("Front", 0, queue.theQueue.front);
        assertEquals("Rear", 0, queue.theQueue.rear);

        orig = new Integer[] {1, 2, 3, 4, 5};
        initDeque(queue.theQueue, orig, 5, 0, 4);

        assertFalse("Empty Call", queue.empty());
    }

    /** Test to ensure the FIFO behavior of queue*/
    @Test
    public void testQueueBehavior() {
        MyQueue<Integer> queue = new MyQueue<>(5);
        Integer[] orig = {4, 5, 1, 2, 3};
        initDeque(queue.theQueue, orig, 5, 2, 1);

        // {1, 2, 3, 4, 5, 6, null, null, null, null}
        queue.enqueue(6);
        assertEquals("Capacity", 10, queue.theQueue.data.length);
        assertEquals("Size", 6, queue.theQueue.size);
        
        Integer firstElement = queue.theQueue.peekFirst();
        Integer lastElement = queue.theQueue.peekLast();
        if (queue.peek() == firstElement) {
            assertEquals("Size", 6, queue.theQueue.size);
            assertEquals("Capacity", 10, queue.theQueue.data.length);
            
            Integer element = queue.dequeue();
            assertEquals("Size should have decremented", 5,
                    queue.theQueue.size);
            assertEquals("Capacity", 10, queue.theQueue.data.length);
            if (element != firstElement) {
                fail("Wrong element popped");
            } 
            if (queue.theQueue.peekLast() != 6) {
                fail("Next element to remove is wrong");
            }
        }
        
        else if (queue.peek() == lastElement) {
            assertEquals("Size", 6, queue.theQueue.size);
            assertEquals("Capacity", 10, queue.theQueue.data.length);
            
            Integer element = queue.dequeue();
            assertEquals("Size should have decremented", 5,
                    queue.theQueue.size);
            assertEquals("Capacity", 10, queue.theQueue.data.length);
            if (element != lastElement) {
                fail("Wrong element popped");
            } 
            if (queue.theQueue.peekFirst() != 6) {
                fail("Next element to remove is wrong");
            }
        }

        else {
            fail("pushed element should be at an end");
        }
    }
}