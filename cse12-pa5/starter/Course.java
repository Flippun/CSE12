/*
 * Name Flip Frauenzimmer
 * Email: ffrauenzimmer@ucsd.edu
 * PID: A17685777
 * Sources Used: JDK 17 Documentation, PA5 Write-up
 * 
 * This file is for CSE 12 PA5 in Spring 2023,
 * and contains the implementation of the Course class.
 */

import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Class that will store the students registered for this particular course 
 * in the form of a HashSet.
 * 
 * Instance Variables:
 * enrolled - A collection of students that are enrolled in this course.
 * capacity - The max number of students that can be enrolled in this course.
 * department - This course falls under this department.
 * number - A string representing the course number.
 * description - A string representing the description of the course.
 */
public class Course {
    
    private static final String FORMAT = "%1$s %2$s [%3$s] %4$s";
    
    HashSet<Student> enrolled;
    private final int capacity;
    private final String department;
    private final String number;
    private final String description;

    /**
     * Constructor that initializes the course’s information with 
     * an initial enrollment of 0 students.
     * 
     * @param department This course falls under this department.
     * @param number A string representing the course number.
     * @param description A string representing the description of the course.
     * @param capacity The max number of students that can be 
     *                 enrolled in this course.
     */
    public Course(String department, String number, String description, 
            int capacity) {
        if (department == null || number == null || description == null
                || capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.department = department;
        this.number = number;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = new HashSet<Student>();
    }

    /**
     * Gets the department name of the course.
     * 
     * @return the department name.
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Gets the number of the course.
     * 
     * @return the course number.
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Gets the description of the course.
     * 
     * @return the description of the course. 	
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the capacity of the course.
     * 
     * @return the capacity of the course. 	
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * If there is room in this course and the student is not currently 
     * enrolled, add the student to the course.
     * 
     * @param student Student to be added.
     * @return true for successful enrollment, false otherwise.
     */
    public boolean enroll(Student student) {
        if (student == null) {
            throw new IllegalArgumentException();
        }
        if (isFull() || enrolled.contains(student)) {
            return false;
        }
        enrolled.add(student);
        return true;
    }

    /**
     * If the student is enrolled in the course, drop them from the course. 
     * 
     * @param student student to be dropped
     * @return true for successfully dropping student, false otherwise.
     */
    public boolean drop(Student student) {
        if (student == null) {
            throw new IllegalArgumentException();
        }
        if (!enrolled.contains(student)) {
            return false;
        } 
        enrolled.remove(student);
        return true;
    }

    /**
     * If the course is canceled, all of the students are dropped 
     * from the course. Remove all the students from the course.
     */
    public void cancel() {
        enrolled.clear();
    }

    /**
     * Determines if enrolled is full.
     * 
     * @return Return true if at capacity, false otherwise.
     */
    public boolean isFull() {
        return enrolled.size() == capacity;
    }

    /**
     * Get the current number of enrolled students.
     * 
     * @return the current number of enrolled students
     */
    public int getEnrolledCount() {
        return enrolled.size();
    }

    /**
     * Get the number of students that can still enroll in the course.
     * 
     * @return the number of students that can still enroll in the course
     */
    public int getAvailableSeats() {
        return capacity - enrolled.size();
    }

    /**
     * Creates a shallow copy of all the students enrolled in this course.
     * 
     * @return a shallow copy of all the students enrolled in this course.
     */
    public HashSet<Student> getStudents() {
        return (HashSet<Student>) enrolled.clone();
    }

    /**
     * Turn the collection of enrolled students into an 
     * ArrayList collection. Students in the course should be listed 
     * in the increasing order specified by the compareTo.
     * 
     * @return the final result as a sorted ArrayList
     */
    public ArrayList<Student> getRoster() {
        ArrayList<Student> newArrayList = new ArrayList<Student>();
        for (Student student: enrolled) {
            newArrayList.add(student);
        }
        Collections.sort(newArrayList);
        return newArrayList;
    }

    /**
     * Creates a string representation for a Course object.
     * 
     * @return a string representation for a Course object
     */
    @Override
    public String toString() {
        return String.format(FORMAT, this.department, this.number, 
            this.capacity, this.description);
    }
}