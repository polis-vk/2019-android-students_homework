package ru.ok.technopolis.students;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Student> students;
    private EditText surName, name;
    private CheckBox gender;
    private ImageView avatar;
    private int index = -1;
    private StudentAdapter studentAdapter;
    private Random random = new Random();
    private int[] men, women;
    private View lastView, currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
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
        surName = findViewById(R.id.activity_main__studentSurName);
        name = findViewById(R.id.activity_main__studentName);
        gender = findViewById(R.id.activity_main__checkGender);
        avatar = findViewById(R.id.activity_main__studentPicture);
        men = new int[]{R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
        women = new int[]{R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};
        students = new ArrayList<>();
    }

    private void onAddClick() {
        if (index != -1 && isDataSaved())
            Toast.makeText(MainActivity.this, "Для начала сохраните уже созданного студента", Toast.LENGTH_SHORT).show();
        else {
            int randomGender = random.nextInt(2);
            Student student = new Student("", "", randomGender == 1);
            student.setBitmap(getRandomBitmap(student.isMaleGender()));
            students.add(student);
            studentAdapter.notifyDataSetChanged();
            setStudent(null, students.size() - 1);
        }
    }

    private Bitmap getRandomBitmap(boolean maleGender) {
        if (maleGender)
            return BitmapFactory.decodeResource(getResources(), men[random.nextInt(men.length)]);
        return BitmapFactory.decodeResource(getResources(), women[random.nextInt(women.length)]);
    }

    private void onChecked() {
        if (index != -1) {
            students.get(index).setBitmap(getRandomBitmap(gender.isChecked()));
            students.get(index).setMaleGender(gender.isChecked());
            String tmpName = getFirstName(), tmpSurName = getLastName();
            setStudent(null, index);
            name.setText(tmpName);
            surName.setText(tmpSurName);
            studentAdapter.notifyDataSetChanged();
        }
    }

    private void clearData() {
        index = -1;
        name.setText("");
        surName.setText("");
        gender.setChecked(false);
        avatar.setImageResource(0);
        name.setError(null);
        surName.setError(null);
    }

    private void onStudentClick(View view, int i) {
        if (index != -1) {
            if (checkFields())
                if (isDataSaved()) {
                    Toast.makeText(MainActivity.this, "Вам следует сохранить изменения", Toast.LENGTH_SHORT).show();
                } else
                    setStudent(view, i);
        } else
            setStudent(view, i);
    }

    private boolean isDataSaved() {
        return students.get(index).getFirstName().trim().length() == 0 || students.get(index).getSecondName().trim().length() == 0;
    }

    private boolean checkFields() {
        if (name.getText().toString().trim().length() > 0 && surName.getText().toString().trim().length() > 0)
            return true;
        if (name.getText().toString().trim().length() == 0) {
            name.setError("Имя не может быть пустым");
        }
        if (surName.getText().toString().trim().length() == 0) {
            surName.setError("Фамилия не может быть пустой");
        }
        return false;
    }

    private void setStudent(View view, int i) {
        if (currentView != lastView)
            lastView = currentView;
        currentView = view;
        surName.setText(students.get(i).getSecondName());
        name.setText(students.get(i).getFirstName());
        gender.setChecked(students.get(i).isMaleGender());
        avatar.setImageBitmap(students.get(i).getBitmap());
        index = i;
        if (view != null)
            setBorderVisibility(view, View.VISIBLE);
        if (lastView != null && lastView != view)
            setBorderVisibility(lastView, View.INVISIBLE);
        updateButtons(true);
    }

    private void updateButtons(boolean value) {
            name.setEnabled(value);
            surName.setEnabled(value);
            gender.setEnabled(value);
            findViewById(R.id.activity_main__save_student_button).setEnabled(value);
            findViewById(R.id.activity_main__delete_student_button).setEnabled(value);
    }

    private void setBorderVisibility(View view, int visible) {
        view.findViewById(R.id.student_item__top).setVisibility(visible);
        view.findViewById(R.id.student_item__bottom).setVisibility(visible);
        view.findViewById(R.id.student_item__left).setVisibility(visible);
        view.findViewById(R.id.student_item__right).setVisibility(visible);
    }

    private void onSaveClick() {
        if (index != -1) {
            if (checkFields()) {
                students.get(index).setFirstName(getFirstName());
                students.get(index).setSecondName(getLastName());
                students.get(index).setMaleGender(gender.isChecked());
                Toast.makeText(MainActivity.this, "Студент сохранён", Toast.LENGTH_SHORT).show();
                studentAdapter.notifyDataSetChanged();
            }
        }
    }

    private void onDeleteClick() {
        if (index != -1) {
            updateButtons(false);
            students.remove(index);
            studentAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Студент удалён", Toast.LENGTH_SHORT).show();
            clearData();
        }
    }

    private String getFirstName() {
        return String.valueOf(name.getText());
    }

    private String getLastName() {
        return String.valueOf(surName.getText());
    }


}