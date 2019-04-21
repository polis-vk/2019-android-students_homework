package ru.ok.technopolis.students.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.ok.technopolis.students.model.Student;

public class StudentDAO  {

    private static final StudentDAO Instance = new StudentDAO();

    public static StudentDAO getInstance() {
        return Instance;
    }

    private List<Student> students;

    private StudentDAO() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void insert(Student student) {
        students.add(student);
    }

    public void delete(Student student) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student nextStudent = iterator.next();
            if (nextStudent.getId().equals(student.getId())) {
                iterator.remove();
            }
        }
    }

    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.get(i).setFirstName(student.getFirstName());
                students.get(i).setSecondName(student.getSecondName());
                students.get(i).setPhoto(student.getPhoto());
            }
        }
    }
}
