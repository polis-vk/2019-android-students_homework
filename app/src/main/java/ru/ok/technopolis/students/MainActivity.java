package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private int r;
    private int randomIcon;
    private boolean sexOfStudent;
    private Student currentStudent;

    private List<Student> students;
    private EditText firstNameText;
    private EditText secondNameText;
    private ImageView icon;
    private FloatingActionButton addBtn;
    private View bottomView;
    private RadioGroup sexRadioGroup;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;
    private Button deleteBtn;
    private Button saveBtn;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        studentAdapter = new StudentAdapter(generateStudentList(),
                new StudentAdapter.Listener() {
                    @Override
                    public void onStudentClick(Student student, View v, View lastV) {
                        currentStudent = student;
                        firstNameText.setText(student.getFirstName());
                        secondNameText.setText(student.getSecondName());
                        icon.setImageResource(student.getPhoto());
                        addBtn.hide();
                        bottomView.setVisibility(View.VISIBLE);

                        if (student.isMaleGender()) {
                            maleRadioBtn.toggle();
                        } else {
                            femaleRadioBtn.toggle();
                        }

                        v.setBackgroundResource(R.color.colorPrimary);
                        if (lastV != null) {
                            lastV.setBackgroundResource(R.color.white);
                        }
                    }
                });

        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        setAddBtn();
        setDeleteAddBtn();
        setSaveBtn();
        setSexRadioGroup();
    }

    private void findViews() {
        sexRadioGroup = findViewById(R.id.activity_main_sex);
        maleRadioBtn = findViewById(R.id.activity_main_sex_male);
        femaleRadioBtn = findViewById(R.id.activity_main_sex_female);
        addBtn = findViewById(R.id.activity_main_fb_add);
        firstNameText = findViewById(R.id.activity_main_first_name);
        secondNameText = findViewById(R.id.activity_main_second_name);
        icon = findViewById(R.id.activity_main_icon);
        bottomView = findViewById(R.id.activity_main_bottom_view);
        deleteBtn = findViewById(R.id.activity_main_delete);
        saveBtn = findViewById(R.id.activity_main_save);
        recyclerView = findViewById(R.id.activity_main_recycler_view);
    }

    private void setSexRadioGroup() {
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.activity_main_sex_male) {
                    sexOfStudent = true;
                } else if (checkedId == R.id.activity_main_sex_female) {
                    sexOfStudent = false;
                }
                if (sexOfStudent) {
                    switch (r) {
                        case 0:
                            randomIcon = R.drawable.male_1;
                            break;
                        case 1:
                            randomIcon = R.drawable.male_2;
                            break;
                        case 2:
                            randomIcon = R.drawable.male_3;
                            break;
                    }
                } else {
                    switch (r) {
                        case 0:
                            randomIcon = R.drawable.female_1;
                            break;
                        case 1:
                            randomIcon = R.drawable.female_2;
                            break;
                        case 2:
                            randomIcon = R.drawable.female_3;
                            break;
                    }
                }
                icon.setImageResource(randomIcon);
            }
        });
    }

    private void setSaveBtn() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstNameText.getText().toString().equals("") &&
                        !secondNameText.getText().toString().equals("")) {
                    Student student = new Student(firstNameText.getText().toString(),
                            secondNameText.getText().toString(),
                            sexOfStudent,
                            randomIcon);
                    students.add(student);

                    bottomView.setVisibility(View.GONE);
                    addBtn.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Имя или фамилия не заданы",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDeleteAddBtn() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                students.remove(currentStudent);
                studentAdapter.notifyDataSetChanged();
                bottomView.setVisibility(View.GONE);
                addBtn.show();
            }
        });
    }

    private void setAddBtn() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.hide();
                bottomView.setVisibility(View.VISIBLE);

                firstNameText.setText("");
                secondNameText.setText("");
                sexRadioGroup.clearCheck();
                r = random.nextInt(3);
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
