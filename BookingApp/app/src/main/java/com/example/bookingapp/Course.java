package com.example.bookingapp;

import android.database.Cursor;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private int[] SessionDayIndex;
    private ArrayList<ArrayList<Session>> sessionList;
    private int capacity;
    private String description;
    private String instructor;

    public int[] getSessionDayIndex() {
        return SessionDayIndex;
    }

    public void setSessionDayIndex(int[] sessionDayIndex) {
        SessionDayIndex = sessionDayIndex;
    }

    public void setSessionList(ArrayList<ArrayList<Session>> sessionList) {
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
        this.instructor = "";
    }

    public ArrayList<ArrayList<Session>> getSessionList() {
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
}
