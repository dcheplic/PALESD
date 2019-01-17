package com.palesd.models;

public class Guest {
    private String name;
    private String number;

    public Guest() {
        name = "";
        number = "";
    }

    public Guest(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}