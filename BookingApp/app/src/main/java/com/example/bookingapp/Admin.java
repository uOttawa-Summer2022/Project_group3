package com.example.bookingapp;

public class Admin extends Account {
    public Admin(String adminName, String password) {
        super(adminName, password, AccountType.ADMIN);
    }
}
