package ru.ok.technopolis.students.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import ru.ok.technopolis.students.R;

public class FemalePhotoRepository implements PhotoRepository {

    public static final FemalePhotoRepository Instance = new FemalePhotoRepository();

    private List<Integer> femalePhotos;

    private FemalePhotoRepository() {
        femalePhotos = new ArrayList<Integer>() {{
            this.add(R.drawable.female_1);
            this.add(R.drawable.female_2);
            this.add(R.drawable.female_3);
        }};
    }


    @Override
    public int getPhotoInRepository() throws NoSuchElementException {
        if (femalePhotos.isEmpty()) {
            throw new NoSuchElementException();
        }
        int indexFemalePhoto = new Random().nextInt(femalePhotos.size());
        int femalePhoto = femalePhotos.get(indexFemalePhoto);
        femalePhotos.remove(indexFemalePhoto);
        return femalePhoto;
    }

    @Override
    public void putPhotoInRepository(int photo) {
        femalePhotos.add(photo);
    }
}
