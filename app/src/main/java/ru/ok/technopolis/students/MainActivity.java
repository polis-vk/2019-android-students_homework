package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinkedList<Student> studentList = new LinkedList<>();
        final StudentCreator studentCreator = new StudentCreator(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_listOfStudent);

        StudentAdapter studentAdapter = new StudentAdapter(studentList, student -> studentCreator.displayStudent(student));

        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        studentCreator.setAddListener(student -> {
            studentList.add(student);
            studentAdapter.notifyDataSetChanged();
        });
        studentCreator.setDeleteListener(student -> {
            studentList.remove(student);
            studentAdapter.notifyDataSetChanged();
        });
        studentCreator.setSaveListener((student) -> {
            studentAdapter.notifyDataSetChanged();
        });
    }


    public List<Student> generateExample() {
        LinkedList<Student> students = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student("Name " + i, "Surname " + i, Student.Gender.FEMALE, R.drawable.female_1);
            students.add(student);
        }
        return students;
    }

}
