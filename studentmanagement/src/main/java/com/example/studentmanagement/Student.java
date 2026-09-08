package com.example.studentmanagement;

public class Student {

    private int roll;
    private String name;
    private String branch;

    public Student() {
    }

    public Student(int roll, String name, String branch) {
        this.roll = roll;
        this.name = name;
        this.branch = branch;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}