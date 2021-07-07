package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StudentAdapter studentAdapter;

    private List<Student> students;

    private int currentStudent = -1;

    private EditText nameInput;

    private EditText surnameInput;

    private CheckBox genderCheckBox;

    private Animation shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__student_list);

        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(students, student -> {
            currentStudent = students.indexOf(student);
            updateStudentInfo();
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupButtons();

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        nameInput = findViewById(R.id.activity_main__name);
        surnameInput = findViewById(R.id.activity_main__surname);
        genderCheckBox = findViewById(R.id.activity_main__gender_checkbox);
    }

    private void setupButtons() {
        Button addStudent = findViewById(R.id.activity_main__add_student_btn);
        addStudent.setOnClickListener(v -> onAddStudentClick());

        Button removeStudent = findViewById(R.id.activity_main__remove_student_btn);
        removeStudent.setOnClickListener(v -> onRemoveStudentClick());

        Button saveStudent = findViewById(R.id.activity_main__save_student_btn);
        saveStudent.setOnClickListener(v -> onSaveStudentClick());
    }

    private void onAddStudentClick() {
        students.add(getStudentStub());
        currentStudent = students.size() - 1;
        studentAdapter.handleStudentAddition();
        studentAdapter.notifyDataSetChanged();
        updateStudentInfo();
    }

    private void onSaveStudentClick() {
        boolean inputCorrect = true;
        if (currentStudent >= 0) {
            Student student = students.get(currentStudent);
            Student userData = getStudentData();


            if (userData.getFirstName().equals(getString(R.string.name)) || userData.getFirstName().isEmpty()) {
                inputCorrect = false;
                nameInput.startAnimation(shake);
            } else {
                student.setFirstName(userData.getFirstName());
            }

            if (userData.getSecondName().equals(getString(R.string.surname)) || userData.getSecondName().isEmpty()) {
                inputCorrect = false;
                surnameInput.startAnimation(shake);
            } else {
                student.setSecondName(userData.getSecondName());
            }

            student.setMaleGender(userData.isMaleGender());
            student.setPhoto(userData.getPhoto());

            if (inputCorrect) {
                studentAdapter.notifyDataSetChanged();
                updateStudentInfo();
            }
        }
    }

    private void onRemoveStudentClick() {
        if (currentStudent >= 0) {
            students.remove(currentStudent);
            currentStudent--;
            studentAdapter.handleStudentRemoval();
            studentAdapter.notifyDataSetChanged();
            updateStudentInfo();
        }
    }

    private void updateStudentInfo() {
        ImageView studentPhoto = findViewById(R.id.activity_main__photo);

        Student student = currentStudent >= 0 ? students.get(currentStudent) : getStudentStub();
        studentPhoto.setImageResource(student.getPhoto());
        nameInput.setText(student.getFirstName());
        surnameInput.setText(student.getSecondName());
        genderCheckBox.setChecked(student.isMaleGender());
    }

    private Student getStudentStub() {
        return new Student(
                getString(R.string.name),
                getString(R.string.surname),
                true,
                getMalePhoto(getString(R.string.name))
        );
    }

    private Student getStudentData() {
        String name = nameInput.getText().toString().replaceAll(" +", "");
        int photo = genderCheckBox.isChecked() ? getMalePhoto(name) : getFemalePhoto(name);
        return new Student(
                name,
                surnameInput.getText().toString().replaceAll(" +", ""),
                genderCheckBox.isChecked(),
                photo
        );
    }

    private int getMalePhoto(String name) {
        int hash = name.hashCode() % 10;
        if (hash > 6) return R.drawable.male_1;
        else if (hash > 3) return R.drawable.male_2;
        else return R.drawable.male_3;
    }

    private int getFemalePhoto(String name) {
        int hash = name.hashCode() % 10;
        if (hash > 6) return R.drawable.female_1;
        else if (hash > 3) return R.drawable.female_2;
        else return R.drawable.female_3;
    }

}
