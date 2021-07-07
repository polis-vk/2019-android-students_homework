package ru.ok.technopolis.students.controller;

import java.util.NoSuchElementException;

import ru.ok.technopolis.students.Student;
import ru.ok.technopolis.students.repository.FemalePhotoRepository;
import ru.ok.technopolis.students.repository.MalePhotoRepository;
import ru.ok.technopolis.students.repository.PhotoRepository;

public class StudentActivityController {

    public static final StudentActivityController Instance = new StudentActivityController();

    private PhotoRepository photoRepository;
    private Student currentStudent;

    public void setCurrentStudent(Student student) {
        currentStudent = student;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    private StudentActivityController() {

    }


    public boolean modifyStudent() {
        return currentStudent.photoAvailable();
    }


    private void setPhotoRepository(boolean genderFlag) {
        if (genderFlag) {
            photoRepository = MalePhotoRepository.Instance;
        } else {
            photoRepository = FemalePhotoRepository.Instance;
        }
    }

    public void putPhotoInPhotoRepository(Student student) {
        setPhotoRepository(student.isMaleGender());
        photoRepository.putPhotoInRepository(student.getPhoto());
    }


    public boolean createStudent(boolean genderFlag) {
        return setPhotoForStudent(genderFlag);
    }

    private boolean setPhotoForStudent(boolean isChecked) {
        try {
            setPhotoRepository(isChecked);
            currentStudent.setPhoto(photoRepository.getPhotoInRepository());
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void setStudentInitials(String firstName, String secondName, boolean isMale) {
        currentStudent.setMaleGender(isMale);
        currentStudent.setFirstName(firstName);
        currentStudent.setSecondName(secondName);
    }
}