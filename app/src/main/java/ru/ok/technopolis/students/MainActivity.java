package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private Student student;
    private StudentAdapter studentAdapter;
    private EditText surnameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = generateStudentList();
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupButton();
        setupSaveButton();
        setupDeleteButton();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__student);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupSaveButton() {
       surnameEdit = (EditText) findViewById(R.id.surname_student);

       //student.setFirstName(s);

        /*surnameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //student.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
       // EditText nameEdit = findViewById(R.id.name_student);
       // student.setSecondName(surnameEdit.getText().toString());
        /*nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //student.setSecondName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((student.getFirstName()) == "Фамилия" && (student.getSecondName() == "Имя")) {
                    Toast.makeText(MainActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();

                }
            }
        });*/

    }

    private void setupDeleteButton() {
        Button delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                students.remove(student);
            }

        });

    }

    private void setupButton() {
        Button floatingActionButton = findViewById(R.id.add_student);
        floatingActionButton.setOnClickListener(v -> onAddClick());
    }

    private void onStudentClick(Student student) {
        Toast.makeText(MainActivity.this, student.getFirstName(), Toast.LENGTH_SHORT).show();
    }

    private void onAddClick() {
        student = generateNewStudent();
        students.add(student);
        studentAdapter.notifyDataSetChanged();

    }

    private Student generateNewStudent() {
        student = new Student("Фамилия", "Имя", true, R.drawable.female_1);
        studentAdapter.notifyDataSetChanged();
        return student;
    }

    private List<Student> generateStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ivan",
                "Ivanov", true,
                R.drawable.male_1));
        students.add(new Student("Denis",
"Akimov", true,              R.drawable.male_2));
        students.add(new Student("Alex",
                "Doriv", true,
                R.drawable.male_3));

        return students;
    }

}
