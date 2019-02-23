package ru.ok.technopolis.students;

public class Student {

    public final String firstName;
    public final String secondName;
    public final boolean maleGender;
    public final int photo;

    public Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }
}
