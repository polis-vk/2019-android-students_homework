package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final List<Student> students = new ArrayList<>();
    private Student currentStudent;
    private EditText firstNameView;
    private String firstName;
    private EditText secondNameView;
    private String secondName;
    private Spinner genderView;
    private ArrayAdapter genderAdapter;
    private String gender;
    private ImageView photoView;
    private int photoResId = 0;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test data
        students.add(new Student("Lyla", "Foy", "Андрогин", R.drawable.female_1));
        students.add(new Student("Evgeny", "Sobko", "Бигендер", R.drawable.male_2));
        students.add(new Student("Habib", "Nurmagomedov", "Дрэг", R.drawable.male_1));

        firstNameView = findViewById(R.id.activity_main_student_first_name);
        secondNameView = findViewById(R.id.activity_main_student_second_name);
        genderView = findViewById(R.id.activity_main_student_gender);
        genderAdapter = (ArrayAdapter) genderView.getAdapter();
        photoView = findViewById(R.id.activity_main_student_photo);

        findViewById(R.id.activity_main_button_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent != null) {
                    students.remove(currentStudent);
                    studentAdapter.notifyDataSetChanged();
                }
                setCurrentStudent(null);
            }
        });

        findViewById(R.id.activity_main_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent == null) {
                    saveNewStudent();
                } else {
                    saveStudent();
                }
            }
        });

        findViewById(R.id.activity_main_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        studentAdapter = new StudentAdapter(students, new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                setCurrentStudent(student);
            }
        });
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getInputData() {
        firstName = firstNameView.getText().toString().trim();
        secondName = secondNameView.getText().toString().trim();
        gender = (String) genderView.getSelectedItem();
    }

    private void setInputData() {
        firstNameView.setText(firstName);
        secondNameView.setText(secondName);
        genderView.setSelection(genderAdapter.getPosition(gender));
        photoView.setImageResource(photoResId);
    }

    private boolean isStudentProfileValid() {
        if (firstName.isEmpty() || secondName.isEmpty()) return false;
        return true;
    }

    private void setCurrentStudent(Student student) {
        if (student == null) {
            firstNameView.setText("");
            secondNameView.setText("");
            genderView.setSelection(0);
            Random random = new Random();
            int randomDrawable = 0;
            switch (random.nextInt(6)) {
                case 0: {
                    randomDrawable = R.drawable.female_1;
                    break;
                }
                case 1: {
                    randomDrawable = R.drawable.female_2;
                    break;
                }
                case 2: {
                    randomDrawable = R.drawable.female_3;
                    break;
                }
                case 3: {
                    randomDrawable = R.drawable.male_1;
                    break;
                }
                case 4: {
                    randomDrawable = R.drawable.male_2;
                    break;
                }
                case 5: {
                    randomDrawable = R.drawable.male_3;
                    break;
                }
            }
            photoResId = randomDrawable;
            photoView.setImageResource(randomDrawable);
            currentStudent = null;
        } else {
            firstName = student.getFirstName();
            secondName = student.getSecondName();
            gender = student.getGender();
            photoResId = student.getPhoto();
            setInputData();
            currentStudent = student;
        }
    }

    private void addStudent() {
        setCurrentStudent(null);
        studentAdapter.setChosenStudent(null);
    }

    private void saveStudent() {
        getInputData();
        if (isStudentProfileValid()) {
            currentStudent.setFirstName(firstName);
            currentStudent.setSecondName(secondName);
            currentStudent.setGender(gender);
            currentStudent.setPhoto(photoResId);
            studentAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, R.string.invalid_profile_toast, Toast.LENGTH_LONG).show();
        }
    }

    private void saveNewStudent() {
        getInputData();
        if (isStudentProfileValid()) {
            currentStudent = new Student(firstName, secondName, gender, photoResId);
            students.add(currentStudent);
            studentAdapter.setChosenStudent(currentStudent);
            studentAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, R.string.invalid_profile_toast, Toast.LENGTH_LONG).show();
        }
    }
}
