package com.iamrajendra.analogclock.model;

public class ColorModel {
    private int id;
    private int primary;
    private int secondary;
    private int heigher;
    private int selected;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public ColorModel(int id, int primary, int secondary, int heigher) {
        this.id = id;
        this.primary = primary;
        this.secondary = secondary;
        this.heigher = heigher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public void setSecondary(int secondary) {
        this.secondary = secondary;
    }

    public int getHeigher() {
        return heigher;
    }

    public void setHeigher(int heigher) {
        this.heigher = heigher;
    }
}
