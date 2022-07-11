package com.example.bookingapp;

public class Instructor extends Account{
    public Instructor(String instructorName, String password) {
        super(instructorName, password, AccountType.INSTRUCTOR);
    }
}
