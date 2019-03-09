package ru.ok.technopolis.students;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean VISIBILITY_LAYOUTS = false;
    private DataBaseHandler dbHandler;

    private List<Student> students;
    private StudentAdapter studentAdapter;
    private Random random = new Random();
    private int[] malePhoto = {R.drawable.male_avatar_1, R.drawable.male_avatar_3, R.drawable.male_avatar_2};
    private int[] femalePhoto = {R.drawable.female_avatar_1, R.drawable.female_avatar_2, R.drawable.female_avatar_3};
    private int prevPos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDataBase();
        students = dbHandler.getAllStudents();
        if (students.size() == 0) {
            students = generateStudentList();
        } else {
            TextView textWithoutRVView = findViewById(R.id.main_activity__text_without_rv);
            textWithoutRVView.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
            recyclerView.setVisibility(View.VISIBLE);


        }
        if (BuildConfig.LOG) {
            Log.d(BuildConfig.LOG_TAG, "I am working!!!");
        }

        setupRecyclerView();
        setupButtons();


    }

    private void setupDataBase() {
        dbHandler = new DataBaseHandler(this);
    }

    @SuppressWarnings({"LambdaBodyCanBeCodeBlock", "Convert2MethodRef"})
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
        studentAdapter = new StudentAdapter(students, student -> onItemClick(student));
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private List<Student> generateStudentList() {
        return new ArrayList<>();
    }

    private void setupButtons() {
        Button addButton = findViewById(R.id.main_activity_add_button_main);
        addButton.setOnClickListener(v -> onAddClick());
        TextView titleTextView = findViewById(R.id.main_acivity__title_text);
        titleTextView.setOnClickListener(v -> onTitleTextAreaClick());
    }

    private void onTitleTextAreaClick() {
        VISIBILITY_LAYOUTS = false;
        LinearLayout linearLayout = findViewById(R.id.main_activity_input_layout);
        linearLayout.setVisibility(View.GONE);

    }

    private void onAddClick() {
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);

        if (!VISIBILITY_LAYOUTS) {
            VISIBILITY_LAYOUTS = true;
            TextView textWithoutRVView = findViewById(R.id.main_activity__text_without_rv);
            textWithoutRVView.setVisibility(View.GONE);
            LinearLayout linearLayout = findViewById(R.id.main_activity_input_layout);
            linearLayout.setVisibility(View.VISIBLE);

            recyclerView.setVisibility(View.VISIBLE);
        }
        if (BuildConfig.LOG) {
            Log.d(BuildConfig.LOG_TAG, "On add click: prevPos is " + prevPos);
        }
        if (prevPos != -1) {
            try {
                Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(prevPos)).itemView.setBackgroundColor((Color.parseColor("#FFFFFF")));
            } catch (Exception ignore) {
            }
            prevPos = -1;

        }
        generateNewStudent();
    }

    private void onDeleteClick(Student student) {
        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
        students.remove(student);
        dbHandler.deleteStudent(student);
        if (prevPos != -1) {
            try {
                Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(prevPos)).itemView.setBackgroundColor((Color.parseColor("#FFFFFF")));
            } catch (Exception ignore) {
            }
        }
        prevPos = -1;
        studentAdapter.notifyDataSetChanged();
        onAddClick();
        if (BuildConfig.LOG) {
            Log.d(BuildConfig.LOG_TAG, "Student " + student.getFirstName() + " " + student.getSecondName() + " deleted");
        }
        onAddClick();
    }

    private void onSaveClick(Student student, String name, String lastname, boolean gender) {
        if (BuildConfig.LOG) {
            Log.d(BuildConfig.LOG_TAG, "Save Clicked");
        }
        if (name.length() != 0 && lastname.length() != 0) {

            int indexArr = students.indexOf(student);
            student.setFirstName(name.trim());
            student.setSecondName(lastname.trim());
            boolean genderLast = student.isMaleGender();
            student.setMaleGender(gender);
            if (indexArr == -1 || (gender ^ genderLast)) {
                int indexPhoto = random.nextInt(3);
                if (name.equalsIgnoreCase("валерий") && lastname.equalsIgnoreCase("ковшов") ||
                        (name.equalsIgnoreCase("valery") && lastname.equalsIgnoreCase("kovshov"))) {
                    student.setPhoto(R.drawable.valeriy_min);
                } else {
                    if (BuildConfig.LOG) {
                        Log.d(BuildConfig.LOG_TAG, "" + indexPhoto + "");
                    }
                    if (student.isMaleGender()) {
                        student.setPhoto(malePhoto[indexPhoto]);

                    } else {
                        student.setPhoto(femalePhoto[indexPhoto]);
                    }
                }
            }
            if (BuildConfig.LOG) {
                Log.d(BuildConfig.LOG_TAG, "Change Saved");
            }
            if (indexArr == -1) {
                students.add(student);
                dbHandler.addStudent(student);
            } else {
                students.set(indexArr, student);
                dbHandler.updateStudentList(student);
            }


            //setupMenu(student);
            onAddClick();
            studentAdapter.notifyDataSetChanged();

            /*int pos = students.indexOf(student);
            RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
            if (pos > -1) {
                Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(pos)).itemView.setBackgroundColor((Color.parseColor("#BEBEBE")));
            }
            if (prevPos != -1) {
                Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(prevPos)).itemView.setBackgroundColor((Color.parseColor("#FFFFFF")));

            }*/

            if (BuildConfig.LOG) {
                Log.d(BuildConfig.LOG_TAG, "UI Updated");
            }


        } else {
            Toast.makeText(MainActivity.this, "Ошибка, введите имя и фамилию", Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("NewApi")
    private void onItemClick(Student student) {
        int pos = students.indexOf(student);

        RecyclerView recyclerView = findViewById(R.id.main_activity__rv_students);
        Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(pos)).itemView.setBackgroundColor((Color.parseColor("#BEBEBE")));
        if (prevPos != -1 && prevPos != pos) {
            try {
                Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(prevPos)).itemView.setBackgroundColor((Color.parseColor("#FFFFFF")));
            } catch (Exception ignore) {
            }
        }
        if (BuildConfig.LOG) {
            Log.d(BuildConfig.LOG_TAG, "Now pos is " + pos + " and prevPos is " + prevPos);
        }
        prevPos = pos;
        if (!VISIBILITY_LAYOUTS) {
            VISIBILITY_LAYOUTS = true;
            LinearLayout linearLayout = findViewById(R.id.main_activity_input_layout);

            linearLayout.setVisibility(View.VISIBLE);

        }
        setupMenu(student);
    }

    private void setupMenu(Student student) {
        ImageView imageView = findViewById(R.id.main_activity_avatar_full);
        TextView nameEditTextView = findViewById(R.id.main_activity_edit_name);
        TextView lastnameEditTextView = findViewById(R.id.main_activity_edit_lastname);
        CheckBox genderCheckBox = findViewById(R.id.main_activity_gender_checkbox);

        imageView.setImageResource(student.getPhoto());
        nameEditTextView.setText(student.getFirstName());
        lastnameEditTextView.setText(student.getSecondName());
        genderCheckBox.setChecked(student.isMaleGender());

        Button deliteButton = findViewById(R.id.main_activity_button_delete);


        deliteButton.setOnClickListener(v -> onDeleteClick(student));
        if (students.size() == 0) {
            deliteButton.setClickable(false);
        } else {
            deliteButton.setClickable(true);
        }
        Button saveButton = findViewById(R.id.main_activity_button_save);
        saveButton.setOnClickListener(v -> onSaveClick(student, nameEditTextView.getText().toString(), lastnameEditTextView.getText().toString(), genderCheckBox.isChecked()));
    }

    private void generateNewStudent() {
        Student student = new Student(null, null, false, 0);

        setupMenu(student);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
