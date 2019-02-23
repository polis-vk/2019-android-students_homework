package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);
        ArrayList<Student> students = generateStudentsList();
        for(Student n : students)
            Log.d("asdasdasd", "onCreate: " + n.secondName);
        StudentAdapter studentAdapter = new StudentAdapter(this,students);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(studentAdapter);
    }

    private ArrayList<Student> generateStudentsList() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Иван", "Иванов", true, R.drawable.male_1));
        students.add(new Student("Петр", "Петров", true, R.drawable.male_2));
        students.add(new Student("Анастасия", "Курочкина", false, R.drawable.female_1));
        return students;
    }
}
