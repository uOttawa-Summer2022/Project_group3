package com.example.bookingapp;

public class Session {
    private int startM,startH,endM,endH;
    private Days day;



    public Session(int startM, int startH, int endM, int endH, Days day) {
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
}
