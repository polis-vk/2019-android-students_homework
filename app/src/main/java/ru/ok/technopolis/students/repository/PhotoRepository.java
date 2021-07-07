package ru.ok.technopolis.students.repository;

public interface PhotoRepository {
    int getPhotoInRepository();

    void putPhotoInRepository(int photo);
}
