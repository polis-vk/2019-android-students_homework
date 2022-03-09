package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private boolean isMale;
    private int photo;

    public Student(String firstName, String secondName, boolean isMale, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.isMale = isMale;
        this.photo = photo;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getPhoto() {
        return photo;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }
}
