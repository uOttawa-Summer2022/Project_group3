package com.example.bookingapp;

import java.util.ArrayList;

public class Account {
    private String user_name;
    private String password;
    private AccountType type;
    private ArrayList<Course> coursesList;
//constructor
    public Account() {

    }

    public Account(String user_name, String password, AccountType type) {
        this.user_name = user_name;
        this.password = password;
        this.type = type;
        coursesList = type == AccountType.STUDENT?new ArrayList<>():null;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public ArrayList<Course> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(ArrayList<Course> coursesList) {
        this.coursesList = coursesList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
