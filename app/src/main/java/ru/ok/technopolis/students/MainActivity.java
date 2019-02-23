package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private boolean edit = false;
    private String prevName;
    private  String prevSurname;
    private boolean prevMaleGender;
    private int prevPhoto;
    private int newPhoto;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = new ArrayList<>();
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupButtons();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student) {
        TextView name = findViewById(R.id.activity_main__enter_name);
        TextView surname = findViewById(R.id.activity_main__enter_surname);
        CheckBox male = findViewById(R.id.activity_main__cbox1);
        CheckBox female = findViewById(R.id.activity_main__cbox2);
        ImageView photo = findViewById(R.id.activity_main__image);
        name.setText(student.getFirstName());
        surname.setText(student.getSecondName());
        photo.setImageResource(student.getPhoto());
        if (student.isMaleGender()) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        edit = true;
        prevName = student.getFirstName();
        prevSurname = student.getSecondName();
//        prevMaleGender = student.isMaleGender();
        prevPhoto = student.getPhoto();
    }

    private void setupButtons() {
        Button addButton = findViewById(R.id.activity_main__button_add);
        addButton.setOnClickListener(v -> onAddClick());
        Button deleteButton = findViewById(R.id.activity_main__button_delete);
        deleteButton.setOnClickListener(v -> onDeleteClick());
        Button saveButton = findViewById(R.id.activity_main__button_save);
        saveButton.setOnClickListener(v -> onSaveClick());
        CheckBox male = findViewById(R.id.activity_main__cbox1);
        male.setOnClickListener(v -> setMalePhoto());
        CheckBox female = findViewById(R.id.activity_main__cbox2);
        female.setOnClickListener(v -> setFemalePhoto());
    }

    private void onAddClick() {
        TextView name = findViewById(R.id.activity_main__enter_name);
        TextView surname = findViewById(R.id.activity_main__enter_surname);
        ImageView photo = findViewById(R.id.activity_main__image);
        name.setText("Name");
        surname.setText("Surname");
        photo.setImageResource(getFemalePhoto());
        CheckBox male = findViewById(R.id.activity_main__cbox1);
        CheckBox female = findViewById(R.id.activity_main__cbox2);
        male.setChecked(false);
        female.setChecked(false);
        edit = false;
    }

    private int getMalePhoto(){
        int resource;
        switch (random.nextInt(3) + 1) {
            case 1:
                resource = R.drawable.male_1;
                break;
            case 2:
                resource = R.drawable.male_2;
                break;
            case 3:
                resource = R.drawable.male_3;
                break;
            default: resource = R.drawable.male_1;
                break;
        }
        newPhoto = resource;
        return resource;
    }

    private void setMalePhoto(){
        ImageView photo = findViewById(R.id.activity_main__image);
        CheckBox male = findViewById(R.id.activity_main__cbox1);
        if (male.isChecked()) {
            photo.setImageResource(getMalePhoto());
        }
    }

    private int getFemalePhoto(){
        int resource;
        switch (random.nextInt(3) + 1) {
            case 1:
                resource = R.drawable.female_1;
                break;
            case 2:
                resource = R.drawable.female_2;
                break;
            case 3:
                resource = R.drawable.female_3;
                break;
            default: resource = R.drawable.female_1;
                break;
        }
        newPhoto = resource;
        return resource;
    }

    private void setFemalePhoto(){
        ImageView photo = findViewById(R.id.activity_main__image);
        CheckBox female = findViewById(R.id.activity_main__cbox2);
        if (female.isChecked()) {
            photo.setImageResource(getFemalePhoto());
        }
    }

    private void onDeleteClick() {
        if (students.isEmpty()) return;
        TextView name = findViewById(R.id.activity_main__enter_name);
        String stringName = name.getText().toString();
        TextView surname = findViewById(R.id.activity_main__enter_surname);
        String stringSurname = surname.getText().toString();
        for (Student student : students) {
            if (student.getSecondName().equals(stringSurname) && student.getFirstName().equals(stringName)) {
                students.remove(student);
                studentAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void onSaveClick() {
        TextView name = findViewById(R.id.activity_main__enter_name);
        String stringName = name.getText().toString();
        TextView surname = findViewById(R.id.activity_main__enter_surname);
        String stringSurname = surname.getText().toString();
        ImageView photo = findViewById(R.id.activity_main__image);
        CheckBox male = findViewById(R.id.activity_main__cbox1);
        CheckBox female = findViewById(R.id.activity_main__cbox2);
        boolean maleGender = false;
        if (stringName.equals("Name")) {
            Toast.makeText(MainActivity.this, "enter name", LENGTH_SHORT).show();
        } else {
            if (stringSurname.equals("Surname")) {
                Toast.makeText(MainActivity.this, "enter surname", LENGTH_SHORT).show();
            } else {
                if (male.isChecked() && female.isChecked()) {
                    Toast.makeText(MainActivity.this, "choose only one gender", LENGTH_SHORT).show();
                } else {
                    if (!male.isChecked() && !female.isChecked()){
                        Toast.makeText(MainActivity.this, "choose gender", LENGTH_SHORT).show();
                    } else {
                        if (male.isChecked()) {
                            maleGender = true;
                        }
                        if (edit) {
                            for (Student student : students) {
                                if (student.getSecondName().equals(prevSurname) && student.getFirstName().equals(prevName)) {
                                    students.remove(student);
                                    break;
                                }
                            }
                            students.add(new Student(stringName, stringSurname, maleGender, prevPhoto));
                        } else {
                            students.add(new Student(stringName, stringSurname, maleGender, newPhoto));
                        }
                        edit = false;
                        name.setText("Name");
                        surname.setText("Surname");
                        male.setChecked(false);
                        female.setChecked(false);
                        photo.setImageResource(getFemalePhoto());
                        studentAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }

}
