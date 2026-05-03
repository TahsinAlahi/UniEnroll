
package unienroll.ui.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Course {
    
    private String code;
    private String title;
    private double credits;
    private double fee;
    
    private BooleanProperty selected = new SimpleBooleanProperty(false); //*

    public Course(String code, String title, double credits, double fee, boolean select) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.fee = fee;
        this.selected.set(select);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
    
    public double getCredits(){
        return credits;
    }

    public double getFee() {
        return fee;
    }
    
    public BooleanProperty selectProperty() {
        return selected;
    } //*

    public boolean isSelected() {
        return selected.get();
    } //*

    public void setSelected(boolean value) {
        selected.set(value);
    } //*
}

