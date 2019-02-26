package ru.ok.technopolis.students;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Student> students;
    private StudentAdapter studentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        students = new ArrayList<>();
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
        studentAdapter = new StudentAdapter(this, students);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
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
        switch (resultCode)
        {
            case 1:
                Student student = (Student) data.getSerializableExtra("Student");
                students.add(student);
                break;
            case 3:
                UUID id = (UUID) data.getSerializableExtra("StudentId");
                for(int i = 0; i < students.size(); i++)
                {
                    if(students.get(i).getId().equals(id))
                    {
                        students.remove(i);
                    }
                }
                break;
        }
        studentAdapter.notifyDataSetChanged();
    }
}
