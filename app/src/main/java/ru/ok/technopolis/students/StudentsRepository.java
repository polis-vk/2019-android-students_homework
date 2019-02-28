package ru.ok.technopolis.students;

import java.util.ArrayList;
import java.util.List;

public class StudentsRepository {

    private final List<Student> students;
    private Integer activeId;

    public StudentsRepository() {
        this(new ArrayList<Student>());
    }

    public StudentsRepository(List<Student> students) {
        this.students = students;
        this.activeId = null;
    }

    public int size() {
        return students.size();
    }

    public Student get(int i) {
        return students.get(i);
    }

    public void setActive(Integer i) {
        this.activeId = i;
    }

    public Integer getActive() {
        return activeId;
    }

    public Student getActiveStudent() {
        return students.get(activeId);
    }

    public void removeActive() {
        if (activeId != null) {
            students.remove(activeId.intValue());
            activeId = null;
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        activeId = students.size() - 1;
    }
}
