package ru.ok.technopolis.students;

public class Student {

    private String firstName;
    private String secondName;
    private boolean male;
    private int photo;

    public Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.male = maleGender;
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
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void copy(Student student) {
        this.firstName = new String(student.firstName);
        this.secondName = new String(student.secondName);
        this.male = new Boolean(student.male);
        this.photo = new Integer(student.photo);
    }

}
