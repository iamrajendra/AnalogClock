package com.iamrajendra.analogclock.model;

import androidx.lifecycle.MutableLiveData;

import com.iamrajendra.analogclock.R;
import com.iamrajendra.analogclock.utlis.Date;

public class PeriodTableV2 implements Comparable<PeriodTableV2> {
    MutableLiveData<String> liveData = new MutableLiveData<>();
    private int id;
    private String uid;
    private String title;
    private String description;
    private boolean visible;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getUid() {
        return uid;
    }

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    private long startDate;
    private long endDate;
    private int color=R.color.md_deep_orange_600;

    public int getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isValid(){
        if (title==null ||title.isEmpty() ){
            liveData.setValue("please enter title");
            return false;
        }
        if (description==null ||description.isEmpty() ){
            liveData.setValue("Write description");
            return false;
        }


        if (startDate==0){
            liveData.setValue("please select start date");
            return false;
        }
        if (endDate==0){
            liveData.setValue("please select end date");
            return false;
        }
        if (!Date.compare(startDate,endDate)){
            liveData.setValue("Start date smaller then end date or there should be difference of 30 min at least");
            return false;
        }
        if (color==-1){
            return false;
        }

        return true;

    }

    public void setUid(String id) {
        this.uid=id;
    }

    @Override
    public int compareTo(PeriodTableV2 o) {

        if (endDate > o.getEndDate()) {
            return 1;
        }
        else if (endDate<  o.getEndDate()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
