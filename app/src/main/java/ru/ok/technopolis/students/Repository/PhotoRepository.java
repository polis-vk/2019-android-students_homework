package ru.ok.technopolis.students.Repository;

public interface PhotoRepository {
    int getPhotoInRepository();

    void putPhotoInRepository(int photo);
}
