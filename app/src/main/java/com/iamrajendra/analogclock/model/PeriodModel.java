package com.iamrajendra.analogclock.model;

public class PeriodModel {
    private  int id;
    private String shortTime;
    private String longTime;
    private String title;
    private String des;

    private int color;

    public PeriodModel(int id, String shortTime, String longTime, String title, int color) {
        this.id = id;
        this.shortTime = shortTime;
        this.longTime = longTime;
        this.title = title;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortTime() {
        return shortTime;
    }

    public void setShortTime(String shortTime) {
        this.shortTime = shortTime;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
