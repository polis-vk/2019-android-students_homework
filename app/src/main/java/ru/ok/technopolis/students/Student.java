package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private String gender;
    private int photo;

    public Student(String firstName, String secondName, String gender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
