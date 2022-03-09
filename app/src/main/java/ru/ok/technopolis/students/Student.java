package ru.ok.technopolis.students;

import java.util.List;

public class Student {

    private String firstName;
    private String secondName;
    private boolean maleGender;
    private int photo;

    public Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public boolean isMaleGender() {
        return maleGender;
    }

    public void setMaleGender(boolean maleGender) {
        this.maleGender = maleGender;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
