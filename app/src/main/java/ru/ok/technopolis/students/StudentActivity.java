package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private int studentProfilePhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private Button deleteStudent;
    private Button saveStudent;
    private CheckBox genderCheckbox;
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        studentFirstName = findViewById(R.id.set_text__first_name_student);
        studentSecondName = findViewById(R.id.set_text__second_name_student);
        deleteStudent = findViewById(R.id.activity_student__delete_button);
        saveStudent = findViewById(R.id.activity_student__save_button);
        genderCheckbox = findViewById(R.id.checkBox__gender);
        if (getIntent().getExtras() == null)
        {
            currentStudent = new Student();
        }
        else
            currentStudent = (Student) getIntent().getExtras().getSerializable("Student");
        saveStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
    }


    private void modifyStudent()
    {
        currentStudent.setFirstName(studentFirstName.getText().toString());
        currentStudent.setSecondName(studentSecondName.getText().toString());
        currentStudent.setMaleGender(genderCheckbox.isChecked());
        if(currentStudent.isMaleGender())
            currentStudent.setPhoto(R.drawable.male_3);
        else
            currentStudent.setPhoto(R.drawable.female_2);
    }




    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_student__save_button:
            {
                modifyStudent();
                setResult(1, new Intent().putExtra("Student", currentStudent));
                finish();
                break;
            }
            case  R.id.activity_student__delete_button:
            {
                setResult(3,new Intent().putExtra("StudentId",currentStudent.getId()));
                finish();
                break;
            }

        }
    }

}
