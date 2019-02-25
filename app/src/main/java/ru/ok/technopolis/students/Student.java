package ru.ok.technopolis.students;

public class Student {

    private int _id;
    private String firstName;
    private String secondName;
    private boolean maleGender;
    private int photo;

    public Student(int _id, String firstName, String secondName, boolean maleGender, int photo) {
        this._id = _id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    public Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    public Student(){
        firstName = "";
        secondName = "";
        maleGender = false;
        photo = 0;
    }

    int getID(){
        return _id;
    }

    void setId(int _id){
        this._id = _id;
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
