package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ru.ok.technopolis.students.controller.StudentActivityController;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView studentImageViewPhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private CheckBox genderCheckbox;
    private String firstName;
    private String secondName;
    private boolean isMale;
    private StudentActivityController studentActivityController = StudentActivityController.Instance;
    private final int CREATE_STUDENT_RESULT_CODE = 1;
    private final int MODIFY_STUDENT_RESULT_CODE = 2;
    private final int DELETE_STUDENT_RESULT_CODE = 3;
    private final String RESPONSE_CREATE_STUDENT = "NewStudent";
    private final String RESPONSE_DELETE_STUDENT = "StudentForDelete";
    private final String RESPONSE_MODIFY_STUDENT = "ModifyStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        studentFirstName = findViewById(R.id.set_text__first_name_student);
        studentSecondName = findViewById(R.id.set_text__second_name_student);
        Button deleteStudent = findViewById(R.id.activity_student__delete_button);
        Button saveStudent = findViewById(R.id.activity_student__save_button);
        genderCheckbox = findViewById(R.id.checkBox__gender);
        studentImageViewPhoto = findViewById(R.id.student_activity__photo);
        if (getIntent().getExtras() != null) {
            studentActivityController.setCurrentStudent((Student) getIntent().getExtras().getSerializable("Student"));
            studentFirstName.setText(studentActivityController.getCurrentStudent().getFirstName());
            studentSecondName.setText(studentActivityController.getCurrentStudent().getSecondName());
            if (studentActivityController.getCurrentStudent().isMaleGender())
                genderCheckbox.setChecked(true);
            studentImageViewPhoto.setImageResource(studentActivityController.getCurrentStudent().getPhoto());
            genderCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    studentActivityController.putPhotoInPhotoRepository(studentActivityController.getCurrentStudent());
                    if(isChecked) {
                        studentActivityController.createStudent(isMale);
                    }
                    else {
                        studentActivityController.createStudent(isMale);
                    }
                }
            });
        } else {
            studentActivityController.setCurrentStudent(new Student());
            deleteStudent.setVisibility(View.GONE);
        }
        saveStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
    }

    private void setFields() {
        firstName = studentFirstName.getText().toString();
        secondName = studentSecondName.getText().toString();
        isMale = genderCheckbox.isChecked();
    }

    @Override
    public void onClick(View v) {
        setFields();
        studentActivityController.setStudentInitials(firstName, secondName, isMale);
        Student student = studentActivityController.getCurrentStudent();
        switch (v.getId()) {
            case R.id.activity_student__save_button: {
                if (studentFirstName.getText().toString().equals("") || studentSecondName.getText().toString().equals("")) {
                    Toast.makeText(StudentActivity.this, "Please, fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (studentActivityController.modifyStudent()) {
                    setResult(MODIFY_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_MODIFY_STUDENT, student));
                    studentImageViewPhoto.setImageResource(student.getPhoto());
                } else if (studentActivityController.createStudent(isMale)) {
                    setResult(CREATE_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_CREATE_STUDENT, student));
                    studentImageViewPhoto.setImageResource(student.getPhoto());
                } else {
                    Toast.makeText(StudentActivity.this, "No Photo in DataBase", Toast.LENGTH_LONG).show();
                }
                finish();
                break;
            }
            case R.id.activity_student__delete_button: {
                setResult(DELETE_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_DELETE_STUDENT, student));
                studentActivityController.putPhotoInPhotoRepository(student);
                finish();
                break;
            }
        }
    }
}