package ru.ok.technopolis.students.Repository;

import java.util.ArrayList;
import java.util.List;

import ru.ok.technopolis.students.Student;

public class StudentDataRepository implements StudentRepository
{
    private static final StudentDataRepository ourInstance = new StudentDataRepository();

    public static StudentDataRepository getInstance() {
        return ourInstance;
    }

    private List <Student> students;



    private StudentDataRepository()
    {
        students = new ArrayList<>();
    }

    @Override
    public List studentsOnRepository()
    {
        return students;
    }

    @Override
    public boolean isAlreadyInRepository(Student student) {
        if(student == null)
        {
            throw new NullPointerException();
        }
        for(int i = 0; i < students.size(); i ++){
            if(students.get(i).getId().equals(student.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(Student student)
    {
        if(isAlreadyInRepository(student)){
            return false;
        }
        students.add(student);
        return true;
    }


    @Override
    public boolean delete(Student student)
    {
        if(isAlreadyInRepository(student)){
            students.remove(student);
            return true;
        }
        return false;
    }
}
