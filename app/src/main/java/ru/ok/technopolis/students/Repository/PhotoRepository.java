package ru.ok.technopolis.students.Repository;

import java.util.List;

public interface PhotoRepository
{
    Integer getPhotoInRepository ();
    void putPhotoInRepository (Integer photo);
}
