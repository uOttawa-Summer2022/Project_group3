package com.example.bookingapp;

import java.util.Objects;

public class Session implements Comparable  {
    private int startM,startH,endM,endH;
    private Days day;

    public Session(String sessionString){
        String[] l1 = sessionString.split("/");
        String[] l2 = l1[1].split("~");
        String[] l3 = l2[0].split(":");
        String[] l4 = l2[1].split(":");


        this.startH = Integer.parseInt(l3[0].trim());
        this.startM = Integer.parseInt(l3[1].trim());
        this.endH = Integer.parseInt(l4[0].trim());
        this.endM = Integer.parseInt(l4[1].trim());
        this.day = Days.stringToDays(l1[0].trim());
    }

    public Session(int startH, int startM, int endH, int endM, Days day) {
        this.startH = startH;
        this.startM = startM;
        this.endH = endH;
        this.endM = endM;
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

    @Override
    public int compareTo(Object o) {
        Session other = (Session) o;
        int dayCompare = this.getDay().compareTo(other.getDay());
        if( dayCompare != 0){
            return dayCompare;
        }
        int hourCompare = this.getStartH()-other.startH;
        if( hourCompare != 0){
            return hourCompare;
        }
        int minCompare = this.getStartM()- other.startM;
        return minCompare;
    }

    public boolean isOverlap(Session other){
        if(this.getDay() != other.getDay()){
            return false;
        }
        int start1 = this.getStartH()*100+this.getStartM();
        int end1 = this.getEndH()*100+this.getEndM();

        int start2 = other.getStartH()*100+other.getStartM();
        int end2 = other.getEndH()*100+other.getEndM();

        return start1-end2 <= 0 && start2-end1 <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return startM == session.startM && startH == session.startH && endM == session.endM && endH == session.endH && day == session.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startM, startH, endM, endH, day);
    }


}

