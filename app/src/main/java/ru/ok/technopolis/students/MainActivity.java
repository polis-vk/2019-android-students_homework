package ru.ok.technopolis.students;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private Student currentStudent;

    private final String DEFAULTFIRSTNAME = "Firstname";
    private final String DEFAULTSECONDNAME = "Secondname";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list);
        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(students, new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                EditText firstName = findViewById(R.id.first_name_info);
                firstName.setText(student.getFirstName());
                EditText secondName = findViewById(R.id.second_name_info);
                secondName.setText(student.getSecondName());
                ImageView photo = findViewById(R.id.big_photo);
                photo.setImageResource(student.getPhoto());
                CheckBox checkBox = findViewById(R.id.checkbox);
                checkBox.setChecked(student.isMaleGender());
                currentStudent = student;
            }
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setupAddButton();
        setupDeleteButton();
        setupSaveButton();
    }

    private void setupAddButton() {
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                boolean maleGender = random.nextBoolean();
                int startPhoto = 0;
                if (maleGender) {
                    switch (random.nextInt(3)) {
                        case 0: startPhoto = R.drawable.male_1;
                        break;
                        case 1: startPhoto = R.drawable.male_2;
                        break;
                        case 2: startPhoto = R.drawable.male_3;
                        break;
                    }
                } else {
                    switch (random.nextInt(3)) {
                        case 0: startPhoto = R.drawable.female_1;
                            break;
                        case 1: startPhoto = R.drawable.female_2;
                            break;
                        case 2: startPhoto = R.drawable.female_3;
                            break;
                    }
                }
                students.add(new Student(DEFAULTFIRSTNAME, DEFAULTSECONDNAME, maleGender, startPhoto));
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupDeleteButton() {
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = students.size();
                if (size != 0) {
                    if (size > 1) {
                        int index = students.indexOf(currentStudent);
                        if (index == 0) {
                            ++index;
                        } else {
                            if (index == size - 1) {
                                --index;
                            } else {
                                ++index;
                            }
                        }
                        Student tempStd = currentStudent;
                        currentStudent = students.get(index);
                        students.remove(tempStd);
                        EditText firstName = findViewById(R.id.first_name_info);
                        firstName.setText(currentStudent.getFirstName());
                        EditText secondName = findViewById(R.id.second_name_info);
                        secondName.setText(currentStudent.getSecondName());
                        ImageView photo = findViewById(R.id.big_photo);
                        photo.setImageResource(currentStudent.getPhoto());
                        CheckBox checkBox = findViewById(R.id.checkbox);
                        checkBox.setChecked(currentStudent.isMaleGender());
                    } else {
                        students.remove(currentStudent);
                        currentStudent = null;
                        EditText firstName = findViewById(R.id.first_name_info);
                        firstName.setText(DEFAULTFIRSTNAME);
                        EditText secondName = findViewById(R.id.second_name_info);
                        secondName.setText(DEFAULTSECONDNAME);
                        ImageView photo = findViewById(R.id.big_photo);
                        photo.setImageResource(0);
                        CheckBox checkBox = findViewById(R.id.checkbox);
                        checkBox.setChecked(false);
                    }
                }
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupSaveButton() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent != null) {
                    EditText firstNameInfo = findViewById(R.id.first_name_info);
                    EditText secondInfo = findViewById(R.id.second_name_info);
                    if (!firstNameInfo.getText().toString().contains(" ") && !firstNameInfo.getText().toString().equals("") &&
                    !secondInfo.getText().toString().equals("") && !secondInfo.getText().toString().contains(" ")) {
                        int index = students.indexOf(currentStudent);
                        currentStudent.setFirstName(firstNameInfo.getText().toString());
                        currentStudent.setSecondName(secondInfo.getText().toString());
                        CheckBox checkBox = findViewById(R.id.checkbox);
                        currentStudent.setMaleGender(checkBox.isChecked());
                        students.set(index, currentStudent);
                        studentAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Заполните корректно поля", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
