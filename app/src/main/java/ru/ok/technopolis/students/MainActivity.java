package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private StudentAdapter adapter;
    private ImageView photo;
    private int photoId;
    private EditText firstNameField;
    private EditText lastNameField;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.activity_main__list);
        adapter = new StudentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        photo = findViewById(R.id.activity_main__photo);
        firstNameField = findViewById(R.id.activity_main__firstName);
        lastNameField = findViewById(R.id.activity_main__lastName);
        radioGroup = findViewById(R.id.activity_main__radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                randomizePhoto();
            }
        });

        Button addButton = findViewById(R.id.activity_main__add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
                adapter.setActiveStudent(StudentAdapter.NO_STUDENT);
            }
        });

        Button saveButton = findViewById(R.id.activity_main__save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });

        Button deleteButton = findViewById(R.id.activity_main__delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });
    }

    private void deleteStudent() {
        adapter.deleteStudent();
    }

    private void saveStudent() {
        String firstNameStr = firstNameField.getText() == null ? "" : firstNameField.getText().toString();
        String lastNameStr = lastNameField.getText() == null ? "" : lastNameField.getText().toString();
        Boolean gender = null;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.activity_main__radio_male:
                gender = true;
                break;
            case R.id.activity_main__radio_woman:
                gender = false;
                break;
        }

        if (firstNameStr.isEmpty() || lastNameStr.isEmpty() || gender == null) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(
                firstNameStr,
                lastNameStr,
                gender,
                photoId
        );
        adapter.saveStudent(student);
    }

    private void randomizePhoto() {
        int[] imgMale = {R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
        int[] imgFemale = {R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};

        int i;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.activity_main__radio_male:
                i = new Random().nextInt(3);
                photo.setImageDrawable(getResources().getDrawable(imgMale[i]));
                photoId = imgMale[i];
                break;
            case R.id.activity_main__radio_woman:
                i = new Random().nextInt(3);
                photo.setImageDrawable(getResources().getDrawable(imgFemale[i]));
                photoId = imgFemale[i];
                break;
            default:
                photoId = imgMale[0];
                break;
        }
    }

    public void clearFields() {
        photo.setImageDrawable(null);
        firstNameField.setText("");
        lastNameField.setText("");
        radioGroup.clearCheck();
    }

    public void setActiveStudent(Student student) {
        photo.setImageDrawable(getResources().getDrawable(student.getPhoto()));
        firstNameField.setText(student.getFirstName());
        lastNameField.setText(student.getSecondName());
        if (student.isMale()) {
            radioGroup.check(R.id.activity_main__radio_male);
        } else {
            radioGroup.check(R.id.activity_main__radio_woman);
        }
    }

}
