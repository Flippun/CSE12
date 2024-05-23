/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA8 Write-up
 * 
 * This file is for CSE 12 PA8 in Spring 2023,
 * and contains the implementation of custom tests for the MyBST class.
 */

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Tester class to test the BST implementation of the MyBST.java class
 */
public class CustomTester {
    MyBST<Integer, Integer> tree1;
    MyBST<Integer, Integer> tree2;
    MyBST<Integer, Integer> tree3;

    /** Create example BST for testing */
    @Before
    public void setup() {
        MyBST.MyBSTNode<Integer, Integer> root =
                new MyBST.MyBSTNode<>(4, 1, null);
        MyBST.MyBSTNode<Integer, Integer> two =
                new MyBST.MyBSTNode<>(2, 1, root);
        MyBST.MyBSTNode<Integer, Integer> six =
                new MyBST.MyBSTNode<>(6, 1, root);
        MyBST.MyBSTNode<Integer, Integer> one =
                new MyBST.MyBSTNode<>(1, 2, two);
        MyBST.MyBSTNode<Integer, Integer> three =
                new MyBST.MyBSTNode<>(3, 30, two);
        MyBST.MyBSTNode<Integer, Integer> five =
                new MyBST.MyBSTNode<>(5, 50, six);

        tree1 = new MyBST<>();
        tree1.root = root;
        root.setLeft(two);
        root.setRight(six);
        two.setLeft(one);
        two.setRight(three);
        six.setLeft(five);
        tree1.size = 6;

        MyBST.MyBSTNode<Integer, Integer> root2 =
                new MyBST.MyBSTNode<>(4, 1, null);

        tree2 = new MyBST<>();
        tree2.root = root2;
        tree2.size = 1;

        tree3 = new MyBST<>();
    }

    // ===================== testSuccessor =======================

    /** Test the successor method */
    @Test
    public void testSuccessor() {
        MyBST.MyBSTNode<Integer, Integer> treeRoot1 = tree1.root;
        MyBST.MyBSTNode<Integer, Integer> treeRoot2 = tree2.root;

        assertSame(treeRoot1.getRight().getLeft(), treeRoot1.successor());
        assertNull(treeRoot2.successor());
        assertNull(treeRoot1.getRight().successor());
        assertSame(treeRoot1, treeRoot1.getLeft().getRight().successor());
        assertSame(treeRoot1.getLeft(), 
            treeRoot1.getLeft().getLeft().successor());
        
        assertSame(6, tree1.size);
        assertSame(tree1.root, treeRoot1);
        assertSame(1, treeRoot1.getValue());
        assertSame(1, treeRoot1.getRight().getValue());
        assertSame(2, treeRoot1.getLeft().getLeft().getValue());
        assertSame(treeRoot1, treeRoot1.getLeft().getParent());
        assertSame(6, treeRoot1.getRight().getKey());

        assertSame(1, tree2.size);
        assertSame(4, treeRoot2.getKey());
    }  

    // ======================= testInsert ==========================

    /** Test the insert method */
    @Test
    public void testInsert1() {
        MyBST.MyBSTNode<Integer, Integer> root1 = tree1.root;

        Integer returnValue = tree1.insert(10, 1);

        assertSame(10, root1.getRight().getRight().getKey());
        assertSame(root1.getRight(), root1.getRight().getRight().getParent());
        assertNull(returnValue);

        assertEquals(7, tree1.size);
        assertSame(root1.getLeft(), root1.getLeft().getRight().getParent());
        assertSame(1, root1.getLeft().getRight().getParent().getValue());

        assertThrows(NullPointerException.class, () -> {
            tree1.insert(null, 1);
        });
        assertThrows(NullPointerException.class, () -> {
            tree1.insert(null, null);
        });
    }

    /** Test the insert method */
    @Test
    public void testInsert2() {
        tree3.insert(4, 1);
        
        assertEquals(1, tree3.size);
        assertSame(4, tree3.root.getKey());
        assertSame(1, tree3.root.getValue());
        assertNull(tree3.root.getParent());
        assertNull(tree3.root.getLeft());
        assertNull(tree3.root.getRight());
    }

    /** Test the insert method */
    @Test
    public void testInsert3() {
        MyBST.MyBSTNode<Integer, Integer> root1 = tree1.root;

        tree1.insert(10, 1);
        Integer oldValue = tree1.insert(10, 5);

        assertSame(1, oldValue);
        assertSame(10, root1.getRight().getRight().getKey());

        assertSame(root1.getRight(), root1.getRight().getRight().getParent());
        assertEquals(7, tree1.size);
        assertSame(root1.getLeft(), root1.getLeft().getRight().getParent());
        assertSame(1, root1.getLeft().getRight().getParent().getValue());
    }

    /** Test the insert method */
    @Test
    public void testInsert4() {
        MyBST.MyBSTNode<Integer, Integer> root1 = tree1.root;

        Integer oldValue = tree1.insert(0, 0);

        assertSame(null, oldValue);
        assertSame(0, root1.getLeft().getLeft().getLeft().getKey());
        assertSame(0, root1.getLeft().getLeft().getLeft().getValue());
        assertSame(root1.getLeft().getLeft(), 
            root1.getLeft().getLeft().getLeft().getParent());

        assertEquals(7, tree1.size);
        assertSame(root1.getLeft(), root1.getLeft().getRight().getParent());
        assertSame(1, root1.getLeft().getRight().getParent().getValue());
        assertSame(50, root1.getRight().getLeft().getValue());
    }

    /** Test the insert method */
    @Test
    public void testInsert5() {
        MyBST.MyBSTNode<Integer, Integer> root2 = tree2.root;

        tree2.insert(10, 100);
        tree2.insert(15, 8);
        tree2.insert(9, 87);
        tree2.insert(5, 5);
        tree2.insert(3, 50);
        tree2.insert(2, 2);

        assertSame(7, tree2.size());
        assertSame(root2, tree2.root);
        assertSame(1, tree2.root.getValue());
        assertSame(root2.getRight(), tree2.root.getRight());
        assertSame(5, root2.getRight().getLeft().getLeft().getValue());
        assertSame(null, root2.getLeft().getRight());
        assertSame(50, root2.getLeft().getValue());
        assertTrue(root2.getKey() > root2.getLeft().getKey()
            && root2.getKey() > root2.getLeft().getLeft().getKey());
        assertTrue(root2.getKey() < root2.getRight().getKey()
            && root2.getKey() < root2.getRight().getRight().getKey()
            && root2.getKey() < root2.getRight().getLeft().getKey()
            && root2.getKey() < root2.getRight().getLeft().getLeft().getKey());
    }


    // ======================= testSearch ==========================

    /** Test the search method */
    @Test
    public void testSearch() {
        MyBST.MyBSTNode<Integer, Integer> treeRoot1 = tree1.root;
        MyBST.MyBSTNode<Integer, Integer> treeRoot2 = tree2.root;
        
        assertEquals(30, tree1.search(3).intValue());
        assertEquals(50, tree1.search(5).intValue());
        assertEquals(2, tree1.search(1).intValue());
        assertEquals(1, tree1.search(4).intValue());
        assertNull(tree1.search(10));

        assertEquals(1, tree2.search(4).intValue());
        assertNull(tree2.search(15));
        assertNull(tree2.search(null));

        assertSame(6, tree1.size);
        assertSame(tree1.root, treeRoot1);
        assertSame(1, treeRoot1.getValue());
        assertSame(1, treeRoot1.getRight().getValue());
        assertSame(2, treeRoot1.getLeft().getLeft().getValue());
        assertSame(treeRoot1, treeRoot1.getLeft().getParent());
        assertSame(6, treeRoot1.getRight().getKey());

        assertSame(1, tree2.size);
        assertSame(4, treeRoot2.getKey());
    }


    // ======================= testRemove ==========================

    /** Test the remove method */
    @Test
    public void testRemove1() {
        MyBST.MyBSTNode<Integer, Integer> root1 = tree1.root;

        assertEquals(30, tree1.remove(3).intValue());
        assertNull(root1.getLeft().getRight());

        assertEquals(1, tree1.remove(6).intValue());
        assertEquals(5, root1.getRight().getKey().intValue());

        assertEquals(2, tree1.remove(1).intValue());
        assertNull(root1.getLeft().getLeft());
        
        assertSame(root1.getRight().getParent(), root1);
        assertSame(root1.getLeft().getParent(), root1);
        assertNull(root1.getRight().getRight());
        assertEquals("size of tree", 3, tree1.size);

        assertNull(tree1.remove(10));
        assertNull(tree1.remove(null));
        assertEquals("size of tree", 3, tree1.size);

        assertEquals(1, tree1.remove(4).intValue());
        assertSame(5, tree1.root.getKey());
        assertSame(2, tree1.root.getLeft().getKey());
        assertSame(tree1.root, tree1.root.getLeft().getParent());
        assertNull(root1.getParent());
        assertEquals("size of tree", 2, tree1.size);

        assertEquals(50, tree1.remove(5).intValue());
        assertSame(2, tree1.root.getKey());
        assertNull(root1.getLeft());
        assertNull(tree1.root.getLeft());
        assertNull(tree1.root.getRight());
        assertNull(root1.getParent());
        assertEquals("size of tree", 1, tree1.size);
    }

    /** Test the remove method */
    @Test
    public void testRemove2() {
        MyBST.MyBSTNode<Integer, Integer> root2 = tree2.root;
        
        assertEquals(1, tree2.remove(4).intValue());
        assertNull(tree2.root);
        assertNull(root2.getParent());
        assertNull(root2.getLeft());
        assertNull(root2.getRight());
        assertEquals(0, tree2.size);
    }


    // ======================= testInorder ==========================

    /** Test the inorder method */
    @Test
    public void testInorder1() {
        MyBST.MyBSTNode<Integer, Integer> root1 = tree1.root;
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedRes =
                new ArrayList<>();
        expectedRes.add(root1.getLeft().getLeft());
        expectedRes.add(root1.getLeft());
        expectedRes.add(root1.getLeft().getRight());
        expectedRes.add(root1);
        expectedRes.add(root1.getRight().getLeft());
        expectedRes.add(root1.getRight());
        assertEquals(expectedRes, tree1.inorder());
    }

    /** Test the inorder method */
    @Test
    public void testInorder2() {
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedRes =
                new ArrayList<>();

        MyBST.MyBSTNode<Integer, Integer> root2 = tree2.root;
        tree2.insert(10, 100);
        tree2.insert(15, 8);
        tree2.insert(9, 87);
        tree2.insert(5, 5);
        tree2.insert(3, 50);
        tree2.insert(2, 2);

        expectedRes.add(root2.getLeft().getLeft());
        expectedRes.add(root2.getLeft());
        expectedRes.add(root2);
        expectedRes.add(root2.getRight().getLeft().getLeft());
        expectedRes.add(root2.getRight().getLeft());
        expectedRes.add(root2.getRight());
        expectedRes.add(root2.getRight().getRight());
      
        assertEquals(tree2.size(), 7);
        assertEquals(expectedRes, tree2.inorder());
    }

    /** Test the inorder method */
    @Test
    public void testInorder3() {
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expectedRes =
                new ArrayList<>();

        assertEquals(tree3.size(), 0);
        assertEquals(expectedRes, tree3.inorder());
    }
}