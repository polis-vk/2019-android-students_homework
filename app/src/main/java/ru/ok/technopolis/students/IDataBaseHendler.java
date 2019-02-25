package ru.ok.technopolis.students;

import java.util.List;

public interface IDataBaseHendler {
    void addStudent(Student student);
    Student getStudent(int id);
    List<Student> getAllStudents();
    int getStudentCount();
    int updateStudentList(Student student);
    void deleteStudent(Student student);
    void clearStudentList();

}
