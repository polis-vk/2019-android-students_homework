package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final Random random = new Random();

    private final static Student EMPTY_STUDENT = new Student("","", false,0);

    private StudentAdapter studentAdapter;
    private StudentList studentList;
    private Student selectStudent;

    EditText firstName;
    EditText secondName;
    CheckBox maleGender;
    ImageView photo;
    int photoNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentList = new StudentList();
        selectStudent = EMPTY_STUDENT;

        firstName = findViewById(R.id.card__first_name);
        secondName = findViewById(R.id.card__second_name);
        maleGender = findViewById(R.id.card__male_gender);
        photo = findViewById(R.id.card__photo);
        photoNumber = random.nextInt(3);

        RecyclerView recyclerView = findViewById(R.id.list);
        studentAdapter = new StudentAdapter(studentList.getStudentList(), new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                findViewById(R.id.card).setVisibility(View.GONE);

                firstName.setText(student.getFirstName());
                secondName.setText(student.getSecondName());
                maleGender.setChecked(student.isMaleGender());
                photoNumber = student.getPhoto();
                photo.setImageResource(StudentList.getPhoto(photoNumber, student.isMaleGender()));
                selectStudent = student;

                findViewById(R.id.card).setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        setAddStudentButton();
        setSaveButton();
        setDeleteButton();
        setMaleGenderClick();
    }

    private void setAddStudentButton() {
        Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStudent = EMPTY_STUDENT;
                photoNumber = random.nextInt(3);
                photo.setImageResource(StudentList.getPhoto(photoNumber, EMPTY_STUDENT.isMaleGender()));
                findViewById(R.id.card).setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSaveButton() {
        Button button = findViewById(R.id.card__save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectStudent == EMPTY_STUDENT) {
                    studentList.add(firstName.getText().toString(), secondName.getText().toString(),
                            maleGender.isChecked(), photoNumber);
                } else {

                    Student newStudent = new Student(firstName.getText().toString(), secondName.getText().toString(),
                            maleGender.isChecked(), photoNumber);
                    studentList.change(selectStudent, newStudent);
                    studentList.delete(selectStudent);
                }
                findViewById(R.id.card).setVisibility(View.GONE);
                studentAdapter.notifyDataSetChanged();
                clearCard();
            }
        });
    }

    private void setDeleteButton() {
        Button button = findViewById(R.id.card__delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentList.delete(selectStudent);
                studentAdapter.notifyDataSetChanged();
                View view = findViewById(R.id.card);
                view.setVisibility(View.GONE);
                clearCard();
            }
        });
    }

    private void setMaleGenderClick() {
        final CheckBox checkBox = findViewById(R.id.card__male_gender);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setImageResource(StudentList.getPhoto(photoNumber, checkBox.isChecked()));
            }
        });
    }

    private void clearCard() {
        firstName.setText(EMPTY_STUDENT.getFirstName());
        secondName.setText(EMPTY_STUDENT.getSecondName());
        maleGender.setChecked(EMPTY_STUDENT.isMaleGender());
        photo.setImageResource(EMPTY_STUDENT.getPhoto());
    }

    @Override
    public void onBackPressed() {
        View view = findViewById(R.id.card);
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
