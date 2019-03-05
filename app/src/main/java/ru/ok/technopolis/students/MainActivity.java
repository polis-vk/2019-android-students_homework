package ru.ok.technopolis.students;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import ru.ok.technopolis.students.Repository.StudentDataRepository;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StudentDataRepository studentDataRepository = StudentDataRepository.Instance;
    private StudentAdapter studentAdapter;
    private int requestCode;


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
        studentAdapter = new StudentAdapter (studentDataRepository.studentsOnRepository(), this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student) {
        requestCode = 2;
        String dataResponse = "Student";
        startActivityForResult(new Intent(this, StudentActivity.class).putExtra(dataResponse, student), requestCode);
    }

    @Override
    public void onClick(View v) {
        requestCode = 1;
        startActivityForResult(new Intent(this, StudentActivity.class),requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) {
            return;
        }
        Student student;
        String dataRequest;
        switch (resultCode) {
            case 1:
                dataRequest = "NewStudent";
                student = (Student) data.getSerializableExtra(dataRequest);
                studentDataRepository.add(student);
                break;
            case 2:
                dataRequest = "ModifyStudent";
                student = (Student) data.getSerializableExtra(dataRequest);
                studentDataRepository.edit(student);
                break;
            case 3:
                dataRequest = "StudentForDelete";
                student = (Student) data.getSerializableExtra(dataRequest);
                studentDataRepository.delete(student);
                break;
        }
        studentAdapter.notifyDataSetChanged();
    }
}
