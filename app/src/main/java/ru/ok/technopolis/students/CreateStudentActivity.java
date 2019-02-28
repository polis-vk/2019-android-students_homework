package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CreateStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstNameEditText;
    private EditText secondNameEditText;
    private CheckBox genderCheckBox;
    private ImageView imageView;
    private int avatarId;
    private Student student;
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        Log.d(TAG,"CreateStudentActivity Create");

        firstNameEditText = findViewById(R.id.activity_create_student__first_name);
        secondNameEditText = findViewById(R.id.activity_create_student__second_name);
        genderCheckBox = findViewById(R.id.activity_create_student__gender_button);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setDefaultView();
        } else {
            student = (Student) extras.get("student");
            setStudentView(student);
        }



        View btnSave = findViewById(R.id.activity_create_student__save_button);
        btnSave.setOnClickListener(this);

        View btnDelete = findViewById(R.id.activity_create_student__delete_button);
        btnDelete.setOnClickListener(this);
    }

    private void setStudentView(Student student) {
        firstNameEditText.setText(student.getFirstName(), TextView.BufferType.EDITABLE);
        secondNameEditText.setText(student.getSecondName(), TextView.BufferType.EDITABLE);
        genderCheckBox.setChecked(student.isMaleGender());
        /*
            ????
         */
        //imageView.setImageResource(student.getPhoto());
    }

    private void setDefaultView() {
        imageView = findViewById(R.id.activity_create_student__avatar);
        imageView.setImageResource(generateRandomAvatar(false));
    }

    private int generateRandomAvatar(boolean gender) {
        Random random = new Random();
        int i = random.nextInt(3) + 1;
        switch (i) {
            case 1:
                avatarId = gender ? R.drawable.male_1 : R.drawable.female_1;
                break;
            case 2:
                avatarId = gender ? R.drawable.male_2 : R.drawable.female_2;
                break;
            case 3:
                avatarId = gender ? R.drawable.male_2 : R.drawable.female_2;
                break;
            default:
                avatarId = R.drawable.male_1;
                break;
        }
        return avatarId;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_create_student__save_button) {
            onSaveBtnClicked();
        } else if (v.getId() == R.id.activity_create_student__delete_button) {
            onDeleteBtnClicked();
        }
    }

    private void onDeleteBtnClicked() {

    }

    private void onSaveBtnClicked() {
        Student student;

        String firstName = firstNameEditText.getText().toString();
        String secondName = secondNameEditText.getText().toString();
        boolean gender = genderCheckBox.isChecked();

        MainActivity.students.add(new Student(firstName,secondName,gender,avatarId));

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"CreateStudentActivity Restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "CreateStudentActivity Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"CreateStudentActivity Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "CreateStudentActivity Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "CreateStudentActivity Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "CreateStudentActivity Destroy");
    }
}
