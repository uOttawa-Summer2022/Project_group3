package com.example.bookingapp;

public class Account {
    private String user_name;
    private String password;
    private AccountType type;
//constructor
    public Account() {

    }

    public Account(String user_name, String password, AccountType type) {
        this.user_name = user_name;
        this.password = password;
        this.type = type;
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

    @Override
    public String toString() {
        return "Account{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
