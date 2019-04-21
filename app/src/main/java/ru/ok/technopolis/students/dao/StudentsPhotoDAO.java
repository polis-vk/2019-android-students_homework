package ru.ok.technopolis.students.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import ru.ok.technopolis.students.R;

public class StudentsPhotoDAO {

    private List <Integer> femalePhotos;
    private List <Integer> malePhotos;
    private static final StudentsPhotoDAO instance = new StudentsPhotoDAO();

    private StudentsPhotoDAO() {
        femalePhotos = new ArrayList<Integer>(){{
            add(R.drawable.female_1);
            add(R.drawable.female_2);
            add(R.drawable.female_3);
        }};
        malePhotos = new ArrayList<Integer>(){{
            add(R.drawable.male_1);
            add(R.drawable.male_2);
            add(R.drawable.male_3);
        }};
    }

    public static StudentsPhotoDAO getInstance() {
        return instance;
    }

    public int getFemalePhoto() throws NoSuchElementException {
        int indexFemalePhoto = new Random().nextInt(femalePhotos.size());
        return femalePhotos.get(indexFemalePhoto);
    }

    public int getMalePhoto() throws NoSuchElementException {
        int indexMalePhoto = new Random().nextInt(malePhotos.size());
        return malePhotos.get(indexMalePhoto);
    }

}
