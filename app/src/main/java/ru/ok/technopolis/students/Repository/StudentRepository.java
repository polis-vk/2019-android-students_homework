package ru.ok.technopolis.students.Repository;

import java.util.Collection;
import java.util.List;

import ru.ok.technopolis.students.Student;

public interface StudentRepository
{
    List studentsOnRepository();

    boolean isAlreadyInRepository(Student student);
    boolean add(Student student);
    boolean delete(Student student);
}
