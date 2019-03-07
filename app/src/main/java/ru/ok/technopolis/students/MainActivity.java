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
import android.widget.EditText;

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

    private Button addStudentButton;
    private Button saveStudentButton;
    private Button removeStudentButton;

    private Student activeStudent;
    private int newPhoto = -1;
    private boolean prevGender;

    private int[] femalesPhoto = new int[3];
    private int[] malesPhoto = new int[3];

    private Random random = new Random();
    private Vibrator vibe;
    private Animation shakeAnimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPhotos();
        generateStudentsList();
        setupRecyclerView();

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shakeAnimations = AnimationUtils.loadAnimation(this, R.anim.shake);

        studentAvatar = findViewById(R.id.student_profile__avatar);
        studentSecondName = findViewById(R.id.student_profile__second_name);
        studentFirstName = findViewById(R.id.student_profile__first_name);

        maleGender = findViewById(R.id.student_profile__male_gender);
        maleGender.setOnCheckedChangeListener((compoundButton, isMaleGender) -> {
            if(activeStudent != null) {
                if (prevGender != isMaleGender){
                    prevGender = isMaleGender;
                    newPhoto = (isMaleGender)
                            ? malesPhoto[random.nextInt(3)]
                            : femalesPhoto[random.nextInt(3)];
                    studentAvatar.setImageResource(newPhoto);
                } else {
                    studentAvatar.setImageResource(activeStudent.getPhoto());
                }

            }
        });

        addStudentButton = findViewById(R.id.student_list__button_add);
        addStudentButton.setOnClickListener(view -> {
            setEnabledField(true);
            clearFields();
            addStudentButton.setEnabled(false);
            int photo = femalesPhoto[random.nextInt(3)];
            activeStudent = new Student(photo);
            studentAvatar.setImageResource(photo);
        });

        saveStudentButton = findViewById(R.id.student_profile__buttons__save);
        saveStudentButton.setOnClickListener(view -> addNewStudent());

        removeStudentButton = findViewById(R.id.student_profile__buttons__remove);
        removeStudentButton.setOnClickListener(view -> removeStudent());

        setEnabledField(false);
    }

    private void clearFields() {
        activeStudent = null;
        studentAvatar.setImageBitmap(null);
        maleGender.setChecked(false);
        studentSecondName.setText("");
        studentFirstName.setText("");
    }

    private void setDefaultValues(){
        clearFields();
        addStudentButton.setEnabled(true);
        setEnabledField(false);
        studentsAdapter.removeHighlight();
        prevGender = false;
        newPhoto = -1;
    }

    private void setEnabledField(boolean enabled){
        studentAvatar.setEnabled(enabled);
        studentSecondName.setEnabled(enabled);
        studentFirstName.setEnabled(enabled);
        maleGender.setEnabled(enabled);
        saveStudentButton.setEnabled(enabled);
        removeStudentButton.setEnabled(enabled);
    }

    private void addNewStudent() {
        String secondName = studentSecondName.getText() != null
                ? studentSecondName.getText().toString().trim()
                : "";
        String firstName = studentFirstName.getText() != null
                ? studentFirstName.getText().toString().trim()
                : "";
        boolean isMale = maleGender.isChecked();

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
            activeStudent.setFirstName(firstName);
            activeStudent.setSecondName(secondName);
            activeStudent.setMaleGender(isMale);
            if (newPhoto != -1){
                activeStudent.setPhoto(newPhoto);
            }
            if (!students.contains(activeStudent)) {
                students.add(activeStudent);
            }
            Collections.sort(students);
            studentsAdapter.notifyDataSetChanged();
            setDefaultValues();
        }
    }

    private void removeStudent() {
        students.remove(activeStudent);
        studentsAdapter.notifyDataSetChanged();
        setDefaultValues();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.student_list__body);
        studentsAdapter = new StudentsAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student) {
        setEnabledField(true);
        addStudentButton.setEnabled(true);
        prevGender = false;
        newPhoto = -1;
        activeStudent = student;
        studentAvatar.setImageResource(activeStudent.getPhoto());
        studentSecondName.setText(activeStudent.getSecondName());
        studentFirstName.setText(activeStudent.getFirstName());
        prevGender = activeStudent.isMaleGender();
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
