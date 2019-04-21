package ru.ok.technopolis.students.app;

import android.app.Application;

import ru.ok.technopolis.students.dao.StudentDAO;
import ru.ok.technopolis.students.dao.StudentsPhotoDAO;

public class App extends Application {

    private static App instance;
    private StudentDAO studentDAO;
    private StudentsPhotoDAO studentsPhotoDAO;

    public static App getInstance() {
        return instance;
    }

    public StudentDAO getStudentDAO () {
        return studentDAO;
    }

    public StudentsPhotoDAO getStudentsPhotoDAO() {
        return studentsPhotoDAO;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        studentDAO = StudentDAO.getInstance();
        studentsPhotoDAO = StudentsPhotoDAO.getInstance();
    }
}
