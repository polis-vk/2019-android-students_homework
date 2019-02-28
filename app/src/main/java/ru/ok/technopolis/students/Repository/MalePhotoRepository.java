package ru.ok.technopolis.students.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import ru.ok.technopolis.students.R;

public class MalePhotoRepository implements PhotoRepository
{
    private static final MalePhotoRepository ourInstance = new MalePhotoRepository();

    private List <Integer> malePhotos;


    public static MalePhotoRepository getInstance() {
        return ourInstance;
    }

    private MalePhotoRepository() {
        malePhotos = new ArrayList() {{
            this.add(R.drawable.male_1);
            this.add(R.drawable.male_2);
            this.add(R.drawable.male_3);
        }};
    }


    @Override
    public Integer getPhotoInRepository() throws NoSuchElementException{
        if(malePhotos.isEmpty()){
            throw new NoSuchElementException();
        }
        int indexMalePhoto = new Random().nextInt(malePhotos.size());
        Integer malePhoto = malePhotos.get(indexMalePhoto);
        malePhotos.remove(indexMalePhoto);
        return malePhoto;
    }

    @Override
    public void putPhotoInRepository(Integer photo) {
        if(photo == null)
        {
            throw new NullPointerException();
        }
        malePhotos.add(photo);
    }
}
