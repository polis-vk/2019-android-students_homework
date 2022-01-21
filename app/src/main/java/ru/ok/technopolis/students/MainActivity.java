package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private List<Student> students;
    private EditText firstName;
    private EditText secondName;
    private CheckBox gender;
    private ImageView avatar;
    private int imageResource;
    private StudentAdapter studentAdapter;
    private Student selectStudent;
    public static int selectedPosition = -1;
    private final static Student EMPTY_STUDENT = new Student("","", false,0);
    private View profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectStudent = EMPTY_STUDENT;
        profile = findViewById(R.id.profile);
        firstName = findViewById(R.id.profile__et_first_name);
        secondName = findViewById(R.id.profile__et_second_name);
        gender = findViewById(R.id.profile__cb_male_gender);
        avatar = findViewById(R.id.profile__iv_avatar);
        students = new ArrayList<>();

        setupRecyclerView();
        setupButtons();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);
        studentAdapter = new StudentAdapter(students, (student)->{
                profile.setVisibility(View.GONE);

                firstName.setText(student.getFirstName());
                secondName.setText(student.getSecondName());
                gender.setChecked(student.isMaleGender());
                avatar.setImageResource(student.getPhoto());
                selectStudent = student;
                selectedPosition = students.indexOf(student);

                profile.setVisibility(View.VISIBLE);
                profile.requestFocus();
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setupButtons() {
        findViewById(R.id.profile__save_button).setOnClickListener(v -> onSaveClick());
        findViewById(R.id.profile__delete_button).setOnClickListener(v -> onDeleteClick());
        findViewById(R.id.activity_main__add_button).setOnClickListener(v -> onAddClick());
        gender.setOnClickListener(v -> onChecked());
    }

    public static int getPhoto(int i, boolean maleGender) {
        switch (i) {
            case 0: return maleGender ? R.drawable.male_1 : R.drawable.female_1;
            case 1: return maleGender ? R.drawable.male_2 : R.drawable.female_2;
            case 2: return maleGender ? R.drawable.male_3 : R.drawable.female_3;
            default: return R.drawable.female_1;
        }
    }

    private void onAddClick() {
        clearProfile();
        selectedPosition = -1;
        studentAdapter.notifyDataSetChanged();
        selectStudent = EMPTY_STUDENT;
        imageResource = getPhoto(random.nextInt(3), EMPTY_STUDENT.isMaleGender());
        avatar.setImageResource(imageResource);
        profile.setVisibility(View.VISIBLE);
        profile.requestFocus();
    }

    private void onSaveClick() {
        if (checkFields()) {
            if (selectStudent == EMPTY_STUDENT) {
                students.add(new Student(firstName.getText().toString(), secondName.getText().toString(),
                        gender.isChecked(), imageResource));
            } else {
                Student newStudent = new Student(firstName.getText().toString(), secondName.getText().toString(),
                        gender.isChecked(), selectStudent.getPhoto());
                if (students.contains(selectStudent)) {
                    students.add(students.indexOf(selectStudent), newStudent);
                    students.remove(selectStudent);
                } else {
                    students.add(newStudent);
                }
            }
            profile.setVisibility(View.GONE);
            studentAdapter.notifyDataSetChanged();
            clearProfile();
        }
    }

    private void onDeleteClick() {
        students.remove(selectStudent);
        selectedPosition = -1;
        studentAdapter.notifyDataSetChanged();
        profile.setVisibility(View.GONE);
        clearProfile();
    }

    private void onChecked() {
        imageResource = getPhoto(random.nextInt(3), gender.isChecked());
        selectStudent.setPhoto(imageResource);
        avatar.setImageResource(imageResource);
    }

    private void clearProfile() {
        firstName.setText(EMPTY_STUDENT.getFirstName());
        secondName.setText(EMPTY_STUDENT.getSecondName());
        gender.setChecked(EMPTY_STUDENT.isMaleGender());
        avatar.setImageResource(EMPTY_STUDENT.getPhoto());
    }

    @Override
    public void onBackPressed() {
        if (profile.getVisibility() == View.VISIBLE) {
            profile.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    private boolean checkFields() {
        if (firstName.getText() != null && secondName.getText() != null && firstName.getText().toString().trim().length() > 0 && secondName.getText().toString().trim().length() > 0) {
            firstName.setError(null);
            secondName.setError(null);
            return true;
        }
        if (firstName.getText() != null && firstName.getText().toString().trim().length() == 0) {
            firstName.setError(getString(R.string.miss_first_name));
        }
        if (secondName.getText() != null && secondName.getText().toString().trim().length() == 0) {
            secondName.setError(getString(R.string.miss_second_name));
        }
        return false;
    }
}
