package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random(2);
    private int randomIcon;
    private boolean sexOfStudent;
    private Student currentStudent;

    List<Student> students;
    TextView text;
    EditText firstName;
    EditText secondName;
    ImageView icon;
    FloatingActionButton add;
    View bottomView;
    RadioGroup sex;
    RadioButton male;
    RadioButton female;
    Button delete;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sex = findViewById(R.id.activity_main_sex);
        male = findViewById(R.id.activity_main_sex_male);
        female = findViewById(R.id.activity_main_sex_female);
        add = findViewById(R.id.activity_main_fb_add);
        text = findViewById(R.id.recycler_view_item_name);
        firstName = findViewById(R.id.activity_main_first_name);
        secondName = findViewById(R.id.activity_main_second_name);
        icon = findViewById(R.id.activity_main_icon);
        bottomView = findViewById(R.id.activity_main_bottom_view);
        delete = findViewById(R.id.activity_main_delete);
        save = findViewById(R.id.activity_main_save);
        RecyclerView recyclerView = findViewById(R.id.activity_main_recycler_view);

        StudentAdapter studentAdapter = new StudentAdapter(generateStudentList(),
                new StudentAdapter.Listener() {
                    @Override
                    public void onStudentClick(Student student) {
                        currentStudent = student;
                        firstName.setText(student.getFirstName());
                        secondName.setText(student.getSecondName());
                        icon.setImageResource(student.getPhoto());
                        add.hide();
                        bottomView.setVisibility(View.VISIBLE);

                        if (student.isMaleGender())
                            male.toggle();
                        else female.toggle();
                    }
                });

        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.hide();
                bottomView.setVisibility(View.VISIBLE);

                firstName.setText("");
                secondName.setText("");
                sex.clearCheck();
                icon.setVisibility(View.GONE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                students.remove(currentStudent);
                bottomView.setVisibility(View.GONE);
                add.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(firstName.getText().toString(),
                        secondName.getText().toString(),
                        sexOfStudent,
                        randomIcon);
                students.add(student);

                bottomView.setVisibility(View.GONE);
                add.show();
            }
        });

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_main_sex_male:
                        sexOfStudent = true;
                        randomIcon = R.drawable.male_3;
                        break;
                    case R.id.activity_main_sex_female:
                        sexOfStudent = false;
                        randomIcon = R.drawable.female_3;
                }
            }
        });
    }

        private List<Student> generateStudentList() {
            students = new LinkedList<>();
            students.add(new Student("Вася", "Пупкин", true, R.drawable.male_1));
            students.add(new Student("Инна", "Вупкина", false, R.drawable.female_1));
            students.add(new Student("Лариса", "Купкина", false, R.drawable.female_2));
            return students;
        }
    }
