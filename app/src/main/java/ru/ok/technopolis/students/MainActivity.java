package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private StudentsAdapter studentsAdapter;
    private Student currentStudent;
    private EditText firstNameView;
    private EditText lastNameView;
    private CheckBox genderView;
    private CircleImageView photoView;
    private Button buttonAdd;
    private Button buttonSave;
    private Button buttonDelete;
    final private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main_students_list);
        students = generateStudentList();
        studentsAdapter = new StudentsAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentsAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        firstNameView = findViewById(R.id.activity_main_student_first_name);
        lastNameView = findViewById(R.id.activity_main_student_last_name);
        genderView = findViewById(R.id.activity_main_student_gender);
        photoView = findViewById(R.id.activity_main_student_photo);
        buttonAdd = findViewById(R.id.activity_main_button_add);
        buttonSave = findViewById(R.id.activity_main_button_save);
        buttonDelete = findViewById(R.id.activity_main_button_delete);
        setupAddButton();
        setupDeleteButton();
        setupSaveButton();
    }

    private void setupAddButton() {
        buttonAdd.setOnClickListener(v -> {
            currentStudent = generateNewStudent();
            if (currentStudent != null) {
                students.add(currentStudent);
                studentsAdapter.notifyDataSetChanged();
                cleanCard();
                return;
            }
            Toast toast = Toast.makeText(this,
                    "Fill first name and last name", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void setupSaveButton() {
        buttonSave.setOnClickListener(v -> {
            if (currentStudent != null) {
                if (editStudent()) {
                    studentsAdapter.notifyDataSetChanged();
                    cleanCard();
                    return;
                }
                Toast toast = Toast.makeText(this,
                        "Fill first name and last name", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Toast toast = Toast.makeText(this,
                    "Choose a student", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void setupDeleteButton() {
        buttonDelete.setOnClickListener(v -> {
            int index = students.indexOf(currentStudent);
            if (index != -1) {
                students.remove(currentStudent);
                studentsAdapter.notifyDataSetChanged();
                cleanCard();
                return;
            }
            Toast toast = Toast.makeText(this,
                    "Choose a student", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    private void cleanCard() {
        firstNameView.setText("");
        lastNameView.setText("");
        genderView.setChecked(false);
        photoView.setImageResource(R.color.colorGray);
        currentStudent = null;
    }

    public void onStudentClick(Student student) {
        currentStudent = student;
        setStudentCard();
    }

    private void setStudentCard() {
        firstNameView.setText(currentStudent.getFirstName());
        lastNameView.setText(currentStudent.getSecondName());
        genderView.setChecked(currentStudent.isMaleGender());
        photoView.setImageResource(currentStudent.getPhoto());
    }

    private int choosePhoto(boolean isMale) {
        String photoName;
        if (isMale) {
            photoName = "male_";
        } else {
            photoName = "female_";
        }
        return getResources().getIdentifier(photoName + (random.nextInt(3) + 1), "drawable", getPackageName());

    }

    private Student generateNewStudent() {
        String firstName;
        String lastName;
        try {
            firstName = firstNameView.getText().toString();
            lastName = lastNameView.getText().toString();
        } catch (NullPointerException e) {
            return null;
        }
        if (firstName.length() != 0 && lastName.length() != 0) {
            boolean isMale = genderView.isChecked();
            return new Student(firstName, lastName, isMale, choosePhoto(isMale));
        }
        return null;
    }

    private boolean editStudent() {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        if (firstName.length() != 0 && lastName.length() != 0) {
            currentStudent.setFirstName(firstName);
            currentStudent.setSecondName(lastName);
            boolean oldIsMale = currentStudent.isMaleGender();
            currentStudent.setMaleGender(genderView.isChecked());
            if (oldIsMale != currentStudent.isMaleGender()) {
                currentStudent.setPhoto(choosePhoto(currentStudent.isMaleGender()));
                cleanCard();
            }
            return true;
        }
        return false;
    }

    private List<Student> generateStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ivan", "Ivanov", true, R.drawable.male_1));
        students.add(new Student("Rita", "Ritina", false, R.drawable.female_1));
        students.add(new Student("Petr", "Petrov", true, R.drawable.male_2));
        students.add(new Student("Masha", "Mashina", false, R.drawable.female_2));
        students.add(new Student("Kyzya", "Kyzin", true, R.drawable.male_3));
        students.add(new Student("Tanya", "Tanina", false, R.drawable.female_3));
        return students;
    }
}
