package ru.ok.technopolis.students.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.ok.technopolis.students.Student;

public class StudentDataRepository implements StudentRepository {

    public static final StudentDataRepository Instance = new StudentDataRepository();

    private List<Student> students;

    private StudentDataRepository() {
        students = new ArrayList<>();
    }

    @Override
    public List<Student> studentsOnRepository() {
        return students;
    }

    @Override
    public void add(Student student) {
        students.add(student);
    }


    @Override
    public void delete(Student student) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student nextStudent = iterator.next();
            if (nextStudent.getId().equals(student.getId())) {
                iterator.remove();
            }
        }
    }

    @Override
    public void edit(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.get(i).setFirstName(student.getFirstName());
                students.get(i).setSecondName(student.getSecondName());
                students.get(i).setPhoto(student.getPhoto());
            }
        }
    }
}
