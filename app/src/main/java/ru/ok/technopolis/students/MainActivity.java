package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private EditText studentSurName;
    private EditText studentName;
    private CheckBox gender;
    private ImageView avatar;
    private int index = -1, imageResource;
    private StudentAdapter studentAdapter;
    private Random random = new Random();
    private int[] men, women;
    private View currentView;
    private View studentLeftBorder;
    private View studentRightBorder;
    private View studentTopBorder;
    private View studentBottomBorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupFields();
        setupRecyclerView();
        setupButtons();
    }

    private void setupButtons() {
        findViewById(R.id.activity_main__save_student_button).setOnClickListener(v -> onSaveClick());
        findViewById(R.id.activity_main__delete_student_button).setOnClickListener(v -> onDeleteClick());
        findViewById(R.id.activity_main__add_student_button).setOnClickListener(v -> onAddClick());
        gender.setOnClickListener(v -> onChecked());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setupFields() {
        studentSurName = findViewById(R.id.activity_main__studentSurName);
        studentName = findViewById(R.id.activity_main__studentName);
        gender = findViewById(R.id.activity_main__checkGender);
        avatar = findViewById(R.id.activity_main__studentPicture);
        men = new int[]{R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
        women = new int[]{R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};
        students = new ArrayList<>();
    }

    private void onAddClick() {
        if (index != -1 && isDataSaved())
            Toast.makeText(MainActivity.this, getResources().getString(R.string.userShouldSaveCreated_warning), Toast.LENGTH_SHORT).show();
        else {
            int randomGender = random.nextInt(2);
            Student student = new Student("", "", randomGender == 1, 0);
            imageResource = getRandomPhoto(student.isMaleGender());
            student.setPhoto(imageResource);
            students.add(student);
            studentAdapter.notifyDataSetChanged();
            setStudent(null, students.size() - 1);
        }
    }

    private int getRandomPhoto(boolean maleGender) {
        if (maleGender) {
            return men[random.nextInt(men.length)];
        }
        return women[random.nextInt(women.length)];
    }

    private void onChecked() {
        imageResource = getRandomPhoto(gender.isChecked());
        avatar.setImageResource(imageResource);
    }

    private void clearData() {
        index = -1;
        studentName.setText("");
        studentSurName.setText("");
        gender.setChecked(false);
        avatar.setImageResource(0);
        studentName.setError(null);
        studentSurName.setError(null);
        setBorderVisibility(currentView, View.INVISIBLE);
    }

    private void onStudentClick(View view, int i) {
        if (index != -1) {
            if (checkFields()) {
                if (isDataSaved()) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.userShouldSave_warning), Toast.LENGTH_SHORT).show();
                } else {
                    setStudent(view, i);
                }
            }
        } else {
            setStudent(view, i);
        }
    }

    private boolean isDataSaved() {
        return students.get(index).getFirstName().trim().length() == 0 || students.get(index).getSecondName().trim().length() == 0;
    }

    private boolean checkFields() {
        if (studentName.getText().toString().trim().length() > 0 && studentSurName.getText().toString().trim().length() > 0)
            return true;
        if (studentName.getText().toString().trim().length() == 0)
            studentName.setError(getResources().getString(R.string.studentName_error));
        if (studentSurName.getText().toString().trim().length() == 0)
            studentSurName.setError(getResources().getString(R.string.studentSurName_error));
        return false;
    }

    private void setStudent(View view, int i) {
        studentSurName.setText(students.get(i).getSecondName());
        studentName.setText(students.get(i).getFirstName());
        gender.setChecked(students.get(i).isMaleGender());
        avatar.setImageResource(students.get(i).getPhoto());
        index = i;
        if (currentView != null)
            setBorderVisibility(currentView, View.INVISIBLE);
        if (view != null)
            setBorders(view);
        currentView = view;
        setBorderVisibility(view, View.VISIBLE);
        updateButtons(true);
    }

    private void setBorders(View view) {
        studentLeftBorder = view.findViewById(R.id.student_item__left);
        studentRightBorder = view.findViewById(R.id.student_item__right);
        studentTopBorder = view.findViewById(R.id.student_item__top);
        studentBottomBorder = view.findViewById(R.id.student_item__bottom);
    }

    private void updateButtons(boolean value) {
        studentName.setEnabled(value);
        studentSurName.setEnabled(value);
        gender.setEnabled(value);
        findViewById(R.id.activity_main__save_student_button).setEnabled(value);
        findViewById(R.id.activity_main__delete_student_button).setEnabled(value);
    }

    private void setBorderVisibility(View view, int visible) {
        if (view != null) {
            studentTopBorder.setVisibility(visible);
            studentBottomBorder.setVisibility(visible);
            studentLeftBorder.setVisibility(visible);
            studentRightBorder.setVisibility(visible);
        }
    }

    private void onSaveClick() {
        if (checkFields()) {
            students.get(index).setFirstName(getFirstName());
            students.get(index).setSecondName(getLastName());
            students.get(index).setMaleGender(gender.isChecked());
            students.get(index).setPhoto(imageResource);
            Toast.makeText(MainActivity.this, getResources().getString(R.string.student_saved), Toast.LENGTH_SHORT).show();
            studentAdapter.notifyDataSetChanged();
        }
    }

    private void onDeleteClick() {
        updateButtons(false);
        students.remove(index);
        studentAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, getResources().getString(R.string.student_removed), Toast.LENGTH_SHORT).show();
        clearData();
    }

    private String getFirstName() {
        return String.valueOf(studentName.getText());
    }

    private String getLastName() {
        return String.valueOf(studentSurName.getText());
    }
}