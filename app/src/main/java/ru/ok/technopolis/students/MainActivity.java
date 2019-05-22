package ru.ok.technopolis.students;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private List<Student> students;
    private StudentsAdapter studentsAdapter;
    public Button btnAddStudent;
    public Button btnDelStudent;
    public Button btnSaveStudent;
    private final Random random = new Random();
    private View borderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = generateStudenList();
        setContentView(R.layout.activity_main);
        setupRecyclerView();

        btnAddStudent = findViewById(R.id.activity_main_add_button);
        btnDelStudent = findViewById(R.id.activity_main_delete);
        btnSaveStudent = findViewById(R.id.activity_main_save);

        btnAddStudent.setOnClickListener(this);
        btnDelStudent.setOnClickListener(this);
        btnSaveStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_add_button:
                onAddClick();
                setupRecyclerView();
                break;
            case R.id.activity_main_delete:
                onDellClick();
                setupRecyclerView();
                break;
            case R.id.activity_main_save:
                onSaveStudent();
                setupRecyclerView();
                break;
            default:
                onAddClick();
                setupRecyclerView();
                break;
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_list);
        studentsAdapter = new StudentsAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student, String id, View view) {
        TextView firstname = findViewById(R.id.edit_name);
        TextView secondname = findViewById(R.id.edit_secondname);
        ImageView posterImageView = findViewById(R.id.activity_main_avatarka);
        CheckBox male = findViewById(R.id.sex_checkbox);
        firstname.setText(student.getFirstName());
        secondname.setText(student.getSecondName());
        posterImageView.setImageResource(student.getPhoto());
        male.setChecked(student.isMaleGender());
        Button dellId = findViewById(R.id.activity_main_delete);
        dellId.setContentDescription(id);
        resetBorder(view);
    }

    private void resetBorder(View view) {
        if (borderView != null)
            borderView.setBackgroundColor(Color.TRANSPARENT);
        borderView = view;
        borderView.setBackgroundResource(R.drawable.customborder);
    }

    private void onDellClick() {
        String str = (String) btnDelStudent.getContentDescription();
        TextView firstname = findViewById(R.id.edit_name);
        TextView secondname = findViewById(R.id.edit_secondname);
        ImageView posterImageView = findViewById(R.id.activity_main_avatarka);
        CheckBox male = findViewById(R.id.sex_checkbox);
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
        if (str != null) {
            int id = Integer.parseInt(str);
            if (id >= 0 && id != 1000) {
                students.remove(id);
            }
        }
    }

    private void onAddClick() {
        TextView firstname = findViewById(R.id.edit_name);
        TextView secondname = findViewById(R.id.edit_secondname);
        ImageView posterImageView = findViewById(R.id.activity_main_avatarka);
        CheckBox male = findViewById(R.id.sex_checkbox);
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
    }

    private void onSaveStudent() {
        int id = Integer.parseInt((String) btnDelStudent.getContentDescription());

        TextView firstname = findViewById(R.id.edit_name);
        TextView secondname = findViewById(R.id.edit_secondname);
        ImageView posterImageView = findViewById(R.id.activity_main_avatarka);
        CheckBox male = findViewById(R.id.sex_checkbox);
        int imgSrc = genImgSrc(male.isChecked());
        if (firstname.length() < 2 || secondname.length() < 2) {
            id = -1;
        }
        if (id == 1000) {
            if (firstname.getText().length() > 1 && secondname.getText().length() > 1)
                students.add(new Student(firstname.getText().toString(), secondname.getText().toString(), male.isChecked(), imgSrc));
        } else if (id >= 0) {
            Student student;
            student = students.get(id);
            student.setFirstName(firstname.getText().toString());
            student.setSecondName(secondname.getText().toString());
            student.setMaleGender(male.isChecked());
            student.setPhoto(imgSrc);
        }
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
    }

    private int genImgSrc(boolean male) {
        int imgSrc;
        int i = (random.nextInt(3)) + 1;
        if (male) {
            switch (i) {
                case 1:
                    imgSrc = R.drawable.male_1;
                    break;
                case 2:
                    imgSrc = R.drawable.male_2;
                    break;
                case 3:
                    imgSrc = R.drawable.male_3;
                    break;
                default:
                    imgSrc = R.mipmap.ic_launcher;
                    break;
            }
        } else {
            switch (i) {
                case 1:
                    imgSrc = R.drawable.female_1;
                    break;
                case 2:
                    imgSrc = R.drawable.female_2;
                    break;
                case 3:
                    imgSrc = R.drawable.female_3;
                    break;
                default:
                    imgSrc = R.mipmap.ic_launcher;
                    break;
            }
        }
        return imgSrc;
    }

    private List<Student> generateStudenList() {
        List<Student> student = new ArrayList<>();
        student.add(new Student("Иванов", "Иван", true, R.drawable.male_1));
        student.add(new Student("Петров", "Петр", true, R.drawable.male_2));
        student.add(new Student("Сидорова", "Мария", false, R.drawable.female_1));
        return student;
    }

}