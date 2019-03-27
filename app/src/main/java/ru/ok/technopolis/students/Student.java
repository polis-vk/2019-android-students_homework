package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private boolean maleGender;
    private int photo;

    Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getSecondName() {
        return secondName;
    }

    void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    boolean isMaleGender() {
        return maleGender;
    }

    void setMaleGender(boolean maleGender) {
        this.maleGender = maleGender;
    }

    int getPhoto() {
        return photo;
    }

    void setPhoto(int photo) {
        this.photo = photo;
    }

}
