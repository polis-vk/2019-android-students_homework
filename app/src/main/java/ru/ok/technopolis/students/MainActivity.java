package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private Button save;
    private Button delete;
    private Button add;
    private CheckBox male;
    private CheckBox female;


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
        add = findViewById(R.id.add_student);
        save = findViewById(R.id.save_button);
        delete = findViewById(R.id.delete_button);
        surnameEdit =  findViewById(R.id.activity_surname_student);
        nameEdit =  findViewById(R.id.activity_name_student);
        male = findViewById(R.id.men_student);
        female = findViewById(R.id.women_student);
        addButton();
        saveButton();
        deleteButton();
    }

    private void saveButton() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student == null) {
                    Toast.makeText(MainActivity.this, "Fill all gaps and click \"add student\"", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(student.getSecondName().equals(surnameEdit.getText().toString()) && student.getFirstName().equals(nameEdit.getText().toString()))) {
                    student.setSecondName(surnameEdit.getText().toString()+" ");
                    student.setFirstName(nameEdit.getText().toString());
                }
                if (male.isChecked() && female.isChecked() || !male.isChecked() && !female.isChecked()) {
                    Toast.makeText(MainActivity.this, "Choose gender", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (male.isChecked()) {
                        student.setMaleGender(true);
                    } else {
                        if (female.isChecked()) {
                            student.setMaleGender(false);
                        }
                    }
                }
                clean();
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void clean() {
        nameEdit.setText("Имя");
        surnameEdit.setText("Фамилия");
        male.setChecked(false);
        female.setChecked(false);

    }

    private void deleteButton() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student == null) {
                    Toast.makeText(MainActivity.this, "Choose student", Toast.LENGTH_SHORT).show();
                    return;
                }
                students.remove(student);
                clean();
                studentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addButton() {
        add.setOnClickListener(v -> onAddClick());
    }

    private void onStudentClick(Student student) {
        surnameEdit.setText(student.getSecondName());
        nameEdit.setText(student.getFirstName());
        if (student.isMaleGender()) {
            male.setChecked(true);
            female.setChecked(false);
        } else {
            male.setChecked(false);
            female.setChecked(true);
        }
        this.student = student;
    }

    private void onAddClick() {
        surnameEdit =  findViewById(R.id.activity_surname_student);
        String surnameEdit_s =  surnameEdit.getText().toString();
        nameEdit =  findViewById(R.id.activity_name_student);
        String nameEdit_s =  nameEdit.getText().toString();
        if (surnameEdit_s.equals("Фамилия") || nameEdit_s.equals(" Имя")) {
            Toast.makeText(MainActivity.this, "Fill all gaps", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean maleGender = true;
        if (male.isChecked() && female.isChecked() || !male.isChecked() && !female.isChecked()) {
            Toast.makeText(MainActivity.this, "Choose gender", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (male.isChecked()) {
                maleGender = true;
            } else {
                if (female.isChecked()) {
                    maleGender = false;
                }
            }
        }
        student = generateNewStudent(nameEdit_s, surnameEdit_s+" ", maleGender);
        students.add(student);
        clean();
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
        students.add(new Student("Ivan ",
                "Ivanov ", true,
                R.drawable.male_1));
        students.add(new Student("Denis ",
"Akimov ", true,              R.drawable.male_2));
        students.add(new Student("Alexa ",
                "Donova ", false,
                R.drawable.female_3));
        return students;
    }

}
