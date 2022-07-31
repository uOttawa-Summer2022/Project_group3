package com.example.bookingapp;

import java.util.ArrayList;

public class Student extends Account{

    public Student(String studentName, String password) {
        super(studentName, password, AccountType.STUDENT);

    }
}
