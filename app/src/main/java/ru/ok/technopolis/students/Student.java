package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private boolean isMale;
    private int photo;

    public Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.isMale = maleGender;
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

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void copy(Student student) {
        this.firstName = student.firstName;
        this.secondName = student.secondName;
        this.isMale = student.isMale;
        this.photo = student.photo;
    }
}
