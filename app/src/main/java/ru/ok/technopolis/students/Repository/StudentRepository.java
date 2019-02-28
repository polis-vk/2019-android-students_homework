package ru.ok.technopolis.students.Repository;

import java.util.List;

import ru.ok.technopolis.students.Student;

public interface StudentRepository
{
    List studentsOnRepository();

    void add(Student student);
    void delete(Student student);
    void edit(Student student);
}
