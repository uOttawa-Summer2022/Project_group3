package com.example.bookingapp;

public class Evaluator {
    public static boolean isValidCrsName(String name){
        return name.matches("[A-Za-z]+");
    }
    public static boolean isValidCrsCode(String name){
        return name.matches("[A-Z]{3}[0-9]{4}");
    }
    public static boolean isValidSession (int sM,int eM,int sH,int eH){
        if(sM < 0 || sM > 59){return false;}

        if(eM< 0 || eM > 59){return false;}

        if(sH< 0 || eH > 23){return false;}

        return eH >= 0 && eH <= 23;
    }
    public static boolean isValidFirstName(String name){
        return name.matches("[A-Za-z]+");
    }
    public static boolean isValidLastName(String name){
        return name.matches("[A-Za-z]+");
    }
    public static boolean isValidUserName(String name){
        return name.matches("[A-Za-z0-9]+");

    }


}
