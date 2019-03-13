package ru.ok.technopolis.students;

import java.util.ArrayList;
import java.util.List;

public class StudentList {
    private List<Student> studentList;

    public static int getPhoto(int i, boolean maleGender) {
        switch (i) {
            case 0: return maleGender ? R.drawable.male_1 : R.drawable.female_1;
            case 1: return maleGender ? R.drawable.male_2 : R.drawable.female_2;
            case 2: return maleGender ? R.drawable.male_3 : R.drawable.female_3;
            default: return R.drawable.female_1;
        }
    }

    public StudentList() {
        studentList = new ArrayList<>();
    }

    public void add(String firstName, String secondName, boolean maleGender, int photo) {
        studentList.add(new Student(firstName, secondName, maleGender, photo));
    }

    public void delete(Student student) {
        studentList.remove(student);
    }

    public void change(Student oldStudent, Student newStudent) {
        studentList.add(studentList.indexOf(oldStudent), newStudent);
    }

    public List<Student> getStudentList() {
        return studentList;
    }
}
