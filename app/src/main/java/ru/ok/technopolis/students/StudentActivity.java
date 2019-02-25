package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener
{
    private int studentProfilePhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private Button deleteStudent, saveStudent;
    private CheckBox genderCheckbox;
    private Student currentStudent;

    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        studentFirstName = findViewById(R.id.set_text__first_name_student);
        studentSecondName = findViewById(R.id.set_text__second_name_student);
        deleteStudent = findViewById(R.id.activity_student__delete_button);
        saveStudent = findViewById(R.id.activity_student__save_button);
        genderCheckbox = findViewById(R.id.checkBox__gender);
        setupStudent();
    }

    private void setupStudent()
    {
        String firstName, secondName;
        boolean isMale = false;
        firstName = studentFirstName.getText().toString();
        secondName = studentSecondName.getText().toString();
        if(genderCheckbox.isChecked()) {
            studentProfilePhoto = R.drawable.male_3;
            isMale = true;
        }
        else {
            studentProfilePhoto = R.drawable.female_2;
        }
        currentStudent = new Student(firstName,secondName,isMale,studentProfilePhoto);
        saveStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.activity_student__save_button)
        {
            Intent intent = new Intent();
            intent.putExtra("Student", currentStudent);
            setResult(1,intent);
            finish();
           // startActivity(new Intent(this, MainActivity.class).putExtra("Student", (Serializable) currentStudent));
        }
    }
}
