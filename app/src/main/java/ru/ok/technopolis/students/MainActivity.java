package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(BuildConfig.LOG){
            Log.d(BuildConfig.LOG_TAG, "Im work!!");
        }
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
        if(BuildConfig.LOG){
            Log.d(BuildConfig.LOG_TAG, "Im still work!");
        }
        StudentAdapter studentAdapter =  new StudentAdapter(generateStudentList());
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private List<Student> generateStudentList(){
        List<Student> students = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            Student student = new Student("Mark", "Arckron", true, 4);
            students.add(student);
        }
        return students;
    }

}
