package ru.ok.technopolis.students;

import android.graphics.Bitmap;

public class Student {

    private String firstName;
    private String secondName;
    private boolean maleGender;
    private Bitmap bitmap;

    public Student(String firstName, String secondName, boolean maleGender) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
