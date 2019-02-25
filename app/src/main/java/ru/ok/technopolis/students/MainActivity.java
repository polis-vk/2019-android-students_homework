package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    StudentAdapter studentAdapter;

    private List<Student> students;

    private int currentStudent = -1;

    private int lastId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__student_list);

        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(students, student -> {
            currentStudent = students.indexOf(student);
            updateStudentInfo();
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setupButtons();
    }

    private void setupButtons() {
        Button addStudent = findViewById(R.id.activity_main__add_student_btn);
        addStudent.setOnClickListener(v -> onAddStudentClick());

        Button removeStudent = findViewById(R.id.activity_main__remove_student_btn);
        removeStudent.setOnClickListener(v -> onRemoveStudentClick());

        Button saveStudent = findViewById(R.id.activity_main__save_student_btn);
        saveStudent.setOnClickListener(v -> onSaveStudentClick());
    }

    private void onAddStudentClick() {
        students.add(getStudentStub(++lastId));
        currentStudent = students.size() - 1;
        studentAdapter.notifyDataSetChanged();
        updateStudentInfo();
    }

    private void onSaveStudentClick() {
        if (currentStudent >= 0) {
            Student student = students.get(currentStudent);
            Student userData = getStudentData();
            student.setFirstName(userData.getFirstName());
            student.setSecondName(userData.getSecondName());
            student.setMaleGender(userData.isMaleGender());
            student.setPhoto(userData.getPhoto());

            studentAdapter.notifyDataSetChanged();
            updateStudentInfo();
        }
    }

    private void onRemoveStudentClick() {
        if (currentStudent >= 0) {
            students.remove(currentStudent);
            currentStudent--;
            studentAdapter.notifyDataSetChanged();
            updateStudentInfo();
        }
    }

    private void updateStudentInfo() {
        ImageView studentPhoto = findViewById(R.id.activity_main__photo);
        EditText name = findViewById(R.id.activity_main__name);
        EditText surname = findViewById(R.id.activity_main__surname);
        CheckBox gender = findViewById(R.id.activity_main__gender_checkbox);

        Student student = currentStudent >= 0 ? students.get(currentStudent) : getStudentStub(-1);
        studentPhoto.setImageResource(student.getPhoto());
        name.setText(student.getFirstName());
        surname.setText(student.getSecondName());
        gender.setChecked(student.isMaleGender());
    }

    private Student getStudentStub(int id) {
        Student student = new Student(
                getString(R.string.name),
                getString(R.string.surname),
                true,
                R.color.colorAccent
        );
        student.setId(id);
        return student;
    }

    private Student getStudentData() {
        EditText name = findViewById(R.id.activity_main__name);
        EditText surname = findViewById(R.id.activity_main__surname);
        CheckBox gender = findViewById(R.id.activity_main__gender_checkbox);
        String sname = name.getText().toString().replaceAll(" +", "");
        int photo = gender.isChecked() ? getMalePhoto(sname) : getFemalePhoto(sname);
        return new Student(
                sname,
                surname.getText().toString().replaceAll(" +", ""),
                gender.isChecked(),
                photo
        );
    }

    private int getMalePhoto(String name) {
        int hash = name.hashCode() % 10;
        if (hash > 6) return R.drawable.male_1;
        else if (hash > 3) return R.drawable.male_2;
        else return R.drawable.male_3;
    }

    private int getFemalePhoto(String name) {
        int hash = name.hashCode() % 10;
        if (hash > 6) return R.drawable.female_1;
        else if (hash > 3) return R.drawable.female_2;
        else return R.drawable.female_3;
    }

}
