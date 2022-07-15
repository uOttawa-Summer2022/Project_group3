package com.example.bookingapp;

public enum Days {
    Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;

    public static Days stringToDays(String str) {
        switch (str) {
            case "SU":
                return Days.Sunday;
            case "MO":
                return Days.Monday;
            case "TU":
                return Days.Tuesday;
            case "WE":
                return Days.Wednesday;
            case "TH":
                return Days.Thursday;
            case "FR":
                return Days.Friday;
            default:
                return Days.Saturday;
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