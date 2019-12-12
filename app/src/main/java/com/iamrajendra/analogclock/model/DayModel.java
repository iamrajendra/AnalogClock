package com.iamrajendra.analogclock.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class DayModel implements Serializable {
    private  int id;
    private String day;
    private  int dayId;

    public DayModel(int id, String day, int dayId) {
        this.id = id;
        this.day = day;
        this.dayId = dayId;
    }

    public DayModel(int dayId) {
        this.dayId = dayId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public DayModel(int id, String day) {
        this.id = id;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

   /* @Override
    public boolean equals(@Nullable Object obj) {
        DayModel dayModel = (DayModel) obj;

        return dayModel.dayId ==dayId;
    }

    @Override
    public int hashCode() {
        return dayId;
    }*/
}
