package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Student> studentList = new ArrayList<>();
        final IStudentUI studentUI = new StudentUI(this);
        final StudentManager studentManager = new StudentManager(studentUI);

        RecyclerView recyclerView = findViewById(R.id.recyclerView__list_Of_Student);

        StudentAdapter studentAdapter = new StudentAdapter(studentList, student -> {
            studentUI.displayStudent(student);
            studentManager.setCurrentStudent(student);
        });

        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        studentManager.setListenerForAddBtn(student -> {
            studentList.add(student);
            studentAdapter.notifyDataSetChanged();
        });

        studentManager.setListenerForDeleteBtn(student -> {
            studentList.remove(student);
            studentAdapter.notifyDataSetChanged();
            studentAdapter.refreshSelectedPos();
        });
        studentManager.setListenerForSaveBtn((student) -> {
            studentAdapter.notifyDataSetChanged();
        });

        studentManager.setListenerForRadioBtn(student -> {
        });

    }

    public List<Student> generateExample() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student("Name " + i, "Surname " + i, Student.Gender.FEMALE, R.drawable.female_1);
            students.add(student);
        }
        return students;
    }

}