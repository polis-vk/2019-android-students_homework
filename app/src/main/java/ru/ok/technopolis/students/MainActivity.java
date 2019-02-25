package ru.ok.technopolis.students;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        students = generateStudentList();
        setupRecyclerView();
        setupAddButton();
    }

    private void setupAddButton()
    {
        addButton = findViewById(R.id.activity_main__add_button);
        addButton.setOnClickListener(this);

    }

    private void setupRecyclerView()
    {
        recyclerView = findViewById(R.id.activity_main__recyclerview);
        studentAdapter = new StudentAdapter(students);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List <Student> generateStudentList()
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


    @Override
    public void onClick(View v)
    {
        startActivityForResult(new Intent(this, StudentActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Student currentStudent = (Student) data.getSerializableExtra("Student");
        students.add(currentStudent);
        studentAdapter.notifyDataSetChanged();
    }
}
