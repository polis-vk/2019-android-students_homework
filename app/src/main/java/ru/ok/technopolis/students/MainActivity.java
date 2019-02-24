package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private List<Student> studentList;
    private StudentAdapter studentAdapter;
    private Student selectedStudent;

    private ImageView studentPhoto;
    private EditText lastNameEdit;
    private EditText firstNameEdit;
    private RadioGroup genderRadioGroup;


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
        initStudentCard();
        initButtons();
    }

    private void initButtons() {
        findViewById(R.id.main__add_student_button).setOnClickListener(v -> addStudent());
        findViewById(R.id.main__delete_button).setOnClickListener(v -> deleteStudent());
        findViewById(R.id.main__save_button).setOnClickListener(v -> applyDataToStudent());
    }

    private void initStudentCard() {
        studentPhoto = findViewById(R.id.main__student_photo);
        lastNameEdit = findViewById(R.id.main__edit_last_name);
        firstNameEdit = findViewById(R.id.main__edit_first_name);
        genderRadioGroup = findViewById(R.id.main__gender_radio_group);
    }

    private void clearStudentCard() {
        selectedStudent = null;
        studentPhoto.setImageResource(0);
        lastNameEdit.setText("");
        firstNameEdit.setText("");
        genderRadioGroup.clearCheck();
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
        Student student = (Student) view.getTag();
        selectedStudent = student;
        studentPhoto.setImageResource(student.getPhoto());
        lastNameEdit.setText(student.getSecondName());
        firstNameEdit.setText(student.getFirstName());
        genderRadioGroup.check(getIdByGender(student.getGender()));
        view.setSelected(true);
    }

    private void applyDataToStudent() {
        if (selectedStudent != null) {
            selectedStudent.setFirstName(firstNameEdit.getText().toString());
            selectedStudent.setSecondName(lastNameEdit.getText().toString());

            Gender gender = getGenderById(genderRadioGroup.getCheckedRadioButtonId());
            if (selectedStudent.getPhoto() == 0 || selectedStudent.getGender() != gender) {
                int randomPhoto = getRandomPhoto(gender);
                selectedStudent.setPhoto(randomPhoto);
                studentPhoto.setImageResource(randomPhoto);
            }
            selectedStudent.setGender(gender);

            int i = studentList.indexOf(selectedStudent);
            studentAdapter.notifyItemChanged(i);
        }
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
        if (selectedStudent != null) {
            int i = studentList.indexOf(selectedStudent);
            studentList.remove(i);
            studentAdapter.notifyItemRemoved(i);
            clearStudentCard();
        }
    }

    private void addStudent() {
        studentList.add(new Student("", "", null, 0));
        studentAdapter.notifyItemInserted(studentList.size() - 1);
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
}
