package ru.ok.technopolis.students;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;


import java.util.List;

import ru.ok.technopolis.students.Repository.FemalePhotoRepository;
import ru.ok.technopolis.students.Repository.MalePhotoRepository;
import ru.ok.technopolis.students.Repository.PhotoRepository;
import ru.ok.technopolis.students.Repository.StudentDataRepository;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StudentDataRepository studentDataRepository = StudentDataRepository.getInstance();
    private List<Student> students = studentDataRepository.studentsOnRepository();
    private StudentAdapter studentAdapter;
    private MalePhotoRepository malePhotoRepository = MalePhotoRepository.getInstance();
    private FemalePhotoRepository femalePhotoRepository = FemalePhotoRepository.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupAddButton();
    }




    private void setupAddButton()
    {
        FloatingActionButton addButton = findViewById(R.id.activity_main__add_button);
        addButton.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recyclerview);
        studentAdapter = new StudentAdapter (students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student)
    {
        startActivityForResult(new Intent(this, StudentActivity.class).putExtra("Student", student), 2);
    }

    @Override
    public void onClick(View v)
    {
        startActivityForResult(new Intent(this, StudentActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Student student;
        switch (resultCode)
        {
            case 1:
                student = (Student) data.getSerializableExtra("Student");
                if(studentDataRepository.add(student)){
                    students.add(student);
                }
                break;
            case 3:
                student = (Student) data.getSerializableExtra("StudentForDelete");
                if(studentDataRepository.delete(student)){
                    students.remove(student);
                }
                break;
        }
        studentAdapter.notifyDataSetChanged();
    }
}
