package com.example.bookingapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Course {
    private String code;
    private String name;
    private int[] sessionIndex;
    private ArrayList<Session> sessionList;
    private ArrayList<String> studentNameList;
    private int capacity;
    private String description;
    private String instructor;

    public ArrayList<String> getStudentNameList() {
        return studentNameList;
    }

    public void setStudentNameList(ArrayList<String> studentNameList) {
        this.studentNameList = studentNameList;
    }

    public int[] getSessionIndex() {
        return sessionIndex;
    }

    public void setSessionIndex(int[] sessionIndex) {
        this.sessionIndex = sessionIndex;
    }

    public void setSessionList(ArrayList<Session> sessionList) {
        this.sessionList = sessionList;
    }



    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Course() {
    }

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        this.capacity=0;
        this.description="";
        this.sessionList = new ArrayList<>();
        this.studentNameList = new ArrayList<>();
        this.instructor = "";
        this.sessionIndex = new int[8];
    }

    public ArrayList<Session> getSessionList() {
        return sessionList;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sessionList=" + sessionList +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return capacity == course.capacity && Objects.equals(code, course.code) && Objects.equals(name, course.name) && Arrays.equals(sessionIndex, course.sessionIndex) && Objects.equals(sessionList, course.sessionList) && Objects.equals(studentNameList, course.studentNameList) && Objects.equals(description, course.description) && Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(code, name, sessionList, studentNameList, capacity, description, instructor);
        result = 31 * result + Arrays.hashCode(sessionIndex);
        return result;
    }
}
