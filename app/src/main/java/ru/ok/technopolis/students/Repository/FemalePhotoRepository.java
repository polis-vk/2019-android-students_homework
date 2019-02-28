package ru.ok.technopolis.students.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import ru.ok.technopolis.students.R;

public class FemalePhotoRepository implements PhotoRepository {
    private static final FemalePhotoRepository ourInstance = new FemalePhotoRepository();

    public static FemalePhotoRepository getInstance() {
        return ourInstance;
    }

    private List <Integer> femalePhotos;

    private FemalePhotoRepository() {
        femalePhotos = new ArrayList(){{
            this.add(R.drawable.female_1);
            this.add(R.drawable.female_2);
            this.add(R.drawable.female_3);
        }};
    }


    @Override
    public Integer getPhotoInRepository() throws NoSuchElementException {
        if(femalePhotos.isEmpty()){
            throw new NoSuchElementException();
        }
        int indexMalePhoto = new Random().nextInt(femalePhotos.size());
        Integer malePhoto = femalePhotos.get(indexMalePhoto);
        femalePhotos.remove(indexMalePhoto);
        return malePhoto;
    }

    @Override
    public void putPhotoInRepository(Integer photo) {
        if(photo == null)
        {
            throw new NullPointerException();
        }
        femalePhotos.add(photo);
    }
}
