import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class CustomTeser {
    /**
     * Helper method to initialize all instance variables of MyMinHeap
     *
     * @param data the data array
     */
    static <E extends Comparable<E>> MyMinHeap<E> initMinHeap(List<E> data) {
        MyMinHeap<E> heap = new MyMinHeap<>();
        heap.data = new ArrayList<>(data);
        return heap;
    }

    /**
     * Helper method to initialize all instance variables of MyPriorityQueue
     *
     * @param data the data array
     */
    static <E extends Comparable<E>> MyPriorityQueue<E> initPriorityQueue(
            List<E> data) {
        MyMinHeap<E> heap = new MyMinHeap<>();
        heap.data = new ArrayList<>(data);
        MyPriorityQueue<E> pq = new MyPriorityQueue<>();
        pq.heap = heap;
        return pq;
    }

    // ===================== MyMinHeap Public Tests =====================

    
}
