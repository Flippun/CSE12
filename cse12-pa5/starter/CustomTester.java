/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA5 Write-up
 * 
 * This file is for CSE 12 PA5 in Spring 2023,
 * and contains checks and tests for the Sanctuary, Student, and course class.
 */

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * The Custom tester for PA5, which covers more complex tests.
 */
public class CustomTester {
    
    // ----------------Student class----------------
    
    /**
     * Call equals when the argument is a non-Student object.
     */
    @Test
    public void testStudentEquals() {
        int FakeStudent = 0;
        Course FakeStudent2 = new Course("", "", "", 1);
        Student Student = new Student("John", "Smith", "A12345677");
        assertFalse(Student.equals(FakeStudent));
        assertFalse(Student.equals(FakeStudent2));
        assertFalse(Student.equals(null));
    }

    /**
     * Call compareTo to compare two Students that have 
     * the same last name and same first name
     */
    @Test
    public void testStudentCompareTo() {
        Student Student1 = new Student("John", "Smith", "A12345677");
        Student Student2 = new Student("John", "Smith", "A12345678");

        assertEquals(Student1.compareTo(Student2), 
            "A12345677".compareTo("A12345678"));
        assertEquals(Student1.getFirstName(), Student2.getFirstName());
        assertEquals(Student1.getLastName(), Student2.getLastName());
        assertNotEquals(Student1, Student2);
    }

    // ----------------Course class----------------

    /**
     * Attempt to drop a non-existing student with a non-empty course roster.
     */
    @Test
    public void testCourseDrop() {
        Student student = new Student("Test", "Student", "A12345678");
        Student student2 = null;
        Course course = new Course("CSE", "12", "Data Structure", 100);
        Student stu = new Student("Whales", "Ocean", "A123");
        course.enroll(stu);

        assertFalse(course.drop(student));
        assertEquals(course.getEnrolledCount(), 1);
        assertThrows(IllegalArgumentException.class, () -> {
            course.drop(student2);
        });

    }

    /**
     * Attempt to enroll a valid student into a course that is 
     * already at maximum capacity.
     */
    @Test
    public void testCourseEnroll() {
        Course course = new Course("CSE", "12", "Data Structure", 1);
        Student stu = new Student("Whales", "Ocean", "A123");
        Student student = new Student("Test", "Student", "A12345678");
        Student student2 = null;
        course.enroll(stu);

        assertFalse(course.enroll(student));
        assertEquals(course.getEnrolledCount(), 1);
        assertEquals(course.getCapacity(), 1);
        assertThrows(IllegalArgumentException.class, () -> {
            course.enroll(student2);
        });
    }

    /**
     * Call getRoster on a course with a large number of enrolled students. 
     */
    @Test
    public void testCourseGetRoster() {
        ArrayList<Student> rosterTest = new ArrayList<>();
        Course course = new Course("CSE", "12", "Data Structure", 100);
        
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            String randString1 = Integer.toString(rand.nextInt(100));
            String randString2 = Integer.toString(rand.nextInt(100));
            String randString3 = Integer.toString(rand.nextInt(100));
            Student curStudent = new Student(randString1, randString2, 
                randString3);
            course.enroll(curStudent);
            rosterTest.add(curStudent);
        }
        Collections.sort(rosterTest);
        assertTrue(course.getRoster().equals(rosterTest));
    }

    // ----------------Sanctuary class----------------

    /**
     * Call the Sanctuary constructor with a negative argument for maxAnimals.
     */
    @Test
    public void testSanctConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sanctuary(-1, 50);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Sanctuary(50, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Sanctuary(3, 50);
        });
    }

    /**
     * rescue animals from an existing species, 
     * where rescuing num animals will cause the number of animals 
     * to exceed the sanctuary's max capacity.
     */
    @Test
    public void testSanctRescuePartial() {
        Sanctuary sanct = new Sanctuary(50, 4);
        sanct.rescue("Koala", 5);
        assertEquals(10, sanct.rescue("Koala", 55));
        assertEquals(50, sanct.getMaxAnimals());
        assertEquals(50, sanct.getTotalAnimals());
        assertEquals(4, sanct.getMaxSpecies());
        assertEquals(1, sanct.getTotalSpecies());
    }

    /**
     * rescue a new non-null species when the sanctuary is 
     * already at the max capacity for species.
     */
    @Test
    public void testSanctRescueMaxSpecies() {
        Sanctuary sanct = new Sanctuary(50, 1);
        sanct.rescue("Fish", 50);
        assertEquals(20, sanct.rescue("Dog", 20));
        assertEquals(50, sanct.getMaxAnimals());
        assertEquals(50, sanct.getTotalAnimals());
        assertEquals(1, sanct.getMaxSpecies());
        assertEquals(1, sanct.getTotalSpecies());
    }

    /**
     * release some (not all) of the animals of an existing 
     * species in the sanctuary.
     */
    @Test
    public void testSanctReleasePartial() {
        Sanctuary sanct = new Sanctuary(50, 1);
        sanct.rescue("Fish", 50);
        sanct.release("Fish", 20);
        
        assertEquals(30, sanct.getTotalAnimals());
        assertEquals(50, sanct.getMaxAnimals());
        assertEquals(1, sanct.getMaxSpecies());
        assertEquals(1, sanct.getTotalSpecies());

        Sanctuary sanct2 = new Sanctuary(50, 4);
        sanct2.rescue("Fish", 30);
        sanct2.rescue("Dog", 10);
        sanct2.release("Fish", 20);

        assertEquals(20, sanct2.getTotalAnimals());
    }

    /**
     * Attempt to release more animals than exists for a specific 
     * animal species in the sanctuary. 
     */
    @Test
    public void testSanctReleaseTooMany() {
        Sanctuary sanct = new Sanctuary(50, 1);
        sanct.rescue("Fish", 50);
        
        assertThrows(IllegalArgumentException.class, () -> {
            sanct.release("Fish", 60);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sanct.release("Fish", 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sanct.release(null, 20);
        });
    }
}
