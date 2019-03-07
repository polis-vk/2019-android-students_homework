package ru.ok.technopolis.students;

import android.view.View;
import android.widget.RadioGroup;

public class StudentManager {

    private final IStudentUI studentUI;
    private Student currentStudent = null;
    private String name;
    private String surname;
    private int photo;
    private Student.Gender gender;

    public StudentManager(IStudentUI studentUI) {
        this.studentUI = studentUI;
    }

    public void setCurrentStudent(Student student) {
        currentStudent = student;
    }

    public Student createNewStudent() {
        name = studentUI.getName();
        if (name == null) {
            studentUI.setToast(R.string.error_Of_Add_Set_Name);
            return null;
        }
        surname = studentUI.getSurname();
        if (surname == null) {
            studentUI.setToast(R.string.error_Of_Add_Set_Surname);
            return null;
        }
        gender = studentUI.getGender();
        if (gender == null) {
            studentUI.setToast(R.string.error_Of_Add_Set_Gender);
            return null;
        }
        return new Student(name, surname, gender, photo);
    }

    public void setListenerForAddBtn(onClickStudentListener listener) {
        studentUI.getBtn(StudentUI.BtnType.ADD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createNewStudent();
                if (student == null) {
                    studentUI.setToast(R.string.error_Of_Add);
                    return;
                } else {
                    listener.onClick(student);
                    studentUI.displayStudent(student);
                    studentUI.setToast(R.string.was_Added);
                    studentUI.clearFields();
                }
            }
        });
    }

    public void setListenerForSaveBtn(onClickStudentListener listener) {
        studentUI.getBtn(StudentUI.BtnType.SAVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent == null) {
                    studentUI.setToast(R.string.error_Of_Save_Not_Selected);
                    return;
                }
                name = studentUI.getName();
                surname = studentUI.getSurname();
                gender = studentUI.getGender();
                //photo = studentUI.getPhoto();
                if (name == null || surname == null || gender == null) {
                    studentUI.setToast(R.string.error_Of_Save);
                } else {
                    currentStudent.setFirstName(name);
                    currentStudent.setSecondName(surname);
                    currentStudent.setGender(gender);
                    currentStudent.setPhoto(photo);
                    listener.onClick(currentStudent);
                    studentUI.setToast(R.string.was_Saved);
                }
            }
        });

    }

    public void setListenerForDeleteBtn(onClickStudentListener listener) {
        studentUI.getBtn(StudentUI.BtnType.DELETE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent == null) {
                    studentUI.setToast(R.string.error_Of_Delete_Not_Selected);
                } else {
                    listener.onClick(currentStudent);
                    studentUI.clearFields();
                    studentUI.setToast(R.string.was_Deleted);
                }
            }
        });

    }

    public void setListenerForRadioBtn(onClickStudentListener listenerForRadioBtn) {
        studentUI.getRadioGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (studentUI.getPhoto() == null) return;
                photo = studentUI.getPhoto();
                studentUI.displayAvatar(photo);
            }
        });
    }

}
