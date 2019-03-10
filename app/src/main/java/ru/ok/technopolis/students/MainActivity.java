package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private int[] iconArrMale = {R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
    private int[] iconsArrFemale = {R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};
    private int randomVal;
    private int randomIcon;
    private boolean sexOfStudent;
    private int index;

    private View currentView;
    private RadioButton maleRadioBtn;
    private RadioButton femaleRadioBtn;
    private List<Student> students;
    private EditText firstNameText;
    private EditText secondNameText;
    private ImageView icon;
    private FloatingActionButton addBtn;
    private View bottomView;
    private RadioGroup sexRadioGroup;
    private Button deleteBtn;
    private Button saveBtn;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        studentAdapter = new StudentAdapter(generateStudentList(),
                this::onStudentClick);

        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        setAddBtn();
        setDeleteAddBtn();
        setSaveBtn();
        setSexRadioGroup();
    }

    private void onStudentClick(View view, int i) {
        firstNameText.setText(students.get(i).getFirstName());
        secondNameText.setText(students.get(i).getSecondName());
        icon.setImageResource(students.get(i).getPhoto());
        addBtn.hide();
        if (students.get(i).isMaleGender()) {
            maleRadioBtn.toggle();
        } else {
            femaleRadioBtn.toggle();
        }
        index = i;

        if (currentView != null) {
            currentView.setBackgroundResource(R.color.white);
        }
        if (view != null) {
            view.setBackgroundResource(R.color.colorPrimaryDark);
        }

        currentView = view;
        bottomView.setVisibility(View.VISIBLE);
    }

    private void findViews() {
        sexRadioGroup = findViewById(R.id.activity_main_sex);
        maleRadioBtn = findViewById(R.id.activity_main_sex_male);
        femaleRadioBtn = findViewById(R.id.activity_main_sex_female);
        addBtn = findViewById(R.id.activity_main_fb_add);
        firstNameText = findViewById(R.id.activity_main_first_name);
        secondNameText = findViewById(R.id.activity_main_second_name);
        icon = findViewById(R.id.activity_main_icon);
        bottomView = findViewById(R.id.activity_main_bottom_view);
        deleteBtn = findViewById(R.id.activity_main_delete);
        saveBtn = findViewById(R.id.activity_main_save);
        recyclerView = findViewById(R.id.activity_main_recycler_view);
    }

    private void setSexRadioGroup() {
        sexRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.activity_main_sex_male) {
                sexOfStudent = true;
                randomIcon = iconArrMale[randomVal];
            } else if (checkedId == R.id.activity_main_sex_female) {
                sexOfStudent = false;
                randomIcon = iconsArrFemale[randomVal];
            }

            icon.setImageResource(randomIcon);
        });
    }

    private void setSaveBtn() {
        saveBtn.setOnClickListener(v -> {
            if (firstNameText.getText() != null && secondNameText.getText() != null &&
                    !firstNameText.getText().toString().equals("") &&
                    !secondNameText.getText().toString().equals("")) {
                Student student = new Student(firstNameText.getText().toString(),
                        secondNameText.getText().toString(),
                        sexOfStudent,
                        randomIcon);
                students.add(student);

                bottomView.setVisibility(View.GONE);
                addBtn.show();
            } else {
                Toast.makeText(getApplicationContext(), "Имя или фамилия не заданы",
                        Toast.LENGTH_SHORT).show();
            }
        });
        studentAdapter.notifyDataSetChanged();
    }

    private void setDeleteAddBtn() {
        deleteBtn.setOnClickListener(v -> {
            students.remove(index);
            studentAdapter.notifyDataSetChanged();
            bottomView.setVisibility(View.GONE);
            addBtn.show();
        });
    }

    private void setAddBtn() {
        addBtn.setOnClickListener(v -> {
            addBtn.hide();
            bottomView.setVisibility(View.VISIBLE);

            firstNameText.setText("");
            secondNameText.setText("");
            sexRadioGroup.clearCheck();
            randomVal = random.nextInt(3);
        });
    }

    private List<Student> generateStudentList() {
        students = new LinkedList<>();
        students.add(new Student("Вася", "Пупкин", true, R.drawable.male_1));
        students.add(new Student("Инна", "Вупкина", false, R.drawable.female_1));
        students.add(new Student("Лариса", "Купкина", false, R.drawable.female_2));
        return students;
    }
}
