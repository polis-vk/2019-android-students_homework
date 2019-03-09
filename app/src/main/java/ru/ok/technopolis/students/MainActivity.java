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
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Student> students;
    private StudentAdapter studentAdapter;
    private String[] imageNames;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);

        imageNames = getResources().getStringArray(R.array.image_names);
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
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String secondName = data.getStringExtra("secondName");
            String firstName = data.getStringExtra("firstName");
            int pic_id = data.getIntExtra("photo", random.nextInt(6));
            Student newStudent = new Student(firstName, secondName, data.getBooleanExtra("sex", false), pic_id);
            students.add(newStudent);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            int op = data.getIntExtra("button_code", 1);
            int index = data.getIntExtra("index", -1);
            switch (op) {
                case 2:
                    if (students.isEmpty())
                        return;
                    //deleting student according to item index
                    if (index != -1)
                        students.remove(index);
                    else
                        Toast.makeText(this, "No such element", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    //getting possible to edit data
                    String secondName = data.getStringExtra("secondName");
                    String firstName = data.getStringExtra("firstName");
                    int pic_id = data.getIntExtra("photo", 1);
                    boolean isMale = data.getBooleanExtra("isMale", false);
                    Student student_to_change = students.get(index);
                    student_to_change.setSecondName(secondName);
                    student_to_change.setFirstName(firstName);
                    student_to_change.setIsMale(isMale);
                    student_to_change.setPhoto(pic_id);
                    students.set(index, student_to_change);
                    break;
            }
        }
        studentAdapter.notifyDataSetChanged();
    }

    private ArrayList<Student> generateStudentsList() {
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
