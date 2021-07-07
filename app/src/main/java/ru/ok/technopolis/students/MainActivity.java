package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    private StudentAdapter studentAdapter;
    private int pics[];
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = new ArrayList<>();
        pics = new int[6];
        currentStudent = null;
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        setupAddButton();
        setupSaveButton();
        setupDeleteButton();
        setupPics();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_students);
        studentAdapter = new StudentAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupPics() {
        pics[0] = R.drawable.male_1;
        pics[1] = R.drawable.male_2;
        pics[2] = R.drawable.male_3;
        pics[3] = R.drawable.female_1;
        pics[4] = R.drawable.female_2;
        pics[5] = R.drawable.female_3;
    }

    private void setupAddButton() {
        Button button = findViewById(R.id.activity_main__btn_add);
        button.setOnClickListener(v -> onAddClick());
    }

    private void onAddClick() {
        setupEdit("", "", false, 0);
        currentStudent = new Student("", "", false, 0);
        studentAdapter.resetSelectionPos();
        studentAdapter.notifyDataSetChanged();
    }

    private void setupEdit(String name, String familyName, Boolean isMale, int pic) {
        EditText nameEdit = findViewById(R.id.names_layout__edit_name);
        EditText familyNameEdit = findViewById(R.id.names_layout__edit_fname);
        CheckBox male = findViewById(R.id.sex_layout__checkbox);
        CircleImageView pics = findViewById(R.id.data_layout__edit_img);
        nameEdit.setText(name);
        familyNameEdit.setText(familyName);
        male.setChecked(isMale);
        pics.setImageResource(pic);
    }

    private void setupSaveButton() {
        Button button = findViewById(R.id.cl_edit__save);
        button.setOnClickListener(v -> onSaveClick());
    }

    private void setupDeleteButton() {
        Button button = findViewById(R.id.cl_edit__delete);
        button.setOnClickListener(v -> onDeleteClick());
    }

    private void onDeleteClick() {
        if (currentStudent == null) {
            Toast.makeText(this, R.string.empty_student, Toast.LENGTH_SHORT).show();
        } else {
            students.remove(currentStudent);
            currentStudent = null;
            studentAdapter.resetSelectionPos();
            studentAdapter.notifyDataSetChanged();
            setupEdit("", "", Boolean.FALSE, 0);
        }
    }

    private void onSaveClick() {
        EditText name = findViewById(R.id.names_layout__edit_name);
        EditText familyName = findViewById(R.id.names_layout__edit_fname);
        CheckBox male = findViewById(R.id.sex_layout__checkbox);
        CircleImageView pic = findViewById(R.id.data_layout__edit_img);
        int picId;
        if ((name.getText() != null) && (familyName.getText() != null)) {
            if (TextUtils.isEmpty(name.getText().toString().trim())
                    || TextUtils.isEmpty(familyName.getText().toString().trim())) {
                Toast.makeText(this, R.string.wrong_enter, Toast.LENGTH_SHORT).show();
            } else {
                if (currentStudent == null || currentStudent.getFirstName().equals("")) {
                    students.add(currentStudent = new Student(name.getText().toString().trim(),
                            familyName.getText().toString().trim(),
                            male.isChecked(),
                            picId = getRandomPick(male.isChecked())));
                    pic.setImageResource(picId);
                    studentAdapter.resetSelectionPos();
                    studentAdapter.notifyDataSetChanged();
                } else {
                    currentStudent.setFirstName(name.getText().toString().trim());
                    currentStudent.setSecondName(familyName.getText().toString().trim());
                    if (currentStudent.isMaleGender() != male.isChecked()) {
                        currentStudent.setPhoto(picId = getRandomPick(male.isChecked()));
                        currentStudent.setMaleGender(male.isChecked());
                        pic.setImageResource(picId);
                    }
                    studentAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    private int getRandomPick(Boolean isMale) {
        Random random = new Random();
        int maleRandom = isMale ? 0 : 3;
        return pics[maleRandom + random.nextInt(3)];
    }

    private void onStudentClick(Student student) {
        setupEdit(student.getFirstName(), student.getSecondName(), student.isMaleGender(), student.getPhoto());
        currentStudent = student;
    }

}
