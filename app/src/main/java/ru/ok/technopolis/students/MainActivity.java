package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;
    private Student currentStudent;
    private View currentView = null;
    private EditText name, surname;
    private CheckBox male;
    private ImageView avatar;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        students = generateStudentList();
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        name = findViewById(R.id.main_edit_text_name);
        surname = findViewById(R.id.main_edit_text_surname);
        male = findViewById(R.id.main_check_male);
        avatar = findViewById(R.id.main_image_avatar);

        cleanCard();
        addClickListener();
        saveClickListener();
        deleteClickListener();
    }

    private void addClickListener() {
        findViewById(R.id.main_button_add).setOnClickListener(v -> {
            currentStudent = null;
            currentView.setBackgroundResource(R.color.white);
            cleanCard();
        });
    }

    private void saveClickListener() {
        findViewById(R.id.main_button_save).setOnClickListener(v -> {
            if (name.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите имя", Toast.LENGTH_SHORT).show();
            } else if (surname.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите фамилию", Toast.LENGTH_SHORT).show();
            } else if (currentStudent == null) {
                students.add(new Student(name.getText().toString(), surname.getText().toString(),
                        male.isChecked(), randomAvatar(male.isChecked())));
                cleanCard();
            } else {
                currentStudent.setFirstName(name.getText().toString());
                currentStudent.setSecondName(surname.getText().toString());
                if (currentStudent.isMaleGender() != male.isChecked()) {
                    currentStudent.setMaleGender(male.isChecked());
                    currentStudent.setPhoto(randomAvatar(currentStudent.isMaleGender()));
                }
                cleanCard();
            }
            studentAdapter.notifyDataSetChanged();
            currentView.setBackgroundResource(R.color.white);
        });
    }

    private void deleteClickListener() {
        findViewById(R.id.main_button_delete).setOnClickListener(v -> {
            students.remove(currentStudent);
            studentAdapter.notifyDataSetChanged();
            currentView.setBackgroundResource(R.color.white);
            cleanCard();
        });
    }

    private int randomAvatar(boolean male) {
        switch (random.nextInt(2)) {
            case 0: {
                if (male)
                    return R.drawable.male_1;
                return R.drawable.female_1;
            }
            case 1: {
                if (male)
                    return R.drawable.male_2;
                return R.drawable.female_2;
            }
            default:
            case 2: {
                if (male)
                    return R.drawable.male_3;
                return R.drawable.female_3;
            }
        }
    }

    public void onStudentClick(View view, int i) {
        if (currentView != null)
            currentView.setBackgroundResource(R.color.white);
        currentView = view;
        currentView.setBackgroundResource(R.color.lightGray);
        currentStudent = students.get(i);
        setStudentCard();
    }

    private void cleanCard() {
        name.setText("");
        surname.setText("");
        male.setChecked(false);
        avatar.setImageResource(R.color.lightGray);
        currentStudent = null;
    }

    private void setStudentCard() {
        name.setText(currentStudent.getFirstName());
        surname.setText(currentStudent.getSecondName());
        male.setChecked(currentStudent.isMaleGender());
        avatar.setImageResource(currentStudent.getPhoto());
    }

    private List<Student> generateStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Иван", "Иванов", true, R.drawable.male_1));
        students.add(new Student("Пётр", "Петров", true, R.drawable.male_2));
        students.add(new Student("Анастасия", "Медведева", false, R.drawable.female_1));
        return students;
    }

}