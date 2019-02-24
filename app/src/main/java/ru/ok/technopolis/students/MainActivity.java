package ru.ok.technopolis.students;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private List<Student> students;
    private StudentAdapter studentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        students = generateStudentList();
        setupRecyclerView();

    }

    private void setupRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recyclerview);
        studentAdapter = new StudentAdapter(students);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List <Student> generateStudentList ()
    {
        return new ArrayList<Student>()
        {
            {
                this.add(new Student("Greig", "Eisenhorn", true, R.drawable.male_1));
                this.add(new Student("Dmitry", "Poloz", true, R.drawable.male_2));
                this.add(new Student("Anna", "Kostushko", false, R.drawable.female_1));
            }
        };
    }

}
