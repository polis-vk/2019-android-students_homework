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


public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView studentImageViewPhoto;
    private EditText studentFirstName;
    private EditText studentSecondName;
    private CheckBox genderCheckbox;
    private Student currentStudent;
    private PhotoRepository photoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        studentFirstName = findViewById(R.id.set_text__first_name_student);
        studentSecondName = findViewById(R.id.set_text__second_name_student);
        Button deleteStudent = findViewById(R.id.activity_student__delete_button);
        Button saveStudent = findViewById(R.id.activity_student__save_button);
        genderCheckbox = findViewById(R.id.checkBox__gender);
        studentImageViewPhoto = findViewById(R.id.student_activity__photo);
        if (getIntent().getExtras() != null){
            currentStudent = (Student) getIntent().getExtras().getSerializable("Student");
            if(currentStudent.isMaleGender()){
                photoRepository = MalePhotoRepository.getInstance();
            }
            else{
                photoRepository = FemalePhotoRepository.getInstance();
            }
            setStudent();
        }
        else {
            currentStudent = new Student();
            deleteStudent.setVisibility(View.GONE);
        }
        saveStudent.setOnClickListener(this);
        deleteStudent.setOnClickListener(this);
    }



    private void setStudent(){
        studentFirstName.setText(currentStudent.getFirstName());
        studentSecondName.setText(currentStudent.getSecondName());
        if(currentStudent.isMaleGender())
            genderCheckbox.isChecked();
        studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
    }


    private boolean createStudent(){
        try {
            if (genderCheckbox.isChecked()) {
                photoRepository = MalePhotoRepository.getInstance();
            }
            else {
                photoRepository = FemalePhotoRepository.getInstance();
            }
            currentStudent.setPhoto(photoRepository.getPhotoInRepository());
        }
        catch (NoSuchElementException e){
            Toast.makeText(StudentActivity.this, "No Photo in DataBase", Toast.LENGTH_LONG).show();
            return false;
        }
        setInitials();
        return true;
    }

    private boolean modifyStudent(){
        if(currentStudent.getPhoto() == 0){
            return false;
        }
        setInitials();
        return true;
    }

    private void setInitials(){
        currentStudent.setMaleGender(genderCheckbox.isChecked());
        currentStudent.setFirstName(studentFirstName.getText().toString());
        currentStudent.setSecondName(studentSecondName.getText().toString());
    }


    @Override
    public void onClick(View v){
        int resultCode;
        String dataResponse;
        switch (v.getId()){
            case R.id.activity_student__save_button: {
                if(modifyStudent()) {
                    if(studentSecondName.getText().toString().equals("") && studentSecondName.getText().toString().equals("")) {
                        return;
                    }
                    resultCode = 2;
                    dataResponse = "ModifyStudent";
                    setResult(resultCode, new Intent().putExtra(dataResponse, currentStudent));
                    studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
                }
                else if(createStudent()){
                    if(studentSecondName.getText().toString().equals("") || studentSecondName.getText().toString().equals("")) {
                        Toast.makeText(StudentActivity.this, "Please, fill all the fields", Toast.LENGTH_LONG).show();
                        return;
                    }
                    resultCode = 1;
                    dataResponse = "NewStudent";
                    setResult(resultCode, new Intent().putExtra(dataResponse, currentStudent));
                    studentImageViewPhoto.setImageResource(currentStudent.getPhoto());
                }
                finish();
                break;
            }
            case  R.id.activity_student__delete_button:{
                resultCode = 3;
                dataResponse = "StudentForDelete";
                setResult(resultCode,new Intent().putExtra(dataResponse,currentStudent));
                photoRepository.putPhotoInRepository(currentStudent.getPhoto());
                finish();
                break;
            }
        }
    }
}
