package com.example.bookingapp;

public class Session {
    private int startM,startH,endM,endH;
    private Days day;

    public Session(String sessionString){
        String days = sessionString.split("/ ")[0];

    }

    public Session(int startH, int startM, int endH, int endM, Days day) {
        this.startM = startM;
        this.startH = startH;
        this.endM = endM;
        this.endH = endH;
        this.day = day;
    }

    public int getStartM() {
        return startM;
    }

    public void setStartM(int startM) {
        this.startM = startM;
    }

    public int getStartH() {
        return startH;
    }

    public void setStartH(int startH) {
        this.startH = startH;
    }

    public int getEndM() {
        return endM;
    }

    public void setEndM(int endM) {
        this.endM = endM;
    }

    public int getEndH() {
        return endH;
    }

    public void setEndH(int endH) {
        this.endH = endH;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public String intTimeToSting(int n){
        if(n == 0)
            return "00";
        return Integer.toString(n);
    }

    @Override
    public String toString() {
        return day + "/ " +
                intTimeToSting(startH)+ ":" +
                intTimeToSting(startM)+" ~ "+
                intTimeToSting(endH) + ":" +
                intTimeToSting(endM);
    }
}
