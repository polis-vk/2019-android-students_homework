package ru.ok.technopolis.students;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private List<Student> students;
    private StudentAdapter studentAdapter;
    private Random random;

    public static final String SECOND_NAME_EXTRA = "secondName";
    public static final String FIRST_NAME_EXTRA = "firstName";
    public static final String MALE_EXTRA = "isMale";
    public static final String PHOTO_EXTRA = "photo";
    public static final String STUDENT_INDEX_EXTRA = "studentIndex";
    public static final String BUTTON_CODE_EXTRA = "buttonCode";
    public static final int ADD_ACTIVITY = 1;
    public static final int INFO_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);

        students = generateStudentsList();
        studentAdapter = new StudentAdapter(this, students);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(studentAdapter);

        FloatingActionButton fab = findViewById(R.id.activity_main__fb_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddingActivity();
            }
        });
    }

    public void startAddingActivity() {
        Intent intent = new Intent(this, AdditionActivity.class);
        startActivityForResult(intent, ADD_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String secondName = data.getStringExtra(SECOND_NAME_EXTRA);
            String firstName = data.getStringExtra(FIRST_NAME_EXTRA);
            int pic_id = data.getIntExtra(PHOTO_EXTRA, random.nextInt(6));
            Student newStudent = new Student(firstName, secondName, data.getBooleanExtra(MALE_EXTRA, false), pic_id);
            students.add(newStudent);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            int op = data.getIntExtra(BUTTON_CODE_EXTRA, 1);
            int index = data.getIntExtra(STUDENT_INDEX_EXTRA, -1);
            switch (op) {
                case 2:
                    if (students.isEmpty()) {
                        return;
                    }
                    /*deleting student according to item index **/
                    if (index != -1) {
                        students.remove(index);
                    }
                    else {
                        Toast.makeText(this, "No such element", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    /*getting possible to edit data **/
                    String secondName = data.getStringExtra(SECOND_NAME_EXTRA);
                    String firstName = data.getStringExtra(FIRST_NAME_EXTRA);
                    int picID = data.getIntExtra(PHOTO_EXTRA, 1);
                    boolean isMale = data.getBooleanExtra(MALE_EXTRA, false);
                    Student studentToChange = students.get(index);
                    studentToChange.setSecondName(secondName);
                    studentToChange.setFirstName(firstName);
                    studentToChange.setIsMale(isMale);
                    studentToChange.setPhoto(picID);
                    students.set(index, studentToChange);
                    break;
            }
        }
        studentAdapter.notifyDataSetChanged();
    }

    private List<Student> generateStudentsList() {
        ArrayList<Student> students = new ArrayList<>();
        int id = getResources().getIdentifier("male_2", "drawable", getPackageName());
        students.add(new Student("Иван", "Иванов", true, id));

        id = getResources().getIdentifier("male_3", "drawable", getPackageName());
        students.add(new Student("Петр", "Петров", true, id));

        id = getResources().getIdentifier("female_1", "drawable", getPackageName());
        students.add(new Student("Анастасия", "Курочкина", false, id));
        return students;
    }
}
