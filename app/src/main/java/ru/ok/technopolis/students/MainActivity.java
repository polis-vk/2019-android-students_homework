package ru.ok.technopolis.students;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Student> students;
    private StudentAdapter studentAdapter;
    private String[] image_names;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random random = new Random();
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);
        students = generateStudentsList();
        image_names = getResources().getStringArray(R.array.image_names);
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

//    .getResources().getIdentifier(arrayOfStrings[position], "drawable", mApplicationContext.getPackageName())

    public void startAddingActivity() {
        Intent intent = new Intent(this, AdditionActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String secondName = data.getStringExtra("secondName");
        String firstName = data.getStringExtra("firstName");
        int photo_id = random.nextInt(5);
        int id = (int) getResources().getIdentifier(image_names[photo_id], "drawable", String.valueOf(this));
        Student newStudent = new Student(firstName, secondName, data.getBooleanExtra("sex",false), id);
        students.add(newStudent);
        studentAdapter.notifyDataSetChanged();
    }

    private ArrayList<Student> generateStudentsList() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Иван", "Иванов", true, R.drawable.male_1));
        students.add(new Student("Петр", "Петров", true, R.drawable.male_2));
        students.add(new Student("Анастасия", "Курочкина", false, R.drawable.female_1));
        return students;
    }
}
