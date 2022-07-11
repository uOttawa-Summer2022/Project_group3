package com.example.bookingapp;

import java.util.ArrayList;

public class Course {
    private String code;
    private String name;
    private ArrayList<Session> sessionList;
    private int capacity;
    private String description;



    public Course() {
    }

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        this.capacity=0;
        this.description="";
        this.sessionList = new ArrayList<Session>();
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
}
