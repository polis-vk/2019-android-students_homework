package ru.ok.technopolis.students;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import ru.ok.technopolis.students.repository.StudentDataRepository;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StudentDataRepository studentDataRepository = StudentDataRepository.Instance;
    private StudentAdapter studentAdapter;
    private final String dataResponse = "Student";
    private final int ACTION_ON_STUDENT_CLICK = 2;
    private final int ACTION_CREATE_STUDENT_CLICK = 1;
    private final String dataRequestNewStudent = "NewStudent";
    private final String dataRequestModifyStudent = "ModifyStudent";
    private final String dataRequestDeleteStudent = "StudentForDelete";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupAddButton();
    }

    private void setupAddButton() {
        FloatingActionButton addButton = findViewById(R.id.activity_main__add_button);
        addButton.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recyclerview);
        studentAdapter = new StudentAdapter(studentDataRepository.studentsOnRepository(), this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student) {
        startActivityForResult(new Intent(this, StudentActivity.class).putExtra(dataResponse, student), ACTION_ON_STUDENT_CLICK);
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, StudentActivity.class), ACTION_CREATE_STUDENT_CLICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Student student;
        switch (resultCode) {
            case 1:
                student = (Student) data.getSerializableExtra(dataRequestNewStudent);
                studentDataRepository.add(student);
                break;
            case 2:
                student = (Student) data.getSerializableExtra(dataRequestModifyStudent);
                studentDataRepository.edit(student);
                break;
            case 3:
                student = (Student) data.getSerializableExtra(dataRequestDeleteStudent);
                studentDataRepository.delete(student);
                break;
        }
        studentAdapter.notifyDataSetChanged();
    }
}
