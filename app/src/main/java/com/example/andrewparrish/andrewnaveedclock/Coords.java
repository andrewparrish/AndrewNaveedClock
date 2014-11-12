package com.example.andrewparrish.andrewnaveedclock;

/**
 * Created by Tuxedo on 11/12/14.
 */

public class Coords {

    private String ptX;
    private String ptY;

    public Coords(String x, String y){
        this.ptX = x;
        this.ptY = y;
    }


    public String getPtX() {
        return ptX;
    }

    public void setPtX(String ptX) {
        this.ptX = ptX;
    }

    public String getPtY() {
        return ptY;
    }

    public void setPtY(String ptY) {
        this.ptY = ptY;
    }

    @Override
    public String toString() {
        return ptX + ',' + ptY;
    }
}
