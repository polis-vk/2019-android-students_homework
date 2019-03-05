package ru.ok.technopolis.students.Repository;

public interface PhotoRepository {
    Integer getPhotoInRepository ();
    void putPhotoInRepository (Integer photo);
}
