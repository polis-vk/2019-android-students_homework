package ru.ok.technopolis.students;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private List<Student> students = new ArrayList<>();
    private StudentsAdapter studentsAdapter;

    private CircleImageView studentAvatar;
    private EditText studentSecondName;
    private EditText studentFirstName;

    private CheckBox maleGender;
    private Student activeStudent;

    private int[] femalesPhoto = new int[3];
    private int[] malesPhoto = new int[3];

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPhotos();
        generateStudentsList();
        setupRecyclerView();

        studentAvatar = findViewById(R.id.student_profile__avatar);
        studentSecondName = findViewById(R.id.student_profile__second_name);
        studentFirstName = findViewById(R.id.student_profile__first_name);

        maleGender = findViewById(R.id.student_profile__male_gender);
        maleGender.setOnCheckedChangeListener((compoundButton, b) -> {
            if (activeStudent != null) {
                activeStudent.setMaleGender(b);
            }
        });


        Button addStudentButton = findViewById(R.id.student_list__button_add);
        addStudentButton.setOnClickListener(view -> clearFields());

        Button saveStudentButton = findViewById(R.id.student_profile__buttons__save);
        saveStudentButton.setOnClickListener(view -> addNewStudent());

        Button removeStudentButton = findViewById(R.id.student_profile__buttons__remove);
        removeStudentButton.setOnClickListener(view -> removeStudent());
    }

    private void clearFields() {
        activeStudent = null;
        studentAvatar.setImageBitmap(null);
        maleGender.setChecked(false);
        studentSecondName.setText("");
        studentFirstName.setText("");
    }

    private void addNewStudent() {
        Animation shakeAnimations = AnimationUtils.loadAnimation(this, R.anim.shake);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        String secondName = studentSecondName.getText().toString().trim();
        String firstName = studentFirstName.getText().toString().trim();
        Boolean isMale = maleGender.isChecked();

        if (secondName.equals("") || firstName.equals("")) {
            if (secondName.equals("")) {
                studentSecondName.startAnimation(shakeAnimations);
            }
            if (firstName.equals("")) {
                studentFirstName.startAnimation(shakeAnimations);
            }
            if (vibe != null) {
                vibe.vibrate(100);
            }
        }  else {
            if (activeStudent == null) {
                int photo = (isMale) ? malesPhoto[random.nextInt(3)] : femalesPhoto[random.nextInt(3)];
                students.add(new Student(firstName, secondName, isMale, photo));
                Collections.sort(students);
            } else {
                activeStudent.setFirstName(firstName);
                activeStudent.setSecondName(secondName);
                activeStudent.setMaleGender(maleGender.isChecked());
            }
            studentsAdapter.notifyDataSetChanged();
            clearFields();
        }
    }

    private void removeStudent() {
        if (activeStudent != null) {
            students.remove(activeStudent);
            studentsAdapter.notifyDataSetChanged();
        }
        clearFields();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.student_list__body);
        studentsAdapter = new StudentsAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student) {
        activeStudent = student;

        studentAvatar.setImageResource(activeStudent.getPhoto());
        studentSecondName.setText(activeStudent.getSecondName());
        studentFirstName.setText(activeStudent.getFirstName());
        maleGender.setChecked(activeStudent.isMaleGender());
    }

    private void setupPhotos() {
        femalesPhoto[0] = R.drawable.female_1;
        femalesPhoto[1] = R.drawable.female_2;
        femalesPhoto[2] = R.drawable.female_3;
        arrayShuffle(femalesPhoto);

        malesPhoto[0] = R.drawable.male_1;
        malesPhoto[1] = R.drawable.male_2;
        malesPhoto[2] = R.drawable.male_3;
        arrayShuffle(malesPhoto);
    }


    private void generateStudentsList() {
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    students.add(new Student("Иван", "Иванов", true, malesPhoto[random.nextInt(3)]));
                    students.add(new Student("Анастасия", "Медведева", false, femalesPhoto[random.nextInt(3)]));
                    break;

                case 1:
                    students.add(new Student("Петр", "Петров", true, malesPhoto[random.nextInt(3)]));
                    students.add(new Student("Валерия", "Прокофьева", false, femalesPhoto[random.nextInt(3)]));
                    break;

                case 2:
                    students.add(new Student("Денис", "Денисов", true, malesPhoto[random.nextInt(3)]));
                    students.add(new Student("Александра", "Волкова", false, femalesPhoto[random.nextInt(3)]));
                    break;
            }
        }
        Collections.sort(students);
    }

    private static void arrayShuffle (int[] arr) {
        Random random = new Random();
        for (int i=0; i < arr.length; i++) {
            int randomPosition = random.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomPosition];
            arr[randomPosition] = temp;
        }
    }

}
