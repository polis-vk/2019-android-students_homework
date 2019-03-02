package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StudentAdapter studentAdapter;
    private List<Student> students;
    private Student selectedStudent;
    private EditText firstNameEditText;
    private EditText secondNameEditText;
    private CheckBox genderCheckBox;
    private ImageView imageView;
    private int imageIdInAvatarField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecycleView();

        firstNameEditText = findViewById(R.id.activity_main__first_name);
        secondNameEditText = findViewById(R.id.activity_main__second_name);
        genderCheckBox = findViewById(R.id.activity_main__checkbox_gender);
        imageView = findViewById(R.id.activity_main__student_avatar);

        setDefaultCreateField();

        setListener(this);
    }

    private void setDefaultCreateField() {
        firstNameEditText.setText(R.string.empty_string, TextView.BufferType.EDITABLE);
        secondNameEditText.setText(R.string.empty_string, TextView.BufferType.EDITABLE);
        genderCheckBox.setChecked(false);
        imageView.setImageResource(generateRandomAvatar(false));
    }

    private void setListener(View.OnClickListener listener) {
        findViewById(R.id.activity_main__add_button).setOnClickListener(listener);
        findViewById(R.id.activity_main__checkbox_gender).setOnClickListener(listener);
        findViewById(R.id.activity_main__save_button).setOnClickListener(listener);
        findViewById(R.id.activity_main__delete_button).setOnClickListener(listener);
    }

    private void setupRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__students);
        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(students, new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                fillCreateField(student);
            }
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void fillCreateField(Student student) {
        selectedStudent = student;
        imageIdInAvatarField = student.getPhoto();
        firstNameEditText.setText(student.getFirstName(), TextView.BufferType.EDITABLE);
        secondNameEditText.setText(student.getSecondName(), TextView.BufferType.EDITABLE);
        genderCheckBox.setChecked(student.isMaleGender());
        imageView.setImageResource(imageIdInAvatarField);
        setVisibilityBottomElements(View.VISIBLE);
    }

    private int generateRandomAvatar(boolean gender) {
        Random random = new Random();
        int i = random.nextInt(3) + 1;
        int result;
        switch (i) {
            case 1:
                result = gender ? R.drawable.male_1 : R.drawable.female_1;
                break;
            case 2:
                result = gender ? R.drawable.male_2 : R.drawable.female_2;
                break;
            case 3:
                result = gender ? R.drawable.male_3 : R.drawable.female_3;
                break;
            default:
                result = R.drawable.male_1;
                break;
        }
        imageIdInAvatarField = result;
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_main__save_button) {
            onSaveBtnClicked();
        } else if (v.getId() == R.id.activity_main__delete_button) {
            onDeleteBtnClicked();
        } else if (v.getId() == R.id.activity_main__checkbox_gender) {
            onGenderCheckBoxClicked();
        } else if (v.getId() == R.id.activity_main__add_button) {
            onAddBtnClicked();
        }
    }

    private void onAddBtnClicked() {
        setVisibilityBottomElements(View.VISIBLE);
        setDefaultCreateField();
        selectedStudent = null;
        resetSelector();
    }

    private void resetSelector() {
        int previousItemSelected = studentAdapter.getSelectedItem();
        studentAdapter.setSelectedItemNoPosition();
        studentAdapter.notifyItemChanged(previousItemSelected);
    }

    private void setVisibilityBottomElements(int visible) {
        findViewById(R.id.group_bottom_elements).setVisibility(visible);
    }

    private void onGenderCheckBoxClicked() {
        if (genderCheckBox.isChecked()) {
            imageView.setImageResource(generateRandomAvatar(true));
        } else {
            imageView.setImageResource(generateRandomAvatar(false));
        }
    }

    boolean isValidInputValues() {
        if (firstNameEditText.getText() == null || secondNameEditText.getText() == null) {
            return false;
        }
        int firstNameLength = firstNameEditText.getText().toString().length();
        int secondNameLegth = secondNameEditText.getText().toString().length();
        if (firstNameLength == 0 || secondNameLegth == 0) {
            Toast.makeText(MainActivity.this, R.string.is_empty_input_field, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void onDeleteBtnClicked() {
        if (selectedStudent == null) {
            setDefaultCreateField();
        } else {
            students.remove(selectedStudent);
            setDefaultCreateField();
            selectedStudent = null;
            studentAdapter.notifyDataSetChanged();
        }
        studentAdapter.setSelectedItemNoPosition();
        setVisibilityBottomElements(View.GONE);
    }

    private void onSaveBtnClicked() {
        if (!isValidInputValues()) {
            return;
        }

        String firstName = firstNameEditText.getText().toString();
        String secondName = secondNameEditText.getText().toString();
        boolean genderCheckBoxChecked = genderCheckBox.isChecked();

        if (selectedStudent == null) {
            students.add(new Student(firstName, secondName, genderCheckBoxChecked, imageIdInAvatarField));
        } else {
            selectedStudent.setFirstName(firstName);
            selectedStudent.setSecondName(secondName);
            selectedStudent.setMaleGender(genderCheckBoxChecked);
            selectedStudent.setPhoto(imageIdInAvatarField);
            selectedStudent = null;
        }

        resetSelector();
        setDefaultCreateField();
        setVisibilityBottomElements(View.GONE);
        studentAdapter.notifyDataSetChanged();
    }
}
