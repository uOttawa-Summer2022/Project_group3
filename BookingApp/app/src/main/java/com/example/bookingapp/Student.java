package com.example.bookingapp;

public class Student extends Account{
    public Student(String studentName, String password) {
        super(studentName, password, AccountType.STUDENT);
    }
}
