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

import java.util.NoSuchElementException;

import ru.ok.technopolis.students.Repository.FemalePhotoRepository;
import ru.ok.technopolis.students.Repository.MalePhotoRepository;
import ru.ok.technopolis.students.Repository.PhotoRepository;


public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView studentImageViewPhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private CheckBox genderCheckbox;
    private Student currentStudent;
    private PhotoRepository photoRepository;
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
            currentStudent = (Student) getIntent().getExtras().getSerializable("Student");
            setStudent();
        } else {
            currentStudent = new Student();
            deleteStudent.setVisibility(View.GONE);
        }
        saveStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
    }


    private void setStudent() {
        studentFirstName.setText(currentStudent.getFirstName());
        studentSecondName.setText(currentStudent.getSecondName());
        if (currentStudent.isMaleGender())
            genderCheckbox.setChecked(true);
        studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
    }


    private boolean createStudent() {
        if (setPhoto()) {
            setInitials();
            return true;
        }
        return false;
    }


    private boolean setPhoto() {
        try {
            if (genderCheckbox.isChecked()) {
                photoRepository = MalePhotoRepository.Instance;
            } else {
                photoRepository = FemalePhotoRepository.Instance;
            }
            currentStudent.setPhoto(photoRepository.getPhotoInRepository());
        } catch (NoSuchElementException e) {
            Toast.makeText(StudentActivity.this, "No Photo in DataBase", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean modifyStudent() {
        if (currentStudent.getPhoto() == 0) {
            return false;
        }
        setInitials();
        return true;
    }

    private void setInitials() {
        currentStudent.setMaleGender(genderCheckbox.isChecked());
        currentStudent.setFirstName(studentFirstName.getText().toString());
        currentStudent.setSecondName(studentSecondName.getText().toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_student__save_button: {
                if (studentFirstName.getText().toString().equals("") || studentSecondName.getText().toString().equals("")) {
                    Toast.makeText(StudentActivity.this, "Please, fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (modifyStudent()) {
                    setResult(MODIFY_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_MODIFY_STUDENT, currentStudent));
                    studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
                } else if (createStudent()) {
                    setResult(CREATE_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_CREATE_STUDENT, currentStudent));
                    studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
                }
                finish();
                break;
            }
            case R.id.activity_student__delete_button: {
                setResult(DELETE_STUDENT_RESULT_CODE, new Intent().putExtra(RESPONSE_DELETE_STUDENT, currentStudent));
                photoRepository.putPhotoInRepository(currentStudent.getPhoto());
                finish();
                break;
            }
        }
    }
}