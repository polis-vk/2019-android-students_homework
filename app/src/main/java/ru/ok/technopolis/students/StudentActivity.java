package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class StudentActivity extends AppCompatActivity implements View.OnClickListener
{
    private ImageView studentImageViewPhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private CheckBox genderCheckbox;
    private Student currentStudent;
    private List <Integer> femalePhoto;
    private List <Integer> malePhoto;


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
        setFreePhoto();
    }

    private void setFreePhoto()
    {
        femalePhoto = new ArrayList<>();
        malePhoto = new ArrayList<>();
        femalePhoto.add(R.drawable.female_1);
        femalePhoto.add(R.drawable.female_2);
        femalePhoto.add(R.drawable.female_3);
        malePhoto.add(R.drawable.male_1);
        malePhoto.add(R.drawable.male_2);
        malePhoto.add(R.drawable.male_3);
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
        int indexCurrentPhoto;
        currentStudent.setFirstName(studentFirstName.getText().toString());
        currentStudent.setSecondName(studentSecondName.getText().toString());
        currentStudent.setMaleGender(genderCheckbox.isChecked());
        if(currentStudent.getPhoto() == 0) {
            if (currentStudent.isMaleGender()) {
                indexCurrentPhoto = new Random().nextInt(malePhoto.size() - 1);
                currentStudent.setPhoto(malePhoto.get(indexCurrentPhoto));
                malePhoto.remove(indexCurrentPhoto);
            } else if (!currentStudent.isMaleGender()) {
                indexCurrentPhoto = new Random().nextInt(femalePhoto.size() - 1);
                currentStudent.setPhoto(femalePhoto.get(indexCurrentPhoto));
                femalePhoto.remove(indexCurrentPhoto);
            }
        }
        if(currentStudent.getFirstName().equals("") || currentStudent.getSecondName().equals(""))
        {
            return false;
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
                if(currentStudent.isMaleGender()){
                    malePhoto.add(currentStudent.getPhoto());
                }
                else {
                    femalePhoto.add(currentStudent.getPhoto());
                }
                setResult(3,new Intent().putExtra("StudentId",currentStudent.getId()));
                finish();
                break;
            }

        }
    }
}
