package ru.ok.technopolis.students;

import android.widget.Button;
import android.widget.RadioGroup;

public interface IStudentUI {
    String getName();

    String getSurname();

    Student.Gender getGender();

    Integer getPhoto();

    void setToast(int resId);

    void setToast(String massage);

    void displayStudent(Student student);

    Button getBtn(StudentUI.BtnType btnType);

    void clearFields();

    RadioGroup getRadioGroup();

    void displayAvatar(int resId);
}