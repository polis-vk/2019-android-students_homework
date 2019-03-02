package ru.ok.technopolis.students;

public class Student {

    public String firstName;
    public String secondName;
    public boolean isMale;
    public int photo;

    public Student(String firstName, String secondName, boolean isMale, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.isMale = isMale;
        this.photo = photo;
    }
}
