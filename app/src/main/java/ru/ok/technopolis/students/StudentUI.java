package ru.ok.technopolis.students;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class StudentUI implements IStudentUI {
    private Random random = new Random();
    private final MainActivity view;
    private final EditText editTextName;
    private final EditText editTextSurname;
    private final RadioButton btnMale;
    private final RadioButton btnFemale;
    private final ImageView imageViewPhoto;

    StudentUI(MainActivity view) {
        this.view = view;
        editTextName = view.findViewById(R.id.editText__name);
        editTextSurname = view.findViewById(R.id.editText__surname);
        btnMale = view.findViewById(R.id.radioButton__male);
        btnFemale = view.findViewById(R.id.radioButton__female);
        imageViewPhoto = view.findViewById(R.id.imageView__student_avatar);
    }

    @Override
    public String getName() {
        if (editTextName.getText() != null && editTextName.getText().toString().length() != 0) {
            return editTextName.getText().toString();
        } else {
            return null;
        }
    }

    @Override
    public String getSurname() {
        if (editTextSurname.getText() != null && editTextSurname.getText().toString().length() != 0) {
            return editTextSurname.getText().toString();
        } else {
            return null;
        }
    }

    @Override
    public Student.Gender getGender() {
        if (btnMale.isChecked()) {
            return Student.Gender.MALE;
        } else {
            if (btnFemale.isChecked()) {
                return Student.Gender.FEMALE;
            } else {
                return null;
            }
        }
    }

    @Override
    public Integer getPhoto() {
        if (getGender() == null) {
            return null;
        }
        if (getGender() == Student.Gender.MALE) {
            return getRandomAvatar(R.drawable.male_1, R.drawable.male_2, R.drawable.male_3);
        } else {
            return getRandomAvatar(R.drawable.female_1, R.drawable.female_2, R.drawable.female_3);
        }
    }

    @Override
    public void setToast(int resId) {
        Toast.makeText(view, resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setToast(String massage) {
        Toast.makeText(view, massage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayStudent(Student student) {
        editTextName.setText(student.getFirstName());
        editTextSurname.setText(student.getSecondName());
        if (student.getGender() == Student.Gender.MALE) {
            btnMale.toggle();
        } else {
            btnFemale.toggle();
        }
        imageViewPhoto.setImageResource(student.getPhoto());
    }

    @Override
    public Button getBtn(BtnType btnType) {
        switch (btnType) {
            case ADD:
                return view.findViewById(R.id.button__add_student);
            case SAVE:
                return view.findViewById(R.id.button__save_student);
            case DELETE:
                return view.findViewById(R.id.button__delete_student);
        }
        return null;
    }

    @Override
    public final void clearFields() {
        editTextName.setText("");
        editTextSurname.setText("");
        ((RadioGroup) view.findViewById(R.id.radioGroup__gender)).clearCheck();
        imageViewPhoto.setImageResource(R.color.cardview_light_background);
    }

    @Override
    public RadioGroup getRadioGroup() {
        return view.findViewById(R.id.radioGroup__gender);
    }

    @Override
    public void displayAvatar(int resId) {
        imageViewPhoto.setImageResource(resId);

    }

    private int getRandomAvatar(int... avatars) {
        return avatars[random.nextInt(avatars.length)];
    }

    enum BtnType {
        ADD,
        SAVE,
        DELETE
    }
}

