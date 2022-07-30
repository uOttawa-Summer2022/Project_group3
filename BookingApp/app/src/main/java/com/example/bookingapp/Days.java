package com.example.bookingapp;

public enum Days {
    Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;

    public static Days stringToDays(String str) {
        switch (str) {
            case "Sunday":
                return Days.Sunday;
            case "Monday":
                return Days.Monday;
            case "Tuesday":
                return Days.Tuesday;
            case "Wednesday":
                return Days.Wednesday;
            case "Thursday":
                return Days.Thursday;
            case "Friday":
                return Days.Friday;
            case "Saturday":
                return Days.Saturday;
            default:
                return null;
        }
    }

    public static Days numToDays(int n){
        switch (n){
            case 5:
                return Days.Sunday;
            case 6:
                return Days.Monday;
            case 7:
                return Days.Tuesday;
            case 8:
                return Days.Wednesday;
            case 9:
                return Days.Thursday;
            case 10:
                return Days.Friday;
            default:
                return Days.Saturday;
        }
    }




}