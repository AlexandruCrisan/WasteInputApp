package com.example.wasteinput;

public class GarbageData {
    private double lat;
    private double log;
    private double pollution_rate;
    private int index;


    public GarbageData() {

    }

    public GarbageData(double lat, double log, int index, double pollution_rate) {
        this.lat = lat;
        this.log = log;
        this.index = index;
        this.pollution_rate = pollution_rate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getPollution_rate() {
        return pollution_rate;
    }

    public void setPollution_rate(double pollution_rate) {
        this.pollution_rate = pollution_rate;
    }
}
