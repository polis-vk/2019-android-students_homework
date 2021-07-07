package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private Gender gender;
    private int photo;

    public Student(String firstName, String secondName, Gender gender, int photo) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
