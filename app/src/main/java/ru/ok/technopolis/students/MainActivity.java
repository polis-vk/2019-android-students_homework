package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private List<Student> studentList;
    private StudentAdapter studentAdapter;
    private int currentPhotoResource;

    private int[] malePhotos = {
            R.drawable.male_1,
            R.drawable.male_2,
            R.drawable.male_3
    };
    private int[] femalePhotos = {
            R.drawable.female_1,
            R.drawable.female_2,
            R.drawable.female_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentList = generateStudentList();
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        initViews();
    }

    private void initViews() {
        getAddButton().setOnClickListener(v -> addStudent());
        getDeleteButton().setOnClickListener(v -> deleteStudent());
        getSaveButton().setOnClickListener(v -> saveStudent());

        getFirstNameEdit().addTextChangedListener(new StudentFieldsWatcher());
        getLastNameEdit().addTextChangedListener(new StudentFieldsWatcher());

        getGenderRadioGroup().setOnCheckedChangeListener(new GenderRadioButtonListener());

        getStudentCardGroup().setVisibility(View.GONE);
    }

    private void checkRequiredFields() {
        if (!getFirstNameEdit().getText().toString().isEmpty()
                && !getLastNameEdit().getText().toString().isEmpty()
                && getGenderRadioGroup().getCheckedRadioButtonId() != -1) {
            getSaveButton().setEnabled(true);
        } else {
            getSaveButton().setEnabled(false);
        }
    }

    private void clearStudentCard() {
        deselectCurrentStudent();
        currentPhotoResource = -1;
        getStudentPhoto().setImageDrawable(null);

        getLastNameEdit().setText("");
        getFirstNameEdit().setText("");
        getGenderRadioGroup().clearCheck();

        getDeleteButton().setEnabled(false);
        getSaveButton().setEnabled(false);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main__student_recycler);
        studentAdapter = new StudentAdapter(studentList, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private int getRandomPhoto(Gender gender) {
        if (gender == null) {
            return 0;
        } else {
            switch (gender) {
                case MALE: {
                    int i = random.nextInt(malePhotos.length);
                    return malePhotos[i];
                }
                case FEMALE: {
                    int i = random.nextInt(femalePhotos.length);
                    return femalePhotos[i];
                }
                default: {
                    return 0;
                }
            }
        }
    }

    private void onStudentClick(View view) {
        deselectCurrentStudent();

        Student student = (Student) view.getTag();
        selectNewStudent(student);

        getStudentPhoto().setImageResource(getSelectedStudent().getPhoto());
        currentPhotoResource = getSelectedStudent().getPhoto();

        getLastNameEdit().setText(getSelectedStudent().getSecondName());
        getFirstNameEdit().setText(getSelectedStudent().getFirstName());
        getGenderRadioGroup().check(getIdByGender(getSelectedStudent().getGender()));

        getStudentCardGroup().setVisibility(View.VISIBLE);

        getDeleteButton().setEnabled(true);
        getSaveButton().setEnabled(true);
    }

    private void deselectCurrentStudent() {
        if (getSelectedStudent() != null) {
            int index = studentList.indexOf(getSelectedStudent());
            studentAdapter.setSelectedStudent(null);
            studentAdapter.notifyItemChanged(index);
        }
    }

    private void selectNewStudent(Student student) {
        studentAdapter.setSelectedStudent(student);
        int index = studentList.indexOf(getSelectedStudent());
        studentAdapter.notifyItemChanged(index);
    }

    private void saveStudent() {
        if (getSelectedStudent() != null) {
            getSelectedStudent().setFirstName(getFirstNameEdit().getText().toString());
            getSelectedStudent().setSecondName(getLastNameEdit().getText().toString());
            getSelectedStudent().setGender(getGenderById(getGenderRadioGroup().getCheckedRadioButtonId()));
            getSelectedStudent().setPhoto(currentPhotoResource);
            deselectCurrentStudent();
        } else {
            Student newStudent = new Student(getFirstNameEdit().getText().toString(),
                    getLastNameEdit().getText().toString(),
                    getGenderById(getGenderRadioGroup().getCheckedRadioButtonId()),
                    currentPhotoResource);
            studentList.add(newStudent);
            studentAdapter.notifyItemInserted(studentList.size() - 1);
        }
        getStudentCardGroup().setVisibility(View.GONE);
    }

    private Gender getGenderById(int id) {
        switch (id) {
            case R.id.main__male_radio: {
                return Gender.MALE;
            }
            case R.id.main__female_radio: {
                return Gender.FEMALE;
            }
            default: {
                return null;
            }
        }
    }

    private int getIdByGender(Gender gender) {
        if (gender == null) {
            return -1;
        } else {
            switch (gender) {
                case MALE: {
                    return R.id.main__male_radio;
                }
                case FEMALE: {
                    return R.id.main__female_radio;
                }
                default: {
                    return -1;
                }
            }
        }
    }

    private void deleteStudent() {
        if (getSelectedStudent() != null) {
            int i = studentList.indexOf(getSelectedStudent());
            studentList.remove(i);
            studentAdapter.notifyItemRemoved(i);
            clearStudentCard();
            getStudentCardGroup().setVisibility(View.GONE);
        }
    }

    private void addStudent() {
        clearStudentCard();
        getStudentCardGroup().setVisibility(View.VISIBLE);
    }

    private List<Student> generateStudentList() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Иван", "Иванов", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Анастасия", "Медведева", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Петр", "Петров", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Екатерина", "Зайцева", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Антон", "Котов", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Анна", "Фёдорова", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Евгений", "Беляев", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Кристина", "Ветрова", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Андрей", "Андреев", Gender.MALE, getRandomPhoto(Gender.MALE)));
        return studentList;
    }

    private Button getAddButton() {
        return findViewById(R.id.main__add_student_button);
    }

    private Button getSaveButton() {
        return findViewById(R.id.main__save_button);
    }

    private Button getDeleteButton() {
        return findViewById(R.id.main__delete_button);
    }

    private EditText getLastNameEdit() {
        return findViewById(R.id.main__edit_last_name);
    }

    private EditText getFirstNameEdit() {
        return findViewById(R.id.main__edit_first_name);
    }

    private ImageView getStudentPhoto() {
        return findViewById(R.id.main__student_photo);
    }

    private RadioGroup getGenderRadioGroup() {
        return findViewById(R.id.main__gender_radio_group);
    }

    private Group getStudentCardGroup() {
        return findViewById(R.id.main__student_card_group);
    }

    private Student getSelectedStudent() {
        return studentAdapter.getSelectedStudent();
    }

    class StudentFieldsWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkRequiredFields();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class GenderRadioButtonListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group == getGenderRadioGroup()) {
                if (checkedId != -1 && getSelectedStudent() == null) {
                    RadioButton radioButton = findViewById(checkedId);
                    if (radioButton.isChecked()) {
                        switch (checkedId) {
                            case R.id.main__male_radio: {
                                int randomPhoto = getRandomPhoto(Gender.MALE);
                                currentPhotoResource = randomPhoto;
                                getStudentPhoto().setImageResource(randomPhoto);
                                break;
                            }
                            case R.id.main__female_radio: {
                                int randomPhoto = getRandomPhoto(Gender.FEMALE);
                                currentPhotoResource = randomPhoto;
                                getStudentPhoto().setImageResource(randomPhoto);
                            }
                        }
                    }
                }

            }
            checkRequiredFields();
        }
    }
}
