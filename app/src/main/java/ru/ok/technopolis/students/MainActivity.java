package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private Student student;
    private StudentAdapter studentAdapter;
    private TextView surnameEdit;
    private TextView nameEdit;
    private Button saveButton;
    private Button deleteButton;
    private Button addButton;
    private CheckBox male;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = generateStudentList();
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__student);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        addButton = findViewById(R.id.add_student);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);
        surnameEdit =  findViewById(R.id.activity_surname_student);
        nameEdit =  findViewById(R.id.activity_name_student);
        male = findViewById(R.id.men_student);
        photo = findViewById(R.id.photo_student);
        addButton();
        saveButton();
        deleteButton();
    }

    private void saveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (student == null) {
                    Toast.makeText(MainActivity.this, "Необходимо заполнить все пропуски и нажать \"добавить студента\"", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(student.getSecondName().equals(surnameEdit.getText().toString()) && student.getFirstName().equals(nameEdit.getText().toString()))) {
                    student.setSecondName(surnameEdit.getText().toString()+" ");
                    student.setFirstName(nameEdit.getText().toString());
                }
                if (male.isChecked()) {
                    student.setMaleGender(true);
                } else {
                    student.setMaleGender(false);
                }
                clear();
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void clear() {
        nameEdit.setText("");
        nameEdit.setHint(R.string.name_student);
        surnameEdit.setText("");
        surnameEdit.setHint(R.string.surname_student);
        male.setChecked(false);
        photo.setImageResource(R.drawable.ic_launcher_background);
    }

    private void deleteButton() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student == null) {
                    Toast.makeText(MainActivity.this, "Выберите студента", Toast.LENGTH_SHORT).show();
                    return;
                }
                students.remove(student);
                clear();
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addButton() {
        addButton.setOnClickListener(v -> onAddClick());
    }

    private void onStudentClick(Student student) {
        surnameEdit.setText(student.getSecondName());
        nameEdit.setText(student.getFirstName());
        if (student.isMaleGender()) {
            male.setChecked(true);
        } else {
            male.setChecked(false);
        }
        photo.setImageResource(student.getPhoto());
        this.student = student;
    }

    private void onAddClick() {
        surnameEdit =  findViewById(R.id.activity_surname_student);
        String surnameEditLocal =  surnameEdit.getText().toString();
        nameEdit =  findViewById(R.id.activity_name_student);
        String nameEdit_s =  nameEdit.getText().toString();
        if (surnameEdit.getText().toString()==null || nameEdit.getText().toString()==null || student==null) {
            Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean maleGender = true;
        if (male.isChecked()) {
            maleGender = true;
        } else {
            maleGender = false;
        }
        student = generateNewStudent(nameEdit_s, surnameEditLocal+" ", maleGender);
        students.add(student);
        clear();
        studentAdapter.notifyDataSetChanged();
    }

    private Student generateNewStudent(String firstName, String secondName, boolean maleGender) {
        if (maleGender) {
            student = new Student(firstName, secondName, maleGender, R.drawable.male_1);
        } else {
            student = new Student(firstName, secondName, maleGender, R.drawable.female_1);
        }
        studentAdapter.notifyDataSetChanged();
        return student;
    }

    private List<Student> generateStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ivan ", "Ivanov ", true, R.drawable.male_1));
        students.add(new Student("Denis ", "Akimov ", true,R.drawable.male_2));
        students.add(new Student("Alexa ", "Donova ", false, R.drawable.female_3));
        return students;
    }

}
