package com.palesd.models;

public class Guest {
    private String firstName;
    private String lastName;
    private int number;
    private String time;

    public Guest() {
        firstName = "";
        lastName = "";
        number = 0;
        time = "";
    }

    public Guest(String firstName, String lastName, int number, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTime() {
        return time;
    }

    public void setNumber(String number) {
        this.number = Integer.parseInt(number);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTime(String time) {
        this.time = time;
    }
}