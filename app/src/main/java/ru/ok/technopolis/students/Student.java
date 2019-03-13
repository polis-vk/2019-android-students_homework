package ru.ok.technopolis.students;

class Student {

    private int _id;
    private String firstName;
    private String secondName;
    private boolean maleGender;
    private int photo;


    Student(int _id, String firstName, String secondName, boolean maleGender, int photo) {
        this._id = _id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    Student(String firstName, String secondName, boolean maleGender, int photo) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.maleGender = maleGender;
        this.photo = photo;
    }

    Student() {
        firstName = "";
        secondName = "";
        maleGender = false;
        photo = 0;
    }

    int getID() {
        return _id;
    }

    void setId(int _id) {
        this._id = _id;
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
