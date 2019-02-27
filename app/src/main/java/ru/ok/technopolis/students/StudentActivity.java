package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.NoSuchElementException;
import ru.ok.technopolis.students.Repository.FemalePhotoRepository;
import ru.ok.technopolis.students.Repository.MalePhotoRepository;
import ru.ok.technopolis.students.Repository.PhotoRepository;
import ru.ok.technopolis.students.Repository.StudentDataRepository;
import ru.ok.technopolis.students.Repository.StudentRepository;


public class StudentActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView studentImageViewPhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private CheckBox genderCheckbox;
    private Student currentStudent;
    private StudentRepository studentRepository = StudentDataRepository.getInstance();

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
        }
        else
            currentStudent = new Student();
        saveStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
    }



    private void setStudent()
    {
        studentFirstName.setText(currentStudent.getFirstName());
        studentSecondName.setText(currentStudent.getSecondName());
        if(currentStudent.isMaleGender())
            genderCheckbox.isChecked();
        studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
    }


    private boolean modifyStudent()
    {

        if(studentSecondName.getText().toString().equals("") || studentSecondName.getText().toString().equals(""))
        {
            return false;
        }
        if(studentRepository.isAlreadyInRepository(currentStudent)){
            currentStudent.setMaleGender(genderCheckbox.isChecked());
            currentStudent.setFirstName(studentFirstName.getText().toString());
            currentStudent.setSecondName(studentSecondName.getText().toString());
            return true;
        }
        if(currentStudent.getPhoto() == 0) {
            try {
                PhotoRepository photoRepository;
                if (currentStudent.isMaleGender()) {
                    photoRepository = MalePhotoRepository.getInstance();
                    currentStudent.setPhoto(photoRepository.getPhotoInRepository());

                } else {
                    photoRepository = FemalePhotoRepository.getInstance();
                    currentStudent.setPhoto(photoRepository.getPhotoInRepository());
                }
            }
            catch (NoSuchElementException e) {
                Toast.makeText(StudentActivity.this, "No Photo in DataBase", Toast.LENGTH_SHORT).show();
                return false;
            }
            currentStudent.setMaleGender(genderCheckbox.isChecked());
            currentStudent.setFirstName(studentFirstName.getText().toString());
            currentStudent.setSecondName(studentSecondName.getText().toString());
        }
        return true;
    }




    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_student__save_button:
            {
                if(modifyStudent()){
                    setResult(1, new Intent().putExtra("Student", currentStudent));
                    studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
                }
                finish();
                break;
            }
            case  R.id.activity_student__delete_button:
            {
                setResult(3,new Intent().putExtra("StudentForDelete",currentStudent));
                finish();
                break;
            }
        }
    }
}
